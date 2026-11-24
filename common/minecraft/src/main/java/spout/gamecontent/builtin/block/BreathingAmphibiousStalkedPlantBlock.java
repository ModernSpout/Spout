package spout.gamecontent.builtin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BreathingAmphibiousStalkedPlantBlock extends BushBlock implements BonemealableBlock {

    public final VoxelShape shape;

    public BreathingAmphibiousStalkedPlantBlock(final double shapeMinX, final double shapeMinY, final double shapeMinZ, final double shapeMaxX, final double shapeMaxY, final double shapeMaxZ, final Properties properties) {
        super(properties);
        this.shape = Block.box(shapeMinX, shapeMinY, shapeMinZ, shapeMaxX, shapeMaxY, shapeMaxZ);
    }

    @Override
    public VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return this.shape.move(state.getOffset(pos));
    }

}
