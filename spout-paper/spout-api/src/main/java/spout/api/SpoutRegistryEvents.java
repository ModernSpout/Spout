package spout.api;

import io.papermc.paper.registry.event.RegistryEventProvider;
import io.papermc.paper.registry.event.RegistryEventProviderImpl;
import io.papermc.paper.registry.event.RegistryEvents;
import spout.api.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.api.clientview.packetmapping.blockstate.registry.BlockStateMappingRegistryEntry;

/**
 * Analogous to {@link RegistryEvents}.
 */
public final class SpoutRegistryEvents {

    private SpoutRegistryEvents() {
        throw new UnsupportedOperationException();
    }

    /**
     * Events for {@link SpoutRegistryKey#BLOCK_STATE_MAPPING}.
     */
    public static final RegistryEventProvider<BlockStateMapping, BlockStateMappingRegistryEntry.Builder> BLOCK_STATE_MAPPING = RegistryEventProviderImpl.create(SpoutRegistryKey.BLOCK_STATE_MAPPING);

}
