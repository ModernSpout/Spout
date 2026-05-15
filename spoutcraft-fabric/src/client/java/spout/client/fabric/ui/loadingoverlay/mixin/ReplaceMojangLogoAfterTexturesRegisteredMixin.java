package spout.client.fabric.ui.loadingoverlay.mixin;

import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.texture.ReloadableTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextureManager.class)
public abstract class ReplaceMojangLogoAfterTexturesRegisteredMixin {

    @Inject(
        method = "registerAndLoad(Lnet/minecraft/resources/Identifier;Lnet/minecraft/client/renderer/texture/ReloadableTexture;)V",
        at = @At("RETURN")
    )
    private void afterTextureRegistered(Identifier id, ReloadableTexture texture, CallbackInfo ci) {
        if (id.getNamespace().equals(Identifier.DEFAULT_NAMESPACE) && id.getPath().equals("textures/environment/end_sky.png") && LoadingOverlay.MOJANG_STUDIOS_LOGO_LOCATION.getNamespace().equals(Identifier.DEFAULT_NAMESPACE)) {
            // SwitchOverlayLogo.setSpout();
        }
    }

}
