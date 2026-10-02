package spout.gamecontent.datadriven.block.subtypes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * A simple enum for the possible values of
 * {@link BlockBehaviour.StatePredicate}s
 * used as values
 * of fields of {@link BlockBehaviour.Properties}.
 */
public class KnownStatePredicate implements BlockBehaviour.StatePredicate {

    public static final KnownStatePredicate NEVER = new KnownStatePredicate("never", Blocks::never);
    public static final KnownStatePredicate ALWAYS = new KnownStatePredicate("always", Blocks::always);
    public static final KnownStatePredicate IS_COLLISION_SHAPE_FULL_BLOCK = new KnownStatePredicate("is_collision_shape_full_block", (state, level, pos) -> state.isCollisionShapeFullBlock(level, pos));
    public static final KnownStatePredicate CAUSES_SUFFOCATION = new KnownStatePredicate("causes_suffocation", (state, level, pos) -> state.is(BlockTags.CAUSES_SUFFOCATION));
    public static final KnownStatePredicate NOT_CLOSED_SHULKER = new KnownStatePredicate("not_closed_shulker", Blocks.NOT_CLOSED_SHULKER);
    public static final KnownStatePredicate NOT_EXTENDED_PISTON = new KnownStatePredicate("not_extended_piston", Blocks.NOT_EXTENDED_PISTON);
    public static final KnownStatePredicate MAX_SNOW_LAYERS = new KnownStatePredicate("max_snow_layers", (state, level, pos) -> state.getValue(SnowLayerBlock.LAYERS) >= 8);

    public final Identifier key;
    public final BlockBehaviour.StatePredicate predicate;

    private KnownStatePredicate(Identifier key, BlockBehaviour.StatePredicate predicate) {
        this.key  = key;
        this.predicate = predicate;
    }

    private KnownStatePredicate(String key, BlockBehaviour.StatePredicate predicate) {
        this(Identifier.parse(key), predicate);
    }

    @Override
    public boolean test(BlockState state, BlockGetter level, BlockPos pos) {
        return this.predicate.test(state, level, pos);
    }

    public static KnownStatePredicate wrap(BlockBehaviour.StatePredicate predicate) {
        // Return the value itself if it is already a KnownStatePredicate
        if (predicate instanceof KnownStatePredicate knownStatePredicate) {
            return knownStatePredicate;
        }
        // Go over the known predicates and compare them
        KnownStatePredicate foundValue = Arrays.stream(values()).filter(value -> value.predicate.equals(predicate)).findAny().orElse(null);
        if (foundValue != null) {
            return foundValue;
        }
        // Compare them to newly instantiated literals
        if (((BlockBehaviour.StatePredicate) Blocks::never).equals(predicate)) {
            return NEVER;
        }
        if (((BlockBehaviour.StatePredicate) Blocks::always).equals(predicate)) {
            return ALWAYS;
        }
        if (((BlockBehaviour.StatePredicate) BlockBehaviour.BlockStateBase::isCollisionShapeFullBlock).equals(predicate)) {
            return IS_COLLISION_SHAPE_FULL_BLOCK;
        }
        if (Blocks.NOT_CLOSED_SHULKER.equals(predicate)) {
            return NOT_CLOSED_SHULKER;
        }
        if (Blocks.NOT_EXTENDED_PISTON.equals(predicate)) {
            return NOT_EXTENDED_PISTON;
        }
        // Use an ugly Reflection trick to detect whether the given predicate is Blocks::never or Blocks::always
        try {
            Method m = predicate.getClass().getDeclaredMethod("writeReplace");
            m.trySetAccessible();
            SerializedLambda serializedLambda = (SerializedLambda) m.invoke(predicate);
            String implClass = serializedLambda.getImplClass();
            String implMethodName = serializedLambda.getImplMethodName();
            if (implClass.equals("net/minecraft/world/level/block/Blocks")) {
                if (implMethodName.equals("never")) {
                    return NEVER;
                } else if (implMethodName.equals("always")) {
                    return ALWAYS;
                }
            } else if (implClass.equals("net/minecraft/world/level/block/state/BlockBehaviour$BlockStateBase")) {
                if (implMethodName.equals("isCollisionShapeFullBlock")) {
                    return IS_COLLISION_SHAPE_FULL_BLOCK;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("Not a known state predicate: " + predicate);
    }

    public static final Codec<BlockBehaviour.StatePredicate> CODEC = Identifier.CODEC.comapFlatMap(key -> {
        @Nullable KnownStatePredicate found = valueOf(key);
        if (found != null) {
            return DataResult.success(valueOf(key));
        }
        return DataResult.error(() -> "Not a known state predicate: " + key);
    }, predicate -> wrap(predicate).key);

    private static KnownStatePredicate @Nullable [] VALUES;

    public static KnownStatePredicate[] values() {
        if (VALUES == null) {
            VALUES = new KnownStatePredicate[]{
                NEVER,
                ALWAYS,
                IS_COLLISION_SHAPE_FULL_BLOCK,
                CAUSES_SUFFOCATION,
                NOT_CLOSED_SHULKER,
                NOT_EXTENDED_PISTON,
                MAX_SNOW_LAYERS
            };
        }
        return VALUES;
    }

    public static @Nullable KnownStatePredicate valueOf(Identifier key) {
        return Arrays.stream(values()).filter(value -> value.key.equals(key)).findFirst().orElse(null);
    }

}
