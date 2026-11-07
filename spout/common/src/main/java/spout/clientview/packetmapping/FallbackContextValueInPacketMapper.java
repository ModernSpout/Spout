package spout.clientview.packetmapping;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import org.jspecify.annotations.Nullable;

/**
 * A {@link ValueInPacketMapper} that has a generic fallback context.
 */
public interface FallbackContextValueInPacketMapper<I, O, C> extends ValueInPacketMapper<I, O, C> {

    C getFallbackContext();

    @Nullable C getDefaultActiveContext();

    O applyWithNonNullContext(I value, C context);

    @Override
    default O apply(I value, @Nullable C context) {
        return this.applyWithNonNullContext(value, context != null ? context : this.getFallbackContext());
    }

    @Override
    default O apply(I value) {
        return this.applyWithNonNullContext(value, this.getFallbackContext());
    }

    /**
     * Applies the default active context, if present.
     * Otherwise, returns the input.
     *
     * <p>
     * This method can only be used if the output instance of type {@link O}
     * is guaranteed to be an instance of {@link I}.
     * </p>
     */
    default I applyWithDefaultActiveContext(I value) {
        @Nullable C activeContext = this.getDefaultActiveContext();
        if (activeContext == null) {
            return value;
        }
        return (I) apply(value, activeContext);
    }

    default Codec<I> wrapCodecWithDefaultActiveContextMapping(Codec<I> codec) {
        return new Codec<>() {

            @Override
            public <T> DataResult<T> encode(final I i, final DynamicOps<T> dynamicOps, final T t) {
                return codec.encode(FallbackContextValueInPacketMapper.this.applyWithDefaultActiveContext(i), dynamicOps, t);
            }

            @Override
            public <T> DataResult<Pair<I, T>> decode(final DynamicOps<T> dynamicOps, final T t) {
                return codec.decode(dynamicOps, t);
            }

        };
    }

}
