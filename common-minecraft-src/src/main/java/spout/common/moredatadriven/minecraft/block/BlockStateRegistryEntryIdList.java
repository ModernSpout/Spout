package spout.common.moredatadriven.minecraft.block;

import it.unimi.dsi.fastutil.ints.IntObjectPair;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.block.Block;
import spout.common.moredatadriven.minecraft.registry.RegistryEntryIdList;

/**
 * A variation on {@link RegistryEntryIdList},
 * that contains the ids for {@link Block#BLOCK_STATE_REGISTRY} entries.
 */
public record BlockStateRegistryEntryIdList(List<IntObjectPair<String>> entryIds) {

    public static final StreamCodec<FriendlyByteBuf, BlockStateRegistryEntryIdList> STREAM_CODEC = StreamCodec.of(
        (buf, value) -> {
            buf.writeShort(value.entryIds.size());
            for (IntObjectPair<String> pair : value.entryIds) {
                buf.writeVarInt(pair.leftInt());
                buf.writeUtf(pair.right());
            }
        },
        buf -> {
            int size = buf.readShort();
            List<IntObjectPair<String>> list = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                list.add(IntObjectPair.of(buf.readVarInt(), buf.readUtf()));
            }
            return new BlockStateRegistryEntryIdList(list);
        }
    );

}
