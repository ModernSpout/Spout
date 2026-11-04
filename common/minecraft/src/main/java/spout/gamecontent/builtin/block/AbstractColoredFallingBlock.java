package spout.gamecontent.builtin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A common base for combinations of {@link ColoredFallingSlabBlock} and other classes.
 */
public interface AbstractColoredFallingBlock extends AbstractFallingBlock {

    @Override
    default int getDustColor(BlockState blockState, BlockGetter level, BlockPos pos) {
        return this.getDustColor().rgba();
    }

    ColorRGBA getDustColor();

}
