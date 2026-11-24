package spout.gamecontent.builtin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jspecify.annotations.Nullable;

public class CattailTopBlock extends BreathingAmphibiousStalkedPlantTopBlock {

    public static final IntegerProperty COUNT = SpoutBlockStateProperties.CATTAIL_COUNT;
    public static final BooleanProperty FLUFFY = SpoutBlockStateProperties.CATTAIL_FLUFFY;

    public CattailTopBlock(final ResourceKey<Block> stalk, final Properties properties) {
        super(stalk, 2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(COUNT, 1).setValue(FLUFFY, false));
    }

    @Override
    protected @Nullable BlockState getStateForPlacementSelf(final BlockPlaceContext context, final BlockState state) {
        return state.setValue(COUNT, Math.min(COUNT.max, state.getValue(COUNT) + 1));
    }

    @Override
    public boolean canBeReplaced(final BlockState state, final BlockPlaceContext context) {
        return !context.isSecondaryUseActive()
            && context.getItemInHand().getItem() == this.getStalk().asItem()
            && state.getValue(COUNT) < COUNT.max;
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COUNT, FLUFFY);
    }

    @Override
    public BlockState updateShape(
        final BlockState state,
        final LevelReader level,
        final ScheduledTickAccess ticks,
        final BlockPos pos,
        final Direction directionToNeighbour,
        final BlockPos neighbourPos,
        final BlockState neighbourState,
        final RandomSource random
    ) {
        // Check for count increase
        if (directionToNeighbour == Direction.DOWN || directionToNeighbour == Direction.UP) {
            if (neighbourState.is(this) || neighbourState.is(this.stalk)) {
                int newValue = neighbourState.getValue(COUNT);
                if (newValue > state.getValue(COUNT)) {
                    return state.setValue(COUNT, newValue);
                }
            }
        }
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected boolean isNewlyPlaced(final BlockState state) {
        return state.getValue(COUNT) == 1;
    }

    @Override
    protected BlockState getMatchingStalkState(final BlockState state) {
        return super.getMatchingStalkState(state).setValue(COUNT, state.getValue(COUNT));
    }

    @Override
    protected BlockState getReplacementStalkState(final BlockState reference, final BlockState toModify) {
        return toModify.setValue(COUNT, reference.getValue(COUNT));
    }

}
