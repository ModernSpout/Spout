package spout.util.mojang.codec;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.VarInt;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * Base on {@link ByteBufCodecs#byteArray}.
 */
public class ExplicitLengthByteArrayStreamCodec implements StreamCodec<ByteBuf, Pair<byte[], Integer>> {

    public static final ExplicitLengthByteArrayStreamCodec INSTANCE = new ExplicitLengthByteArrayStreamCodec();

    public ExplicitLengthByteArrayStreamCodec() {
    }

    @Override
    public Pair<byte[], Integer> decode(final ByteBuf input) {
        byte[] value = FriendlyByteBuf.readByteArray(input);
        return Pair.of(value, value.length);
    }

    @Override
    public void encode(final ByteBuf output, final Pair<byte[], Integer> value) {
        int length = value.second();
        VarInt.write(output, length);
        output.writeBytes(value.first(), 0, length);
    }

}
