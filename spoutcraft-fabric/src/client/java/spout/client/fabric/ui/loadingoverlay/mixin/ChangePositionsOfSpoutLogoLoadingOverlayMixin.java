package spout.client.fabric.ui.loadingoverlay.mixin;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import spout.client.fabric.ui.loadingoverlay.SpoutLogoIdentifier;

/**
 * Redirect the two specific blit calls in render that draw {@link LoadingOverlay#MOJANG_STUDIOS_LOGO_LOCATION}.
 * The ordinal distinguishes the two calls (0 and 1).
 */
@Mixin(LoadingOverlay.class)
public abstract class ChangePositionsOfSpoutLogoLoadingOverlayMixin {

    @Redirect(
        method = "extractRenderState",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V",
            ordinal = 0
        )
    )
    private void redirectBlitFirst(
        GuiGraphicsExtractor guiGraphics,
        RenderPipeline renderPipeline,
        Identifier texture,
        int i, int j,
        float f, float g,
        int k, int l,
        int m, int n,
        int o, int p,
        int q
    ) {
        if (texture.equals(SpoutLogoIdentifier.IDENTIFIER)) {
            // Adjust coordinates so the logo renders fully
            int n2 = (int) ((double) guiGraphics.guiWidth() * (double) 0.5F);
            int p2 = (int) ((double) guiGraphics.guiHeight() * (double) 0.5F);
            double d2 = Math.min((double) guiGraphics.guiWidth() * (double) 0.75F, (double) guiGraphics.guiHeight()) * (double) 0.25F;
            int q2 = (int) (d2 * (double) 0.5F);
            double e2 = d2 * (double) 4.0F;
            int r2 = (int) (e2 * (double) 0.5F);
            int k2 = k * 4 / 5;
            int l2 = l * 4 / 5;
            guiGraphics.blit(renderPipeline, texture, n2 - k2 / 2, (p2 - l2) * 17 / 20, 0f, 0f, k2, l2 * 2, 320, 320, 320, 320, q);
        } else {
            guiGraphics.blit(renderPipeline, texture, i, j, f, g, k, l, m, n, o, p, q);
        }
    }

    @Redirect(
        method = "extractRenderState",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V",
            ordinal = 1
        )
    )
    private void redirectBlitSecond(
        GuiGraphicsExtractor guiGraphics,
        RenderPipeline renderPipeline,
        Identifier texture,
        int i, int j,
        float f, float g,
        int k, int l,
        int m, int n,
        int o, int p,
        int q
    ) {
        if (texture.equals(SpoutLogoIdentifier.IDENTIFIER)) {
            // Don't draw anything
        } else {
            guiGraphics.blit(renderPipeline, texture, i, j, f, g, k, l, m, n, o, p, q);
        }
    }

}
