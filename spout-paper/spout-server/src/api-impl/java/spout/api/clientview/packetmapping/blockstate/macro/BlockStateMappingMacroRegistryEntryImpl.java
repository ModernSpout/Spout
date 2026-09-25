package spout.api.clientview.packetmapping.blockstate.macro;

import io.papermc.paper.registry.PaperRegistryBuilder;
import io.papermc.paper.registry.data.util.Conversions;

/**
 * The implementation for {@link BlockStateMappingMacroRegistryEntry}.
 */
public class BlockStateMappingMacroRegistryEntryImpl implements BlockStateMappingMacroRegistryEntry {

    public BlockStateMappingMacroRegistryEntryImpl(
        final Conversions ignoredConversions,
        final spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro internal
    ) {
        if (internal == null) return;
    }

    /**
     * The implementation for {@link BlockStateMappingMacroRegistryEntry.Builder}.
     */
    public static final class Builder extends BlockStateMappingMacroRegistryEntryImpl implements BlockStateMappingMacroRegistryEntry.Builder,
        PaperRegistryBuilder<spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro, BlockStateMappingMacro> {

        public Builder(
            final Conversions conversions,
            final spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro internal
        ) {
            super(conversions, internal);
        }

        @Override
        public spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro build() {
            throw new UnsupportedOperationException("Not implemented yet"); // TODO implement
        }

    }

}
