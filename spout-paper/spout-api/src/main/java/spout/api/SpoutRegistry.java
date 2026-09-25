package spout.api;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.RegistryKeyImpl;
import org.bukkit.Keyed;
import org.bukkit.Registry;
import spout.api.clientview.packetmapping.blockstate.registry.BlockStateMapping;
import spout.branding.SpoutNamespace;

/**
 * Analogous to {@link Registry}.
 */
public final class SpoutRegistry {

    private SpoutRegistry() {
        throw new UnsupportedOperationException();
    }

    /**
     * Data-driven registry for block state mappings.
     */
    public static final Registry<BlockStateMapping> BLOCK_STATE_MAPPING = registryFor(SpoutRegistryKey.BLOCK_STATE_MAPPING);

    private static <A extends Keyed> Registry<A> registryFor(final RegistryKey<A> registryKey) {
        return RegistryAccess.registryAccess().getRegistry(registryKey);
    }

}
