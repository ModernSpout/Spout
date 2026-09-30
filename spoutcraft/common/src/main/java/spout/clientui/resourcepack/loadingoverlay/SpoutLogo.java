package spout.clientui.resourcepack.loadingoverlay;

import net.minecraft.resources.Identifier;
import spout.branding.SpoutNamespace;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Points to the Spout logo.
 */
public final class SpoutLogo {

    private SpoutLogo() {
        throw new UnsupportedOperationException();
    }

    private static final String FILENAME = "icon.png";
    public static final Identifier IDENTIFIER = Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, FILENAME);
    private static final String RESOURCE_PATH = "assets/spout/" + FILENAME;

    public static InputStream openInputStream() throws IOException {
        InputStream input = SpoutLogo.class.getClassLoader().getResourceAsStream(RESOURCE_PATH);
        if (input == null) {
            throw new FileNotFoundException(RESOURCE_PATH);
        }
        return input;
    }

}
