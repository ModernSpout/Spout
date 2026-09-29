package spout.util.mojang.codec;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import java.util.function.BiFunction;

/**
 * Similar to {@link InputAndOps}, but for a {@link MapLike} input for a {@link MapCodec}.
 */
public record MapInputAndOps<T>(MapLike<T> input, DynamicOps<T> ops) {

    public <A> DataResult<A> decodeUntyped(BiFunction<DynamicOps<?>, MapLike<?>, DataResult<A>> decoder) {
        return decoder.apply(this.ops, this.input);
    }

    public <A> A decodeValueUntyped(BiFunction<DynamicOps<?>, MapLike<?>, DataResult<A>> decoder) {
        return this.decodeUntyped(decoder).getOrThrow();
    }

    public <A> DataResult<A> decodeTyped(TypedDecoder<A> typedDecoder) {
        return this.decodeUntyped((ops, input) -> typedDecoder.decode((DynamicOps) ops, (MapLike) input));
    }

    public <A> A decodeValueTyped(TypedDecoder<A> typedDecoder) {
        return this.decodeTyped(typedDecoder).getOrThrow();
    }

    public interface TypedDecoder<A> {

        <T> DataResult<A> decode(DynamicOps<T> ops, MapLike<T> input);

    }

}
