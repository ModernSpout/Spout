package spout.gamecontent.datadriven.block.subtypes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Arrays;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

/**
 * A simple enum for the possible values of
 * {@link BlockBehaviour.StateArgumentPredicate}{@code <}{@link AABB}{@code >}s
 * used as values
 * of fields of {@link BlockBehaviour.Properties}.
 */
public interface KnownStateAABBPredicate extends BlockBehaviour.StateArgumentPredicate<AABB> {

    KnownStateAABBPredicate NEAR_PLANE_INTERSECTS_OUTLINE = new Explicit("near_plane_intersects_outline", Blocks.NEAR_PLANE_INTERSECTS_OUTLINE);

    Identifier getKey();

    final class Explicit implements KnownStateAABBPredicate {

        public final Identifier key;
        public final BlockBehaviour.StateArgumentPredicate<AABB> predicate;

        private Explicit(Identifier key, BlockBehaviour.StateArgumentPredicate<AABB> predicate) {
            this.key = key;
            this.predicate = predicate;
        }

        private Explicit(String key, BlockBehaviour.StateArgumentPredicate<AABB> predicate) {
            this(Identifier.parse(key), predicate);
        }

        @Override
        public boolean test(BlockState state, BlockGetter level, BlockPos pos, AABB aabb) {
            return this.predicate.test(state, level, pos, aabb);
        }

        @Override
        public Identifier getKey() {
            return this.key;
        }

    }

    final class Via implements KnownStateAABBPredicate {

        public final KnownStatePredicate inner;

        public Via(KnownStatePredicate inner) {
            this.inner = inner;
        }

        @Override
        public boolean test(BlockState state, BlockGetter level, BlockPos pos, AABB aabb) {
            return this.inner.test(state, level, pos);
        }

        @Override
        public Identifier getKey() {
            return this.inner.key;
        }

    }

    final class Values {

        private Values() {
            throw new UnsupportedOperationException();
        }

        private static KnownStateAABBPredicate @Nullable [] VALUES;

        public static KnownStateAABBPredicate[] get() {
            if (VALUES == null) {
                VALUES = Stream.concat(
                    Stream.of(NEAR_PLANE_INTERSECTS_OUTLINE),
                    Arrays.stream(KnownStatePredicate.values()).map(Via::new)
                ).toArray(KnownStateAABBPredicate[]::new);
            }
            return VALUES;
        }

        public static @Nullable KnownStateAABBPredicate get(Identifier key) {
            return Arrays.stream(get()).filter(value -> value.getKey().equals(key)).findFirst().orElse(null);
        }

    }

    static KnownStateAABBPredicate wrap(BlockBehaviour.StateArgumentPredicate<AABB> predicate) {
        // Return the value itself if it is already a KnownStateAABBPredicate
        if (predicate instanceof KnownStateAABBPredicate knownStateAABBPredicate) {
            return knownStateAABBPredicate;
        }
        // Go over the known predicates and compare them
        KnownStateAABBPredicate foundValue = Arrays.stream(Values.get()).filter(value -> value instanceof Explicit && ((Explicit) value).predicate.equals(predicate)).findAny().orElse(null);
        if (foundValue != null) {
            return foundValue;
        }
        // Compare them to newly instantiated literals
        if (Blocks.NEAR_PLANE_INTERSECTS_OUTLINE.equals(predicate)) {
            return NEAR_PLANE_INTERSECTS_OUTLINE;
        }
        throw new IllegalArgumentException("Not a known state AABB predicate: " + predicate);
    }

    Codec<BlockBehaviour.StateArgumentPredicate<AABB>> CODEC = Identifier.CODEC.comapFlatMap(key -> {
        @Nullable KnownStateAABBPredicate found = Values.get(key);
        if (found != null) {
            return DataResult.success(Values.get(key));
        }
        return DataResult.error(() -> "Not a known state AABB predicate: " + key);
    }, predicate -> wrap(predicate).getKey());

}
