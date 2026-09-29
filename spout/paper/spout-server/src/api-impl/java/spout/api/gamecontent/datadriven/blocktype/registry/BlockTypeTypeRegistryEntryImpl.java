package spout.api.gamecontent.datadriven.blocktype.registry;

import io.papermc.paper.registry.PaperRegistryBuilder;
import io.papermc.paper.registry.data.util.Conversions;
import spout.gamecontent.datadriven.blocktype.SpoutBlockType;
import spout.api.gamecontent.datadriven.blocktype.BlockTypeType;
import org.jspecify.annotations.Nullable;

/**
 * Implementation for {@link BlockTypeTypeRegistryEntry}.
 */
public class BlockTypeTypeRegistryEntryImpl implements BlockTypeTypeRegistryEntryNMS {

    protected @Nullable SpoutBlockType blockType;

    public BlockTypeTypeRegistryEntryImpl(Conversions conversions, @Nullable SpoutBlockType internal) {
        this.blockType = internal;
    }

    // @Override
    // public WrappedBlockCodec<?> getWrappedCodec() {
    //     return this.wrappedCodec;
    // }

    public static class BuilderImpl extends BlockTypeTypeRegistryEntryImpl implements BlockTypeTypeRegistryEntryNMS.Builder, PaperRegistryBuilder<SpoutBlockType, BlockTypeType> {

        public BuilderImpl(Conversions conversions, @Nullable SpoutBlockType internal) {
            super(conversions, internal);
        }

        // @Override
        // public void setCodec(MapCodec<? extends Block> codecForType) {
        //     this.wrappedCodec = WrappedBlockCodecImpl.wrap(codecForType);
        // }

        @Override
        public SpoutBlockType build() {
            return this.blockType;
        }

    }

}
