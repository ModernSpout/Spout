package spout.gamecontent.builtin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import spout.gamecontent.datadriven.block.subtypes.BlockStateFunction;

public abstract class BreathingAmphibiousStalkedPlantBlock extends BushBlock implements BonemealableBlock, DataDrivenShapeBlock {

    public final BlockStateFunction<AABB> shape;
    public final BlockStateFunction<VoxelShape> voxelShape;

    public BreathingAmphibiousStalkedPlantBlock(final BlockStateFunction<AABB> shape, final Properties properties) {
        super(properties);
        this.shape = shape;
        this.voxelShape = this.shape.map(it -> Block.box(it.minX, it.minY, it.minZ, it.maxX, it.maxY, it.maxZ));
    }

    @Override
    public BlockStateFunction<AABB> getShape() {
        return this.shape;
    }

    @Override
    public VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return this.voxelShape.apply(state).move(state.getOffset(pos));
    }

}
