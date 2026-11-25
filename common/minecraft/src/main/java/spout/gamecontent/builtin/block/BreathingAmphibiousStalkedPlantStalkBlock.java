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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;
import spout.gamecontent.builtin.block.tag.SpoutBlockTags;

public class BreathingAmphibiousStalkedPlantStalkBlock extends BreathingAmphibiousStalkedPlantBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty BOTTOM = SpoutBlockStateProperties.PLANT_BOTTOM;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public final ResourceKey<Block> top;
    private @Nullable BreathingAmphibiousStalkedPlantTopBlock cachedTop;

    public BreathingAmphibiousStalkedPlantStalkBlock(final ResourceKey<Block> top, final AABB shapeBox, final Properties properties) {
        super(shapeBox, properties);
        this.top = top;
        this.registerDefaultState(this.defaultBlockState().setValue(BOTTOM, true).setValue(WATERLOGGED, false));
    }

    public BreathingAmphibiousStalkedPlantTopBlock getTop() {
        if (this.cachedTop == null) {
            this.cachedTop = (BreathingAmphibiousStalkedPlantTopBlock) BuiltInRegistries.BLOCK.getValue(this.top);
        }
        return this.cachedTop;
    }

    @Override
    public boolean canSurvive(final BlockState state, final LevelReader level, final BlockPos pos) {
        if (!state.getValue(BOTTOM)) {
            if (state.getFluidState().is(Fluids.WATER)) {
                if (!this.canMiddleBeWater()) {
                    return false;
                }
            } else {
                if (!this.canMiddleBeAir()) {
                    return false;
                }
            }
        } else {
            if (state.getFluidState().is(Fluids.WATER)) {
                if (!this.canBottomBeWater()) {
                    return false;
                }
            } else {
                if (!this.canBottomBeAir()) {
                    return false;
                }
            }
        }
        return this.mayPlaceOn(level.getBlockState(pos.below()), level, pos);
    }

    @Override
    protected boolean mayPlaceOn(final BlockState state, final BlockGetter level, final BlockPos pos) {
        return state.is(SpoutBlockTags.SUPPORTS_AMPHIBIOUS_STALKED_PLANTS) || state.is(this);
    }

    public boolean canBottomBePlacedAt(final BlockGetter level, final BlockPos pos) {
        // Verify that the block itself is air or regular water
        BlockState state = level.getBlockState(pos);
        if ((!this.canBottomBeAir() && state.isAir()) || (!this.canBottomBeWater() && state.is(Blocks.WATER))) {
            return false;
        }
        // Verify that there is eventually enough air above, with only air or water in between
        BlockPos above = pos.above();
        int height = 1;
        while (true) {
            height++;
            if (height > this.getMaxHeight()) {
                return false;
            }
            if (above.getY() > level.getMaxY()) {
                return false;
            }
            BlockState aboveState = level.getBlockState(above);
            if (aboveState.isAir()) {
                if (height >= this.getMinHeight()) {
                    return true;
                }
                if (!this.canMiddleBeAir()) {
                    return false;
                }
            } else if (!aboveState.is(Blocks.WATER)) {
                return false;
            } else if (!this.canMiddleBeWater()) {
                return false;
            }
            above = above.above();
        }
    }

    public boolean canBottomBeAir() {
        return true;
    }

    public boolean canBottomBeWater() {
        return true;
    }

    public boolean canMiddleBeAir() {
        return true;
    }

    public boolean canMiddleBeWater() {
        return true;
    }

    public int getMinHeight() {
        return 2;
    }

    public int getMaxHeight() {
        return 4096;
    }

    public void placeBottomAt(final LevelAccessor level, final BlockPos pos, BlockState intendedState) {
        BlockState bottomState = intendedState.setValue(BOTTOM, true).setValue(WATERLOGGED, level.getBlockState(pos).is(Blocks.WATER));
        level.setBlock(pos, bottomState, Block.UPDATE_CLIENTS);
        this.placeMatchingBlocksAbove(level, pos, bottomState);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(final BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        BlockState state = level.getBlockState(pos);

        // Check for modifying the block itself
        if (state.is(this)) {
            return this.getStateForPlacementSelf(context, state);
        } else if (state.is(this.top)) {
            return this.getTop().getStateForPlacementSelf(context, state);
        }

        // Check for valid placement
        if (!this.canBottomBePlacedAt(level, pos)) {
            return null;
        }

        return super.getStateForPlacement(context).setValue(WATERLOGGED, !level.getBlockState(pos).isAir());
    }

    protected @Nullable BlockState getStateForPlacementSelf(final BlockPlaceContext context, final BlockState state) {
        return state;
    }

    public @Nullable BlockPos getTopPos(final LevelReader level, final BlockPos pos) {
        BlockPos.MutableBlockPos checked = new BlockPos.MutableBlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
        while (true) {
            if (checked.getY() - pos.getY() >= this.getMaxHeight()) {
                return null;
            }
            if (checked.getY() > level.getMaxY()) {
                return null;
            }
            BlockState state = level.getBlockState(checked);
            if (state.is(this.top)) {
                return checked.immutable();
            }
            if (state.is(this)) {
                checked.move(0, 1, 0);
                continue;
            }
            return null;
        }
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BOTTOM, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(final BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected ItemStack getCloneItemStack(final LevelReader level, final BlockPos pos, final BlockState state, final boolean includeData) {
        return new ItemStack(this);
    }

    @Override
    public boolean isValidBonemealTarget(final LevelReader level, final BlockPos pos, final BlockState state, final BonemealSource source) {
        return this.isValidBonemealTarget(level, pos);
    }

    public boolean isValidBonemealTarget(final LevelReader level, final BlockPos pos) {
        BlockPos topPos = this.getTopPos(level, pos);
        return topPos != null && this.getTop().isValidBonemealTarget(level, topPos);
    }

    @Override
    public boolean isBonemealSuccess(final Level level, final RandomSource random, final BlockPos pos, final BlockState state, final BonemealSource source) {
        return true;
    }

    @Override
    public void performBonemeal(final ServerLevel level, final RandomSource random, final BlockPos pos, final BlockState state, final BonemealSource source) {
        BlockPos topPos = this.getTopPos(level, pos);
        if (topPos != null) {
            this.getTop().performBonemeal(level, random, topPos, level.getBlockState(topPos), source);
        }
    }

    public boolean growUp(final LevelAccessor level, final BlockPos pos) {
        BlockPos topPos = this.getTopPos(level, pos);
        if (topPos != null) {
            return this.getTop().growUp(level, topPos);
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
        // Check for break above
        if (directionToNeighbour == Direction.UP) {
            if (!(neighbourState.is(this) || neighbourState.is(this.top))) {
                return state.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
            }
        }
        // Check for survival
        if (!state.canSurvive(level, pos)) {
            ticks.scheduleTick(pos, this, 1);
        }
        // Check for water
        if (state.getValue(WATERLOGGED)) {
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    public void spreadMatchingPropertiesUpAndDown(final LevelAccessor level, final BlockPos pos, final BlockState state) {
        BlockPos.MutableBlockPos changingPos = new BlockPos.MutableBlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
        while (true) {
            if (changingPos.getY() > level.getMaxY()) {
                break;
            }
            BlockState oldChangingState = level.getBlockState(changingPos);
            if (oldChangingState.is(this.top)) {
                level.setBlockAndUpdate(changingPos, this.getMatchingTopState(state, oldChangingState));
                break;
            }
            if (oldChangingState.is(this)) {
                level.setBlockAndUpdate(changingPos, this.getReplacementStalkState(state, oldChangingState));
                changingPos.move(0, 1, 0);
                continue;
            }
            break;
        }
        changingPos.set(pos.getX(), pos.getY() - 1, pos.getZ());
        while (true) {
            if (changingPos.getY() < level.getMinY()) {
                break;
            }
            BlockState oldChangingState = level.getBlockState(changingPos);
            if (oldChangingState.is(this)) {
                level.setBlockAndUpdate(changingPos, this.getReplacementStalkState(state, oldChangingState));
                changingPos.move(0, -1, 0);
                continue;
            }
            break;
        }
    }

    public void placeMatchingBlocksAbove(final LevelAccessor level, final BlockPos pos, final BlockState state) {
        BlockPos.MutableBlockPos changingPos = new BlockPos.MutableBlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
        int height = 1;
        while (true) {
            height++;
            if (height > this.getMaxHeight()) {
                break;
            }
            if (changingPos.getY() > level.getMaxY()) {
                break;
            }
            BlockState oldChangingState = level.getBlockState(changingPos);
            if (oldChangingState.isAir()) {
                if (height >= this.getMinHeight()) {
                    level.setBlockAndUpdate(changingPos, this.getMatchingTopState(state, this.getTop().defaultBlockState()));
                    break;
                }
                level.setBlockAndUpdate(changingPos, state.setValue(BOTTOM, false).setValue(WATERLOGGED, false));
            } else if (oldChangingState.is(Blocks.WATER)) {
                level.setBlockAndUpdate(changingPos, state.setValue(BOTTOM, false).setValue(WATERLOGGED, true));
            } else {
                break;
            }
            changingPos.move(0, 1, 0);
        }
    }

    @Override
    public void setPlacedBy(final Level level, final BlockPos pos, final BlockState state, final @Nullable LivingEntity by, final ItemStack itemStack) {
        if (!this.isNewlyPlaced(state)) {
            this.spreadMatchingPropertiesUpAndDown(level, pos, state);
            return;
        }
        this.placeMatchingBlocksAbove(level, pos, state);
    }

    protected boolean isNewlyPlaced(final BlockState state) {
        return state.getValue(BOTTOM);
    }

    protected BlockState getMatchingTopState(final BlockState state, final BlockState topState) {
        return topState;
    }

    protected BlockState getReplacementStalkState(final BlockState reference, final BlockState toModify) {
        return toModify;
    }

}
