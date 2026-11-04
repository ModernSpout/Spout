package spout.gamecontent.builtin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.sounds.AmbientDesertBlockSoundsPlayer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A combination of {@link SandBlock} and {@link StairBlock}.
 */
public class SandStairBlock extends ColoredFallingStairBlock {

    public SandStairBlock(final BlockState baseState, final ColorRGBA dustColor, final BlockBehaviour.Properties properties) {
        super(baseState, dustColor, properties);
    }

    @Override
    public void animateTick(final BlockState state, final Level level, final BlockPos pos, final RandomSource random) {
        super.animateTick(state, level, pos, random);
        AmbientDesertBlockSoundsPlayer.playAmbientSandSounds(level, pos, random);
    }

}
