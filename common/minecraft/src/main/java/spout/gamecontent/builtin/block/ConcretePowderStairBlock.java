package spout.gamecontent.builtin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A combination of {@link ConcretePowderBlock} and {@link StairBlock}.
 */
public class ConcretePowderStairBlock extends FallingStairBlock implements AbstractConcretePowderBlock {

    public final Block concrete;

    public ConcretePowderStairBlock(final BlockState baseState, final Block concrete, final Properties properties) {
        super(baseState, properties);
        this.concrete = concrete;
    }

    @Override
    public void onLand(final Level level, final BlockPos pos, final BlockState state, final BlockState replacedBlock, final FallingBlockEntity entity) {
        this.onLandConcretePowder(level, pos, state, replacedBlock, entity);
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        return this.getStateForPlacementConcretePowder(context, super::getStateForPlacement);
    }

    @Override
    protected BlockState updateShape(
        final BlockState state,
        final LevelReader level,
        final ScheduledTickAccess ticks,
        final BlockPos pos,
        final Direction directionToNeighbour,
        final BlockPos neighbourPos,
        final BlockState neighbourState,
        final RandomSource random
    ) {
        return this.updateShapeConcretePowder(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random, super::updateShape);
    }

    @Override
    public Block getConcrete() {
        return this.concrete;
    }

}
