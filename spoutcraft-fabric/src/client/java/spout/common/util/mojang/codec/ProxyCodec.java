package spout.common.util.mojang.codec;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

/**
 * A one-way codec that passes via a proxy type.
 */
public class ProxyCodec<A> implements Codec<A> {

    private final Codec<? extends Proxy<A>> proxyCodec;

    public ProxyCodec(Codec<? extends Proxy<A>> proxyCodec) {
        this.proxyCodec = proxyCodec;
    }

    @Override
    public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
        return proxyCodec.decode(ops, input)
            .map(result -> Pair.of(result.getFirst().getOriginal(), result.getSecond()));
    }

    @Override
    public <T> DataResult<T> encode(A input, DynamicOps<T> ops, T prefix) {
        throw new UnsupportedOperationException();
    }

    public interface Proxy<A> {

        A getOriginal();

    }

}
