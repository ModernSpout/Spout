package spout.gamecontent.datadriven.block.subtypes;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Triple;
import org.jspecify.annotations.Nullable;
import spout.util.mojang.codec.EnumViaIdentifierCodec;
import spout.util.mojang.codec.StaticFieldViaIdentifierCodec;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Holder for codecs of subtypes.
 */
public final class SubtypeCodecs {

    private SubtypeCodecs() {
        throw new UnsupportedOperationException();
    }

    public static final Codec<MapColor> MAP_COLOR_CODEC = new StaticFieldViaIdentifierCodec<>(MapColor.class);
    public static final Codec<BlockStateFunction<MapColor>> MAP_COLOR_FUNCTION_CODEC = BlockStateFunction.codec(MAP_COLOR_CODEC);
    public static final Codec<SoundType> SOUND_TYPE_CODEC = new StaticFieldViaIdentifierCodec<>(SoundType.class);
    public static final Codec<BlockStateFunction<Integer>> LIGHT_EMISSION_CODEC = BlockStateFunction.codec(Codec.INT);
    public static final Codec<PushReaction> PUSH_REACTION_CODEC = new EnumViaIdentifierCodec<>(PushReaction.class);
    public static final Codec<NoteBlockInstrument> NOTE_BLOCK_INSTRUMENT_CODEC = new EnumViaIdentifierCodec<>(NoteBlockInstrument.class);
    public static final Codec<BlockBehaviour.OffsetType> OFFSET_TYPE_CODEC = new EnumViaIdentifierCodec<>(BlockBehaviour.OffsetType.class);

    public static final Codec<BlockBehaviour.OffsetFunction> OFFSET_FUNCTION_CODEC = new Codec<>() {

        private static @Nullable List<Pair<BlockState, BlockPos>> testInputs;

        private static @Nullable List<Triple<BlockBehaviour.OffsetType, BlockBehaviour.OffsetFunction, List<Vec3>>> functionByType;

        private static List<Pair<BlockState, BlockPos>> testInputs() {
            if (testInputs == null) {
                Block block = BuiltInRegistries.BLOCK.stream().filter(it -> ((BlockBehaviourMaxVerticalOffsetAccessor) it).spout$getMaxVerticalOffset() != 0).findFirst().get();
                BlockState blockState = block.defaultBlockState();
                testInputs = Stream.of(BlockPos.ZERO, BlockPos.ZERO.offset(3, 7, 2), BlockPos.ZERO.offset(9, 11, -4))
                    .map(pos -> Pair.of(blockState, pos)).toList();
            }
            return testInputs;
        }

        private static List<Triple<BlockBehaviour.OffsetType, BlockBehaviour.OffsetFunction, List<Vec3>>> functionByType() {
            if (functionByType == null) {
                functionByType = Arrays.stream(BlockBehaviour.OffsetType.values()).map(type -> {
                    BlockBehaviour.Properties properties = BlockBehaviour.Properties.of();
                    properties.offsetType(type);
                    return Triple.of(
                        type,
                        properties.offsetFunction,
                        testInputs().stream().map(test -> properties.offsetFunction.evaluate(test.getFirst(), test.getSecond())).toList()
                    );
                }).toList();
            }
            return functionByType;
        }

        @Override
        public <T> DataResult<T> encode(BlockBehaviour.OffsetFunction input, DynamicOps<T> dynamicOps, T prefix) {
            List<Triple<BlockBehaviour.OffsetType, BlockBehaviour.OffsetFunction, List<Vec3>>> functionByType = functionByType();
            List<Vec3> outputs = testInputs().stream().map(test -> input.evaluate(test.getFirst(), test.getSecond())).toList();
            for (int i = 0; i < functionByType.size(); i++) {
                Triple<BlockBehaviour.OffsetType, BlockBehaviour.OffsetFunction, List<Vec3>> function = functionByType.get(i);
                if (function.getRight().equals(outputs)) {
                    return OFFSET_TYPE_CODEC.encode(function.getLeft(), dynamicOps, prefix);
                }
            }
            return DataResult.error(() -> "Unknown offset type");
        }

        @Override
        public <T> DataResult<Pair<BlockBehaviour.OffsetFunction, T>> decode(DynamicOps<T> dynamicOps, T input) {
            return OFFSET_TYPE_CODEC.decode(dynamicOps, input).flatMap(typeResult -> {
                List<Triple<BlockBehaviour.OffsetType, BlockBehaviour.OffsetFunction, List<Vec3>>> functionByType = functionByType();
                BlockBehaviour.OffsetType type = typeResult.getFirst();
                for (int i = 0; i < functionByType.size(); i++) {
                    Triple<BlockBehaviour.OffsetType, BlockBehaviour.OffsetFunction, List<Vec3>> function = functionByType.get(i);
                    if (function.getLeft().equals(type)) {
                        return DataResult.success(Pair.of(function.getMiddle(), input));
                    }
                }
                return DataResult.error(() -> "Unknown offset type");
            });
        }

    };

    public static final Codec<FeatureFlag> FEATURE_FLAG_CODEC = Identifier.CODEC.comapFlatMap(identifier -> {
        FeatureFlag featureFlag = FeatureFlags.REGISTRY.names.get(identifier);
        if (featureFlag != null) {
            return DataResult.success(featureFlag);
        }
        return DataResult.error(() -> "No such feature flag: " + identifier);
    }, featureFlag -> FeatureFlags.REGISTRY.names.entrySet().stream().filter(entry -> entry.getValue().equals(featureFlag)).findAny().get().getKey());

    public static final Codec<FeatureFlagSet> FEATURE_FLAG_SET_CODEC = FEATURE_FLAG_CODEC.listOf().xmap(
        featureFlags -> {
            FeatureFlag[] array = featureFlags.toArray(FeatureFlag[]::new);
            return array.length == 0 ? FeatureFlagSet.of() : array.length == 1 ? FeatureFlagSet.of(array[0]) : FeatureFlagSet.of(array[0], Arrays.copyOfRange(array, 1, array.length));
        },
        featureFlagSet -> FeatureFlags.REGISTRY.names.values().stream().filter(featureFlagSet::contains).toList()
    );

    public static final Codec<BlockBehaviour.PostProcess> POST_PROCESS_CODEC = new Codec<>() {

        @Override
        public <T> DataResult<T> encode(BlockBehaviour.PostProcess input, DynamicOps<T> ops, T prefix) {
            BlockPos output;
            try {
                output = input.getPostProcessPos(null, null, BlockPos.ZERO);
            } catch (Exception e) {
                return DataResult.error(() -> "Not an encodable post process: " + e);
            }
            if (output == null) {
                return DataResult.success(ops.createString("null"));
            }
            return DataResult.success(ops.createIntList(IntStream.of(output.getX(), output.getY(), output.getZ())));
        }

        @Override
        public <T> DataResult<Pair<BlockBehaviour.PostProcess, T>> decode(DynamicOps<T> ops, T input) {
            DataResult<String> stringResult = ops.getStringValue(input);
            if (stringResult.isSuccess() && stringResult.getOrThrow().equals("null")) {
                return DataResult.success(Pair.of((state, level, pos) -> null, input));
            }
            DataResult<IntStream> intStreamResult = ops.getIntStream(input);
            IntStream intStream = intStreamResult.getOrThrow();
            int dx = intStream.findFirst().getAsInt();
            int dy = intStream.findFirst().getAsInt();
            int dz = intStream.findFirst().getAsInt();
            return DataResult.success(Pair.of((state, level, pos) -> pos.offset(dx, dy, dz), input));
        }

    };

}
