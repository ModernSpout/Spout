package spout.gamecontent.builtin.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A union of {@link StairBlock} and {@link HalfTransparentBlock}.
 */
public class HalfTransparentStairBlock extends StairBlock {

    public HalfTransparentStairBlock(BlockState baseState, Properties properties) {
        super(baseState, properties);
    }

    @Override
    protected boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.is(this) || super.skipRendering(state, adjacentBlockState, side);
    }

}
