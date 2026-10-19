package spout.gamecontent.datadriven.block.mixin;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spout.gamecontent.datadriven.block.OverrideSuffocationCheckWhilePlayerMoving;

/**
 * @see OverrideSuffocationCheckWhilePlayerMoving
 */
@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {

    @Inject(method = "Lnet/minecraft/client/player/LocalPlayer;aiStep()V", at = @At("HEAD"))
    private void aiStepHead(CallbackInfo ci) {
        OverrideSuffocationCheckWhilePlayerMoving.isMoving = true;
    }

}
