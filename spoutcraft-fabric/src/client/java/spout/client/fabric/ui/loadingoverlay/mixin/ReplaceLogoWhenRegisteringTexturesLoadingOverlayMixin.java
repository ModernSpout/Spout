package spout.client.fabric.ui.loadingoverlay.mixin;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.texture.MipmapStrategy;
import net.minecraft.client.renderer.texture.ReloadableTexture;
import net.minecraft.client.renderer.texture.TextureContents;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spout.client.fabric.ui.loadingoverlay.SpoutLogoIdentifier;
import java.io.IOException;
import java.io.InputStream;

@Mixin(LoadingOverlay.class)
public abstract class ReplaceLogoWhenRegisteringTexturesLoadingOverlayMixin {

    @Inject(method = "registerTextures", at = @At("HEAD"), cancellable = true)
    private static void spout$replaceLogo(TextureManager textureManager, CallbackInfo ci) {
        textureManager.registerAndLoad(SpoutLogoIdentifier.IDENTIFIER, new ReloadableTexture(SpoutLogoIdentifier.IDENTIFIER) {

            @Override
            public TextureContents loadContents(ResourceManager resourceManager) throws IOException {
                try (InputStream input = resourceManager.open(SpoutLogoIdentifier.IDENTIFIER)) {
                    TextureContents result = new TextureContents(NativeImage.read(input),
                        new TextureMetadataSection(true, true, MipmapStrategy.MEAN, 0.0f));
                    return result;
                }
            }

        });
        ci.cancel(); // Prevent vanilla logo instance from being created
    }

}
