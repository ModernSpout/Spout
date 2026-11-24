package spout.gamecontent.builtin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public abstract class BreathingAmphibiousStalkedPlantTopBlock extends BushBlock implements BonemealableBlock {

    public final ResourceKey<Block> stalk;
    private @Nullable BreathingAmphibiousStalkedPlantStalkBlock cachedStalk;

    public BreathingAmphibiousStalkedPlantTopBlock(final ResourceKey<Block> stalk, final Properties properties) {
        super(properties);
        this.stalk = stalk;
    }

    public BreathingAmphibiousStalkedPlantStalkBlock getStalk() {
        if (this.cachedStalk == null) {
            this.cachedStalk = (BreathingAmphibiousStalkedPlantStalkBlock) BuiltInRegistries.BLOCK.getValue(this.stalk);
        }
        return this.cachedStalk;
    }

    public abstract VoxelShape getShape();

    @Override
    public VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return this.getShape().move(state.getOffset(pos));
    }

    @Override
    public boolean canSurvive(final BlockState state, final LevelReader level, final BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        return belowState.is(this.stalk) || belowState.is(this); // State below can be temporarily a top block while growing
    }

    @Override
    protected boolean mayPlaceOn(final BlockState state, final BlockGetter level, final BlockPos pos) {
        return this.getStalk().mayPlaceOn(state, level, pos);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(final BlockPlaceContext context) {
        return this.getStalk().getStateForPlacement(context);
    }

    protected @Nullable BlockState getStateForPlacementSelf(final BlockPlaceContext context, final BlockState state) {
        return state;
    }

    @Override
    protected ItemStack getCloneItemStack(final LevelReader level, final BlockPos pos, final BlockState state, final boolean includeData) {
        return new ItemStack(this.getStalk());
    }

    @Override
    public boolean isValidBonemealTarget(final LevelReader level, final BlockPos pos, final BlockState state, final BonemealSource source) {
        return pos.getY() < level.getMaxY() && level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(final Level level, final RandomSource random, final BlockPos pos, final BlockState state, final BonemealSource source) {
        return true;
    }

    @Override
    public void performBonemeal(final ServerLevel level, final RandomSource random, final BlockPos pos, final BlockState state, final BonemealSource source) {
        this.growUp(level, pos, state, source);
    }

    public boolean growUp(final ServerLevel level, final BlockPos pos, final BlockState state, final BonemealSource source) {
        if (this.isValidBonemealTarget(level, pos, state, source)) {
            level.setBlockAndUpdate(pos.above(), state);
            return true;
        }
        return false;
    }

    @Override
    public void tick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
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
        // Check for post-growth
        if (directionToNeighbour == Direction.UP) {
            if (neighbourState.is(this)) {
                return this.getMatchingStalkState(state);
            }
        }
        // Check for survival
        if (!state.canSurvive(level, pos)) {
            ticks.scheduleTick(pos, this, 1);
        }
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    public void setPlacedBy(final Level level, final BlockPos pos, final BlockState state, final @Nullable LivingEntity by, final ItemStack itemStack) {
        if (!this.isNewlyPlaced(state)) {
            BlockPos.MutableBlockPos changing = new BlockPos.MutableBlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
            while (true) {
                if (changing.getY() < level.getMinY()) {
                    break;
                }
                BlockState oldChangingState = level.getBlockState(changing);
                if (oldChangingState.is(this.stalk)) {
                    level.setBlockAndUpdate(changing, this.getReplacementStalkState(state, oldChangingState));
                    changing.move(0, -1, 0);
                    continue;
                }
                break;
            }
        }
    }

    protected boolean isNewlyPlaced(final BlockState state) {
        return true;
    }

    protected BlockState getMatchingStalkState(final BlockState state) {
        return this.getStalk().defaultBlockState().setValue(BreathingAmphibiousStalkedPlantStalkBlock.BOTTOM, false).setValue(BreathingAmphibiousStalkedPlantStalkBlock.WATERLOGGED, false);
    }

    protected BlockState getReplacementStalkState(final BlockState reference, final BlockState toModify) {
        return toModify;
    }

}
