package spout.api;

import io.papermc.paper.registry.event.RegistryEventProvider;
import io.papermc.paper.registry.event.RegistryEventProviderImpl;
import io.papermc.paper.registry.event.RegistryEvents;
import spout.api.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.api.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.api.clientview.packetmapping.blockstate.macro.registry.BlockStateMappingMacroRegistryEntry;
import spout.api.clientview.packetmapping.blockstate.registry.BlockStateMappingRegistryEntry;
import spout.api.clientview.packetmapping.component.ComponentMapping;
import spout.api.clientview.packetmapping.component.registry.ComponentMappingRegistryEntry;
import spout.api.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.api.clientview.packetmapping.itemstack.registry.ItemStackMappingRegistryEntry;

/**
 * Analogous to {@link RegistryEvents}.
 */
public final class SpoutRegistryEvents {

    private SpoutRegistryEvents() {
        throw new UnsupportedOperationException();
    }

    /**
     * Events for {@link SpoutRegistryKey#COMPONENT_MAPPING}.
     */
    public static final RegistryEventProvider<ComponentMapping, ComponentMappingRegistryEntry.Builder> COMPONENT_MAPPING = RegistryEventProviderImpl.create(SpoutRegistryKey.COMPONENT_MAPPING);

    /**
     * Events for {@link SpoutRegistryKey#BLOCK_STATE_MAPPING_MACRO}.
     */
    public static final RegistryEventProvider<BlockStateMappingMacro, BlockStateMappingMacroRegistryEntry.Builder> BLOCK_STATE_MAPPING_MACRO = RegistryEventProviderImpl.create(SpoutRegistryKey.BLOCK_STATE_MAPPING_MACRO);

    /**
     * Events for {@link SpoutRegistryKey#BLOCK_STATE_MAPPING}.
     */
    public static final RegistryEventProvider<BlockStateMapping, BlockStateMappingRegistryEntry.Builder> BLOCK_STATE_MAPPING = RegistryEventProviderImpl.create(SpoutRegistryKey.BLOCK_STATE_MAPPING);

    /**
     * Events for {@link SpoutRegistryKey#ITEM_STACK_MAPPING}.
     */
    public static final RegistryEventProvider<ItemStackMapping, ItemStackMappingRegistryEntry.Builder> ITEM_STACK_MAPPING = RegistryEventProviderImpl.create(SpoutRegistryKey.ITEM_STACK_MAPPING);

}
