package spout.gamecontent.datadriven.common.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import spout.gamecontent.datadriven.common.nonbuiltin.SpoutNonBuiltInResource;
import spout.util.mojang.codec.MapInputAndOps;

/**
 * An implementation of {@link TypeWithCodec} defined by its codec.
 */
public abstract class ExplicitTypeWithCodec<V, R extends SpoutNonBuiltInResource<V, ?>> implements TypeWithCodec<V, R> {

    protected final Identifier identifier;
    protected final MapCodec<? extends V> minecraftCodec;
    protected final MapCodec<R> codec;
    protected @Nullable List<RequiredResourceField> requiredResourceFields;

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

    protected Map<Codec<?>, Decoder<Identifier>> getRequiredResourceFieldCodecs() {
        return Collections.emptyMap();
    }

    protected List<RequiredResourceField> computeRequiredResourceFields() {
        Map<Codec<?>, Decoder<Identifier>> requiredResourceFieldCodecs = this.getRequiredResourceFieldCodecs();
        return RecordCodedFieldScanner.getRecordCodecFields(
            this.minecraftCodec,
            requiredResourceFieldCodecs::containsKey
        ).stream().map(pair -> new RequiredResourceField(pair.left(), requiredResourceFieldCodecs.get(pair.right()))).toList();
    }

    @Override
    public List<Identifier> decodeRequiredResources(MapInputAndOps<?> input) {
        if (this.requiredResourceFields == null) {
            this.requiredResourceFields = this.computeRequiredResourceFields();
            if (this.requiredResourceFields.isEmpty()) {
                this.requiredResourceFields = Collections.emptyList(); // Potentially saves memory
            }
        }
        return this.requiredResourceFields.stream().map(field -> {
            DataResult<Optional<Identifier>> result = input.decodeTyped(new MapInputAndOps.TypedDecoder<>() {

                @Override
                public <T> DataResult<Optional<Identifier>> decode(DynamicOps<T> ops, MapLike<T> input) {
                    T fieldInput = input.get(field.name);
                    if (fieldInput == null) {
                        return DataResult.success(Optional.empty());
                    }
                    return field.decoder.decode(ops, fieldInput).map(result -> Optional.of(result.getFirst()));
                }

            });
            return result.getOrThrow();
        }).filter(Optional::isPresent).map(Optional::get).toList();
    }

    protected record RequiredResourceField(String name, Decoder<Identifier> decoder) {
    }

}
