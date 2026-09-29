package spout.server.paper.impl.resourcepack.construct;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import spout.api.clientview.model.awarenesslevel.CraftAwarenessLevel;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.server.paper.api.resourcepack.construct.ConstructedResourcePack;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;

/**
 * The implementation for {@link ConstructedResourcePack}.
 */
public final class ConstructedResourcePackImpl implements ConstructedResourcePack {

    private final AwarenessLevel awarenessLevel;
    private final byte[] bytes;
    private final String sha1Hash;
    private final UUID uuid;

    public ConstructedResourcePackImpl(AwarenessLevel awarenessLevel, byte[] bytes) {
        this.awarenessLevel = awarenessLevel;
        this.bytes = bytes;
        HashCode sha1HashCode;
        try {
             sha1HashCode = ByteSource.wrap(bytes).hash(Hashing.sha1());
        } catch (Exception e) {
            throw new RuntimeException("Exception while hashing generated resource pack contents", e);
        }
        this.sha1Hash = sha1HashCode.toString();
        byte[] sha1HashBytes = sha1HashCode.asBytes();
        byte[] sha1HashBytesAndAwarenessLevel = Arrays.copyOf(sha1HashBytes, sha1HashBytes.length + 1);
        sha1HashBytesAndAwarenessLevel[sha1HashBytes.length] = (byte) awarenessLevel.getId();
        this.uuid = UUID.nameUUIDFromBytes(sha1HashBytesAndAwarenessLevel);
    }

    @Override
    public spout.api.clientview.model.awarenesslevel.AwarenessLevel getAwarenessLevel() {
        return CraftAwarenessLevel.toBukkit(this.awarenessLevel);
    }

    @Override
    public byte[] getBytes() {
        return this.bytes;
    }

    @Override
    public String getSHA1Hash() {
        return this.sha1Hash;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public void writeToFile(Path path) throws IOException {
        Files.write(path, this.getBytes());
    }

}
