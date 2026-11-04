package spout.gamecontent.builtin.block;

import com.mojang.datafixers.util.Function8;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A common base for combinations of {@link ConcretePowderBlock} and other classes.
 */
public interface AbstractConcretePowderBlock extends AbstractFallingBlock {

    default void onLandConcretePowder(final Level level, final BlockPos pos, final BlockState state, final BlockState replacedBlock, final FallingBlockEntity entity) {
        if (ConcretePowderBlock.shouldSolidify(level, pos, replacedBlock)) {
            level.setBlockAndUpdate(pos, this.getConcrete().defaultBlockState().withPropertiesOf(state));
        }
    }

    default BlockState getStateForPlacementConcretePowder(final BlockPlaceContext context, final Function<BlockPlaceContext, BlockState> superMethod) {
        BlockGetter level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState replacedBlock = level.getBlockState(pos);
        return ConcretePowderBlock.shouldSolidify(level, pos, replacedBlock) ? this.getConcrete().getStateForPlacement(context) : superMethod.apply(context);
    }

    default BlockState updateShapeConcretePowder(
        final BlockState state,
        final LevelReader level,
        final ScheduledTickAccess ticks,
        final BlockPos pos,
        final Direction directionToNeighbour,
        final BlockPos neighbourPos,
        final BlockState neighbourState,
        final RandomSource random,
        final Function8<BlockState, LevelReader, ScheduledTickAccess, BlockPos, Direction, BlockPos, BlockState, RandomSource, BlockState> superMethod
    ) {
        return ConcretePowderBlockTouchesLiquid.touchesLiquid(level, pos, state) ? this.getConcrete().defaultBlockState().withPropertiesOf(state) : superMethod.apply(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    default int getDustColor(final BlockState blockState, final BlockGetter level, final BlockPos pos) {
        return blockState.getMapColor(level, pos).col;
    }

    Block getConcrete();

}
