package spout.gamecontent.builtin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import spout.gamecontent.datadriven.block.subtypes.BlockStateFunction;

public class LongHangingPlantBlock extends Block implements BonemealableBlock, DataDrivenShapeBlock {

    public static final EnumProperty<LongPlantPart> PART = SpoutBlockStateProperties.LONG_PLANT_PART;

    public final BlockStateFunction<AABB> shape;
    public final BlockStateFunction<VoxelShape> voxelShape;
    public final boolean requiresTint;

    public LongHangingPlantBlock(final BlockStateFunction<AABB> shape, boolean requiresTint, final Properties properties) {
        super(properties);
        this.shape = shape;
        this.voxelShape = shape.map(it -> Block.box(it.minX, it.minY, it.minZ, it.maxX, it.maxY, it.maxZ));
        this.requiresTint = requiresTint;
        this.registerDefaultState(defaultBlockState().setValue(PART, LongPlantPart.SINGLE));
    }

    @Override
    public BlockStateFunction<AABB> getShape() {
        return this.shape;
    }

    @Override
    public VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return this.voxelShape.apply(state);
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART);
    }

    @Override
    protected boolean canSurvive(final BlockState state, final LevelReader level, final BlockPos pos) {
        if (pos.getY() >= level.getMaxY()) {
            return false;
        }
        BlockState aboveState = level.getBlockState(pos.above());
        LongPlantPart part = state.getValue(PART);
        return (part == LongPlantPart.TOP || part == LongPlantPart.SINGLE) ? !(aboveState.isAir() || aboveState.is(Blocks.WATER) || aboveState.is(Blocks.LAVA)) : aboveState.is(this);
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
        if (directionToNeighbour == Direction.UP) {
            if (this.canSurvive(state, level, pos)) {
                BlockState belowState = level.getBlockState(pos.below());
                if (neighbourState.is(this)) {
                    if (belowState.is(this)) {
                        return defaultBlockState().setValue(PART, LongPlantPart.MIDDLE);
                    } else {
                        return defaultBlockState().setValue(PART, LongPlantPart.BOTTOM);
                    }
                } else {
                    if (belowState.is(this)) {
                        return defaultBlockState().setValue(PART, LongPlantPart.TOP);
                    } else {
                        return defaultBlockState().setValue(PART, LongPlantPart.SINGLE);
                    }
                }
            } else {
                ticks.scheduleTick(pos, this, 1);
                return state;
            }
        } else if (directionToNeighbour == Direction.DOWN) {
            BlockState aboveState = level.getBlockState(pos.above());
            if (neighbourState.is(this)) {
                if (aboveState.is(this)) {
                    return defaultBlockState().setValue(PART, LongPlantPart.MIDDLE);
                } else {
                    return defaultBlockState().setValue(PART, LongPlantPart.TOP);
                }
            } else {
                if (aboveState.is(this)) {
                    return defaultBlockState().setValue(PART, LongPlantPart.BOTTOM);
                } else {
                    return defaultBlockState().setValue(PART, LongPlantPart.SINGLE);
                }
            }
        }
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected void tick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean isValidBonemealTarget(final LevelReader level, final BlockPos pos, final BlockState state, final BonemealSource source) {
        BlockPos.MutableBlockPos relativePos = new BlockPos.MutableBlockPos();
        relativePos.set(pos);
        while (true) {
            relativePos.move(0, -1, 0);
            if (relativePos.getY() < level.getMinY()) {
                return false;
            }
            BlockState relativeState = level.getBlockState(relativePos);
            if (relativeState.isAir()) {
                return true;
            } else if (!relativeState.is(this)) {
                return false;
            }
        }
    }

    @Override
    public boolean isBonemealSuccess(final Level level, final RandomSource random, final BlockPos pos, final BlockState state, final BonemealSource source) {
        return true;
    }

    @Override
    public void performBonemeal(final ServerLevel level, final RandomSource random, final BlockPos pos, final BlockState state, final BonemealSource source) {
        BlockPos.MutableBlockPos relativePos = new BlockPos.MutableBlockPos();
        relativePos.set(pos);
        while (true) {
            relativePos.move(0, -1, 0);
            if (relativePos.getY() < level.getMinY()) {
                return;
            }
            BlockState relativeState = level.getBlockState(relativePos);
            if (relativeState.isAir()) {
                BlockPos aboveRelativePos = relativePos.above();
                BlockState aboveRelativeState = level.getBlockState(aboveRelativePos);
                if (aboveRelativeState.is(this)) {
                    level.setBlock(aboveRelativePos, defaultBlockState().setValue(PART, aboveRelativeState.getValue(PART) == LongPlantPart.SINGLE ? LongPlantPart.TOP : LongPlantPart.MIDDLE), Block.UPDATE_CLIENTS);
                    level.setBlock(relativePos, defaultBlockState().setValue(PART, LongPlantPart.BOTTOM), Block.UPDATE_CLIENTS);
                }
                return;
            } else if (!relativeState.is(this)) {
                return;
            }
        }
    }

}
