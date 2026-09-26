package spout.clientui.resourcepack.loadingoverlay;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.resources.Identifier;
import spout.branding.SpoutNamespace;

/**
 * Holder for {@link #IDENTIFIER}.
 */
public final class SpoutLogoIdentifier {

    private static final String RESOURCE_PATH = "assets/spout/icon.png";

    private SpoutLogoIdentifier() {
        throw new UnsupportedOperationException();
    }

    public static final Identifier IDENTIFIER = Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, "icon.png");

    public static InputStream open() throws IOException {
        InputStream input = SpoutLogoIdentifier.class.getClassLoader().getResourceAsStream(RESOURCE_PATH);
        if (input == null) {
            throw new FileNotFoundException(RESOURCE_PATH);
        }
        return input;
    }

}
