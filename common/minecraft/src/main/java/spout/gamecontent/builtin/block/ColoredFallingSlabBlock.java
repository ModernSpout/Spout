package spout.gamecontent.builtin.block;

import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * A combination of {@link ColoredFallingBlock} and {@link SlabBlock}.
 */
public class ColoredFallingSlabBlock extends FallingSlabBlock implements AbstractColoredFallingBlock {

    public final ColorRGBA dustColor;

    public ColoredFallingSlabBlock(final ColorRGBA dustColor, final BlockBehaviour.Properties properties) {
        super(properties);
        this.dustColor = dustColor;
    }

    @Override
    public ColorRGBA getDustColor() {
        return this.dustColor;
    }

}
