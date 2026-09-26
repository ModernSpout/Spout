package spout.clientui.resourcepack.loadingoverlay.mixin;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import spout.clientui.resourcepack.loadingoverlay.SpoutLogoIdentifier;

/**
 * Redirect the two specific blit calls in render that draw {@link LoadingOverlay#MOJANG_STUDIOS_LOGO_LOCATION}.
 * The ordinal distinguishes the two calls (0 and 1).
 */
@Mixin(LoadingOverlay.class)
public abstract class ChangePositionsOfSpoutLogoLoadingOverlayMixin {

    private static final double ADJUSTED_GRAPHICS_WIDTH_FACTOR = 0.4; // Original is 0.25
    private static final double ADJUSTED_GRAPHICS_Y_FACTOR = 0.85; // Original is 1

    private static void adjustedGraphicsBlit(GuiGraphicsExtractor graphics, RenderPipeline renderPipeline, Identifier texture, int color, int ordinal) {

        // Adjust parameters
        int contentX = (int) ((double) graphics.guiWidth() * (double) 0.5F);
        int logoY = (int) ((double) graphics.guiHeight() * (double) 0.5F);
        double logoHeight = Math.min((double) graphics.guiWidth() * (double) 0.75F, graphics.guiHeight()) * ADJUSTED_GRAPHICS_WIDTH_FACTOR;
        int logoHeightHalf = (int) (logoHeight * (double) 0.5F);
        double contentWidth = logoHeight * (double) 4.0F;
        int logoWidthHalf = (int) (contentWidth * (double) 0.5F);

        // Draw
        if (ordinal == 0) {
            graphics.blit(renderPipeline, texture, contentX - logoWidthHalf, ((int) ((logoY - logoHeightHalf) * ADJUSTED_GRAPHICS_Y_FACTOR)), -0.0625F, 0.0F, logoWidthHalf, (int) logoHeight, 120, 60, 120, 120, color);
        } else {
            graphics.blit(renderPipeline, texture, contentX, ((int) ((logoY - logoHeightHalf) * ADJUSTED_GRAPHICS_Y_FACTOR)), 0.0625F, 60.0F, logoWidthHalf, (int) logoHeight, 120, 60, 120, 120, color);
        }

    }

    @Redirect(
        method = "extractRenderState",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V",
            ordinal = 0
        )
    )
    private void redirectBlitFirst(
        GuiGraphicsExtractor graphics,
        RenderPipeline renderPipeline,
        Identifier texture,
        int x, int y,
        float u, float v,
        int width, int height,
        int srcWidth, int srcHeight,
        int textureWidth, int textureHeight,
        int color
    ) {
        if (texture.equals(SpoutLogoIdentifier.IDENTIFIER)) {
            adjustedGraphicsBlit(graphics, renderPipeline, texture, color, 0);
        } else {
            graphics.blit(renderPipeline, texture, x, y, u, v, width, height, srcWidth, srcHeight, textureWidth, textureHeight, color);
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
        GuiGraphicsExtractor graphics,
        RenderPipeline renderPipeline,
        Identifier texture,
        int x, int y,
        float u, float v,
        int width, int height,
        int srcWidth, int srcHeight,
        int textureWidth, int textureHeight,
        int color
    ) {
        if (texture.equals(SpoutLogoIdentifier.IDENTIFIER)) {
            adjustedGraphicsBlit(graphics, renderPipeline, texture, color, 1);
        } else {
            graphics.blit(renderPipeline, texture, x, y, u, v, width, height, srcWidth, srcHeight, textureWidth, textureHeight, color);
        }
    }

}
