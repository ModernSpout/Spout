package spout.common.moredatadriven.minecraft.common.type;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.stream.Stream;
import net.minecraft.resources.Identifier;
import spout.common.moredatadriven.minecraft.common.nonbuiltin.SpoutNonBuiltInResource;
import spout.common.util.mojang.codec.MapInputAndOps;

/**
 * An implementation of {@link TypeWithCodec} defined by its codec.
 */
public abstract class ExplicitTypeWithCodec<V, R extends SpoutNonBuiltInResource<V, ?>> implements TypeWithCodec<V, R> {

    private final Identifier identifier;
    private final MapCodec<? extends V> minecraftCodec;
    private final MapCodec<R> codec;

    public ExplicitTypeWithCodec(Identifier identifier, MapCodec<? extends V> minecraftCodec) {
        this.identifier = identifier;
        this.minecraftCodec = minecraftCodec;
        this.codec = new MapCodec<>() {

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return ExplicitTypeWithCodec.this.minecraftCodec.keys(dynamicOps);
            }

            @Override
            public <T> RecordBuilder<T> encode(R input, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
                return ((MapCodec<V>) ExplicitTypeWithCodec.this.minecraftCodec).encode(input.getValue(), dynamicOps, recordBuilder);
            }

            @Override
            public <T> DataResult<R> decode(DynamicOps<T> dynamicOps, MapLike<T> input) {
                return DataResult.success(ExplicitTypeWithCodec.this.constructForInput(new MapInputAndOps<>(input, dynamicOps)));
            }

        };
    }

    protected abstract R constructForInput(MapInputAndOps<?> input);

    @Override
    public Identifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public MapCodec<R> getCodec() {
        return this.codec;
    }

    @Override
    public <T> DataResult<? extends V> decodeValueFromInput(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
        return this.minecraftCodec.decode(dynamicOps, mapLike);
    }

}
