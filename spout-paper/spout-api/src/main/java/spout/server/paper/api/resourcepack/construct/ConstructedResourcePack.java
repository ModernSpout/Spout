package spout.server.paper.api.resourcepack.construct;

import com.google.common.hash.Hashing;
import spout.api.clientview.model.awarenesslevel.AwarenessLevel;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public interface ConstructedResourcePack {

    /**
     * @return The {@link AwarenessLevel} for which this resource pack was constructed.
     */
    AwarenessLevel getAwarenessLevel();

    /**
     * @return The byte array contents of the pack.
     *
     * <p>
     * This can be used to write the pack to a file, for example.
     * </p>
     */
    byte[] getBytes();

    /**
     * @return A {@linkplain Hashing#sha1() SHA-1} hash of the {@linkplain #getBytes() byte contents} of the pack.
     */
    String getSHA1Hash();

    /**
     * @return A {@link UUID} for the pack, based on {@link #getAwarenessLevel()} and {@link #getSHA1Hash()}.
     */
    UUID getUUID();

    /**
     * Writes the {@linkplain #getBytes() contents} of this pack to a file at the given path.
     */
    void writeToFile(Path path) throws IOException;

}
