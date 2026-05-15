package spout.client.fabric.ui.loadingoverlay.mixin;

import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LoadingOverlay.class)
public interface LoadingOverlayLogoLocationAccessor {

    @Accessor("MOJANG_STUDIOS_LOGO_LOCATION")
    static Identifier getMojangStudiosLogoLocation() {
        throw new UnsupportedOperationException();
    }

    @Mutable
    @Final
    @Accessor("MOJANG_STUDIOS_LOGO_LOCATION")
    static void setMojangStudiosLogoLocation(Identifier id) {
        throw new UnsupportedOperationException();
    }

}
