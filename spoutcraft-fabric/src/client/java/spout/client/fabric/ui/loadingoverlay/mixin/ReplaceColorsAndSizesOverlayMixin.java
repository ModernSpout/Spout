package spout.client.fabric.ui.loadingoverlay.mixin;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.texture.MipmapStrategy;
import net.minecraft.client.renderer.texture.ReloadableTexture;
import net.minecraft.client.renderer.texture.TextureContents;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.server.packs.resources.ResourceManager;
import spout.client.fabric.ui.loadingoverlay.SpoutLogoIdentifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spout.client.fabric.ui.loadingoverlay.SwitchOverlayStyle;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.IntSupplier;

@Mixin(LoadingOverlay.class)
public abstract class ReplaceColorsAndSizesOverlayMixin {

    @Shadow
    @Final
    @Mutable
    private static IntSupplier BRAND_BACKGROUND;

    // @Shadow
    // @Final
    // @Mutable
    // private static int LOGO_SCALE;
    //
    // @Shadow
    // @Final
    // @Mutable
    // private static float LOGO_QUARTER_FLOAT;
    //
    // @Shadow
    // @Final
    // @Mutable
    // private static int LOGO_QUARTER;
    //
    // @Shadow
    // @Final
    // @Mutable
    // private static int LOGO_HALF;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void spout$modifyConstants(CallbackInfo ci) {

        // Background colors (ARGB)
        IntSupplier oldBrandBackground = BRAND_BACKGROUND;
        BRAND_BACKGROUND = () -> SwitchOverlayStyle.isSpout() ? 0xFF111111 : oldBrandBackground.getAsInt();

        // Logo sizing
        // LOGO_SCALE = 320;
        // LOGO_QUARTER = LOGO_SCALE / 4;
        // LOGO_HALF = LOGO_SCALE / 2;
        // LOGO_QUARTER_FLOAT = (float) LOGO_QUARTER;

    }

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
