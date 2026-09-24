package spout.api;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.RegistryKeyImpl;
import spout.api.clientview.packetmapping.blockstate.registry.BlockStateMapping;
import spout.branding.SpoutNamespace;

/**
 * Analogous to {@link RegistryKey}.
 */
public final class SpoutRegistryKey {

    private SpoutRegistryKey() {
        throw new UnsupportedOperationException();
    }

    /**
     * Data-driven registry for block state mappings.
     */
    public static final RegistryKey<BlockStateMapping> BLOCK_STATE_MAPPING = RegistryKeyImpl.create(SpoutNamespace.SPOUT + ":block_state_mapping");

}
