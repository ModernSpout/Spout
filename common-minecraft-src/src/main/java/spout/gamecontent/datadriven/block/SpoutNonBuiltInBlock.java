package spout.gamecontent.datadriven.block;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.block.Block;
import spout.gamecontent.datadriven.blocktype.BuiltInBlockTypeRegistry;
import spout.gamecontent.datadriven.blocktype.SpoutBlockType;
import spout.gamecontent.datadriven.common.nonbuiltin.SpoutNonBuiltInResource;
import spout.util.mojang.codec.CodecUtil;
import spout.util.mojang.codec.MapInputAndOps;

/**
 * A {@link SpoutNonBuiltInResource} for {@link Block}.
 */
public class SpoutNonBuiltInBlock extends SpoutNonBuiltInResource<Block, SpoutBlockType> {

    private static final MapCodec<SpoutNonBuiltInBlock> CODEC_WITHOUT_CONTEXT = codec(BuiltInBlockTypeRegistry.BLOCK_TYPE);

    /**
     * A codec for {@link SpoutNonBuiltInBlock}s.
     */
    public static final Codec<SpoutNonBuiltInBlock> CODEC = new Codec<>() {

        @Override
        public <T> DataResult<T> encode(SpoutNonBuiltInBlock input, DynamicOps<T> dynamicOps, T prefix) {
            ContextAwareBlockPropertiesEncoding.setBlock(input.getValue());
            DataResult<T> result = CODEC_WITHOUT_CONTEXT.encoder().encode(input, dynamicOps, prefix);
            ContextAwareBlockPropertiesEncoding.clearBlock();
            return result;
        }

        @Override
        public <T> DataResult<Pair<SpoutNonBuiltInBlock, T>> decode(DynamicOps<T> dynamicOps, T input) {
            return CODEC_WITHOUT_CONTEXT.decoder().decode(dynamicOps, input);
        }

    };

    /**
     * A stream codec for {@link SpoutNonBuiltInBlock}s.
     */
    public static final StreamCodec<FriendlyByteBuf, SpoutNonBuiltInBlock> STREAM_CODEC = CodecUtil.streamViaNBT(CODEC);

    public SpoutNonBuiltInBlock(SpoutBlockType type, MapInputAndOps<?> input) {
        super(type, input);
    }

    public SpoutNonBuiltInBlock(Block value) {
        super(value);
    }

    @Override
    protected SpoutBlockType valueToType(Block value) {
        return ((BlockTypeDecorator) value).spout$getBlockType();
    }

    @Override
    protected Block decodeInput(final MapInputAndOps<?> input) {
        ContextAwareBlockPropertiesDecoding.setType(this.type);
        Block result = super.decodeInput(input);
        ContextAwareBlockPropertiesDecoding.clearType();
        return result;
    }

}
