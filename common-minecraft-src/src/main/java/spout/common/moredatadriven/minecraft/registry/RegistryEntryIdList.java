package spout.common.moredatadriven.minecraft.registry;

import it.unimi.dsi.fastutil.ints.IntObjectPair;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import java.util.ArrayList;
import java.util.List;

/**
 * A list of ids (and keys) of entries in some {@link Registry}.
 */
public record RegistryEntryIdList(Identifier registryIdentifier, List<IntObjectPair<Identifier>> entryIds) {

    public static final StreamCodec<FriendlyByteBuf, RegistryEntryIdList> STREAM_CODEC = StreamCodec.of(
        (buf, value) -> {
            buf.writeIdentifier(value.registryIdentifier);
            buf.writeShort(value.entryIds.size());
            for (IntObjectPair<Identifier> pair : value.entryIds) {
                buf.writeVarInt(pair.leftInt());
                buf.writeIdentifier(pair.right());
            }
        },
        buf -> {
            Identifier registryIdentifier = buf.readIdentifier();
            int size = buf.readShort();
            List<IntObjectPair<Identifier>> list = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                list.add(IntObjectPair.of(buf.readVarInt(), buf.readIdentifier()));
            }
            return new RegistryEntryIdList(registryIdentifier, list);
        }
    );

}
