package spout.gamecontent.datadriven.block;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import spout.util.minecraft.blockstate.BlockStateStringConversion;

/**
 * Holder for {@link #CODEC}.
 */
public final class FormattedBlockStateCodec {

    private FormattedBlockStateCodec() {
        throw new UnsupportedOperationException();
    }

    /**
     * A {@link BlockState} codec that accepts the regular format used in commands
     * (e.g. {@code "stone"} or {@code "minecraft:dirt"} or {@code "willow_trees:willow_leaves[waterlogged=true]"}).
     */
    public static final Codec<BlockState> CODEC = Codec.STRING.xmap(
        BlockStateStringConversion::blockStateFromString,
        BlockStateStringConversion::blockStateToString
    );

}
