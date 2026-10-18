package spout.api.clientview.packetmapping.blockstate.macro.registry;

import io.papermc.paper.registry.PaperRegistryBuilder;
import io.papermc.paper.registry.data.util.Conversions;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import java.util.Objects;

/**
 * The implementation for {@link BlockStateMappingMacroRegistryEntry}.
 */
public class BlockStateMappingMacroRegistryEntryImpl implements BlockStateMappingMacroRegistryEntry {

    protected @Nullable BlockStateMappingMacro direct;

    public BlockStateMappingMacroRegistryEntryImpl(
        final Conversions ignoredConversions,
        final BlockStateMappingMacro internal
    ) {
        if (internal == null) return;
        this.direct = internal;
    }

    /**
     * The implementation for {@link BlockStateMappingMacroRegistryEntry.Builder}.
     */
    public static final class Builder extends BlockStateMappingMacroRegistryEntryImpl implements BlockStateMappingMacroRegistryEntryBuilderNMS,
        PaperRegistryBuilder<BlockStateMappingMacro, spout.api.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro> {

        public Builder(
            final Conversions conversions,
            final BlockStateMappingMacro internal
        ) {
            super(conversions, internal);
        }

        @Override
        public void set(final BlockStateMappingMacro macro) {
            this.direct = macro;
        }

        @Override
        public BlockStateMappingMacro build() {
            return Objects.requireNonNull(this.direct, "No block state mapping macro defined");
        }

    }

}
