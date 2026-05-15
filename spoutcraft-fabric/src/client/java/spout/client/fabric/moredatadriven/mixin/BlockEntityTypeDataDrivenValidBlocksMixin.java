package spout.client.fabric.moredatadriven.mixin;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import spout.common.moredatadriven.minecraft.BlockEntityAlternativeValidation;

@Mixin(BlockEntityType.class)
public abstract class BlockEntityTypeDataDrivenValidBlocksMixin {

    @Inject(method = "isValid", at = @At("HEAD"), cancellable = true)
    private void spout$checkAlternativelyValid(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (BlockEntityAlternativeValidation.isAlternativelyValid((BlockEntityType) (Object) this, state)) {
            cir.setReturnValue(true);
        }
    }

}
