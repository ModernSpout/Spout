package spout.gamecontent.datadriven.block.mixin;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import spout.gamecontent.datadriven.block.AllowUnregisteringBlockColorsDecorator;
import java.util.List;
import java.util.Map;

@Mixin(BlockColors.class)
public abstract class AllowUnregisteringBlockColorsMixin implements AllowUnregisteringBlockColorsDecorator {

    @Shadow
    @Final
    private Map<Block, List<BlockTintSource>> sources;

    @Override
    public void unregister(Block block) {
        this.sources.remove(block);
    }

}
