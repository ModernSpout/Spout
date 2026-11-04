package spout.gamecontent.builtin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A combination of {@link ColoredFallingBlock} and {@link SlabBlock}.
 */
public class ColoredFallingSlabBlock extends FallingSlabBlock {

    public final ColorRGBA dustColor;

    public ColoredFallingSlabBlock(final ColorRGBA dustColor, final BlockBehaviour.Properties properties) {
        super(properties);
        this.dustColor = dustColor;
    }

    @Override
    public int getDustColor(final BlockState blockState, final BlockGetter level, final BlockPos pos) {
        return this.dustColor.rgba();
    }

}
