package spout.gamecontent.builtin.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A union of {@link SlabBlock} and {@link HalfTransparentBlock}.
 */
public class HalfTransparentSlabBlock extends SlabBlock {

    public HalfTransparentSlabBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.is(this) || super.skipRendering(state, adjacentBlockState, side);
    }

}
