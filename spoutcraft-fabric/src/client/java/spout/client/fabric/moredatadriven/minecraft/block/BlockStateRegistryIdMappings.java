package spout.client.fabric.moredatadriven.minecraft.block;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import org.jspecify.annotations.Nullable;

/**
 * Keeps track of any block state id mappings that must be applied.
 */
public final class BlockStateRegistryIdMappings {

    private BlockStateRegistryIdMappings() {
        throw new UnsupportedOperationException();
    }

    private static @Nullable Int2IntMap clientToServer;
    private static @Nullable Int2IntMap serverToClient;

    public static int applyClientToServer(int id) {
        return clientToServer == null ? id : clientToServer.getOrDefault(id, id);
    }

    public static int applyServerToClient(int id) {
        return serverToClient == null ? id : serverToClient.getOrDefault(id, id);
    }

    public static void add(int client, int server) {
        if (clientToServer == null) {
            clientToServer = new Int2IntOpenHashMap();
            serverToClient = new Int2IntOpenHashMap();
        }
        clientToServer.put(client, server);
        serverToClient.put(server, client);
    }

    public static void clear() {
        clientToServer = null;
        serverToClient = null;
    }

}
