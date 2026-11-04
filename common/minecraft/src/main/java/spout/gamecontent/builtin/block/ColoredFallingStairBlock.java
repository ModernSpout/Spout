package spout.gamecontent.builtin.block;

import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A combination of {@link ColoredFallingBlock} and {@link StairBlock}.
 */
public class ColoredFallingStairBlock extends FallingStairBlock implements AbstractColoredFallingBlock {

    public final ColorRGBA dustColor;

    public ColoredFallingStairBlock(final BlockState baseState, final ColorRGBA dustColor, final BlockBehaviour.Properties properties) {
        super(baseState, properties);
        this.dustColor = dustColor;
    }

    @Override
    public ColorRGBA getDustColor() {
        return this.dustColor;
    }

}
