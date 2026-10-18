package spout.api.clientview.packetmapping.blockstate.macro.registry;

import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;

/**
 * A {@link BlockStateMappingMacroRegistryEntry.Builder} that allows using Minecraft internals.
 */
public interface BlockStateMappingMacroRegistryEntryBuilderNMS extends BlockStateMappingMacroRegistryEntry.Builder {

    void set(BlockStateMappingMacro macro);

}
