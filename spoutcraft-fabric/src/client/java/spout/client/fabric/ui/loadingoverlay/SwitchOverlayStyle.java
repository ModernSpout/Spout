package spout.client.fabric.ui.loadingoverlay;

import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import spout.client.fabric.ui.loadingoverlay.mixin.LoadingOverlayLogoLocationAccessor;

/**
 * A utility for switching the overlay logo and style.
 */
public final class SwitchOverlayStyle {

    private SwitchOverlayStyle() {
        throw new UnsupportedOperationException();
    }

    /**
     * The original {@link Identifier} for the Mojang logo,
     * or null if not cached yet.
     */
    private static @Nullable Identifier mojangIdentifier;

    /**
     * The current state of the logo: true for Spout.
     */
    private static boolean isSpout;

    public static void setSpout() {
        if (isSpout) {
            return;
        }
        if (mojangIdentifier == null) {
            mojangIdentifier = LoadingOverlayLogoLocationAccessor.getMojangStudiosLogoLocation();
        }
        LoadingOverlayLogoLocationAccessor.setMojangStudiosLogoLocation(SpoutLogoIdentifier.IDENTIFIER);
        isSpout = true;
    }

    public static void setMojang() {
        if (isSpout) {
            LoadingOverlayLogoLocationAccessor.setMojangStudiosLogoLocation(mojangIdentifier);
            isSpout = false;
        }
    }

    public static boolean isSpout() {
        return isSpout;
    }

}
