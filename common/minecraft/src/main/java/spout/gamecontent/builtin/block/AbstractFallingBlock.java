package spout.gamecontent.builtin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A common base for combinations of {@link FallingBlock} and other classes.
 */
public interface AbstractFallingBlock extends Fallable {

    default void onPlaceFalling(final BlockState state, final Level level, final BlockPos pos, final BlockState oldState, final boolean movedByPiston) {
        level.scheduleTick(pos, (Block) this, this.getDelayAfterPlace());
    }

    default void tickFalling(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
        if (FallingBlock.isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinY()) {
            FallingBlockEntity entity = FallingBlockEntity.fall(level, pos, state);
            this.falling(entity);
        }
    }

    default void falling(final FallingBlockEntity entity) {
    }

    default int getDelayAfterPlace() {
        return 2;
    }

    default void animateTickFalling(final BlockState state, final Level level, final BlockPos pos, final RandomSource random) {
        if (random.nextInt(16) == 0) {
            BlockPos below = pos.below();
            if (FallingBlock.isFree(level.getBlockState(below))) {
                ParticleUtils.spawnParticleBelow(level, pos, random, new BlockParticleOption(ParticleTypes.FALLING_DUST, state));
            }
        }
    }

    int getDustColor(BlockState blockState, BlockGetter level, BlockPos pos);

}
