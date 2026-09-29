package spout.api.clientview.packetmapping.blockstate.macro.registry;

import io.papermc.paper.registry.RegistryBuilder;
import org.jetbrains.annotations.ApiStatus;
import spout.api.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;

/**
 * A data-centric version-specific registry entry for the {@link BlockStateMappingMacro} type.
 */
public interface BlockStateMappingMacroRegistryEntry {

    /**
     * A mutable builder for a {@link BlockStateMappingMacroRegistryEntry}.
     */
    @ApiStatus.NonExtendable
    interface Builder extends BlockStateMappingMacroRegistryEntry, RegistryBuilder<BlockStateMappingMacro> {
    }

}
