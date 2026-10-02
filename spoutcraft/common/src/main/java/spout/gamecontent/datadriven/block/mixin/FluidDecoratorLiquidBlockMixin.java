package spout.gamecontent.datadriven.block.mixin;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import spout.gamecontent.datadriven.block.LiquidBlockFluidDecorator;

@Mixin(LiquidBlock.class)
public abstract class FluidDecoratorLiquidBlockMixin implements LiquidBlockFluidDecorator {

    @Shadow
    @Final
    public FlowingFluid fluid;

    @Override
    public FlowingFluid spout$getFluid() {
        return this.fluid;
    }

}
