package spout.client.fabric.moredatadriven.minecraft.registry;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.core.IdMap;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.VarInt;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Keeps track of any registry id mappings that must be applied.
 */
public final class RegistryIdMappings {

    private RegistryIdMappings() {
        throw new UnsupportedOperationException();
    }

    private static final Map<Identifier, Instance> instancesByRegistryIdentifier = new HashMap<>();

    public static <T, R> StreamCodec<RegistryFriendlyByteBuf, R> getStreamCodec(
        ResourceKey<? extends Registry<T>> registryKey,
        Function<Registry<T>, IdMap<R>> mapExtractor
    ) {
        Instance instance = instancesByRegistryIdentifier.computeIfAbsent(registryKey.identifier(), _ -> new Instance());
        return new StreamCodec<>() {

            private IdMap<R> getRegistryOrThrow(RegistryFriendlyByteBuf input) {
                // No changes to the original
                return mapExtractor.apply(input.registryAccess().lookupOrThrow(registryKey));
            }

            public R decode(RegistryFriendlyByteBuf input) {
                int id = VarInt.read(input);
                id = instance.applyServerToClient(id); // Potentially apply a mapping
                return this.getRegistryOrThrow(input).byIdOrThrow(id);
            }

            public void encode(RegistryFriendlyByteBuf output, R value) {
                int id = this.getRegistryOrThrow(output).getIdOrThrow(value);
                id = instance.applyClientToServer(id); // Potentially apply a mapping
                VarInt.write(output, id);
            }

        };
    }

    public static void add(Registry<?> registry, int client, int server) {
        Instance instance = instancesByRegistryIdentifier.get(registry.key().identifier());
        if (instance != null) {
            instance.add(client, server);
        }
    }

    public static void clear() {
        instancesByRegistryIdentifier.forEach((_, instance) -> instance.clear());
    }

    private static class Instance {

        private @Nullable Int2IntMap clientToServer;
        private @Nullable Int2IntMap serverToClient;

        public int applyClientToServer(int id) {
            return this.clientToServer == null ? id : this.clientToServer.getOrDefault(id, id);
        }

        public int applyServerToClient(int id) {
            return this.serverToClient == null ? id : this.serverToClient.getOrDefault(id, id);
        }

        public void add(int client, int server) {
            if (this.clientToServer == null) {
                this.clientToServer = new Int2IntOpenHashMap();
                this.serverToClient = new Int2IntOpenHashMap();
            }
            this.clientToServer.put(client, server);
            this.serverToClient.put(server, client);
        }

        public void clear() {
            this.clientToServer = null;
            this.serverToClient = null;
        }

    }

}
