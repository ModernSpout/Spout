package spout.gamecontent.builtin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * A {@link FlowerBlock} with a large hitbox.
 */
public class BroadFlowerBlock extends FlowerBlock {

    public BroadFlowerBlock(final Holder<MobEffect> suspiciousStewEffect, final float effectSeconds, final Properties properties) {
        super(suspiciousStewEffect, effectSeconds, properties);
    }

    public BroadFlowerBlock(final SuspiciousStewEffects suspiciousStewEffects, final Properties properties) {
        super(suspiciousStewEffects, properties);
    }

    @Override
    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return TallGrassBlock.SHAPE;
    }

}
