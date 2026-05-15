package spout.common.moredatadriven.minecraft.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import spout.client.fabric.moredatadriven.minecraft.type.mixin.BlocksStatePredicatesAccessor;

/**
 * A simple enum for the possible values of {@link BlockBehaviour.StatePredicate}s used as values
 * of fields of {@link BlockBehaviour.Properties}.
 */
public enum KnownStatePredicate implements BlockBehaviour.StatePredicate {

    NEVER(Blocks::never),
    ALWAYS(Blocks::always),
    IS_COLLISION_SHAPE_FULL_BLOCK((state, level, pos) -> state.isCollisionShapeFullBlock(level, pos)),
    BLOCKS_MOTION_AND_IS_COLLISION_SHAPE_FULL_BLOCK((state, level, pos) -> state.blocksMotion() && state.isCollisionShapeFullBlock(level, pos)),
    NOT_CLOSED_SHULKER(BlocksStatePredicatesAccessor.getNotClosedShulker()),
    NOT_EXTENDED_PISTON(BlocksStatePredicatesAccessor.getNotExtendedPiston()),
    MAX_SNOW_LAYERS((state, level, pos) -> state.getValue(SnowLayerBlock.LAYERS) >= 8),
    SCULK_PHASE_ACTIVE((state, level, pos) -> SculkSensorBlock.getPhase(state) == SculkSensorPhase.ACTIVE);

    public final BlockBehaviour.StatePredicate predicate;

    KnownStatePredicate(BlockBehaviour.StatePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean test(BlockState state, BlockGetter level, BlockPos pos) {
        return this.predicate.test(state, level, pos);
    }

    private static final KnownStatePredicate[] VALUES = values();

    public static KnownStatePredicate wrap(BlockBehaviour.StatePredicate predicate) {
        KnownStatePredicate foundValue = Arrays.stream(VALUES).filter(value -> value.predicate.equals(predicate)).findAny().orElse(null);
        if (foundValue != null) {
            return foundValue;
        }
        if (((BlockBehaviour.StatePredicate) Blocks::never).equals(predicate)) {
            return NEVER;
        }
        if (((BlockBehaviour.StatePredicate) Blocks::always).equals(predicate)) {
            return ALWAYS;
        }
        // Use an ugly Reflection trick to detect whether the given predicate is Blocks::never or Blocks::always
        try {
            List<Method> alwaysNeverMethods = Arrays.stream(Blocks.class.getDeclaredMethods())
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .filter(m -> Modifier.isStatic(m.getModifiers()))
                .filter(m -> m.getReturnType().equals(Boolean.class))
                .filter(m -> m.getParameterCount() == 3)
                .filter(m -> m.getParameterTypes()[0].equals(BlockState.class))
                .filter(m -> m.getParameterTypes()[1].equals(BlockGetter.class))
                .filter(m -> m.getParameterTypes()[2].equals(BlockPos.class))
                .toList();
            alwaysNeverMethods.forEach(Method::trySetAccessible);
            Method alwaysMethod;
            Method neverMethod;
            if ((boolean) alwaysNeverMethods.get(0).invoke(null, null, null, null)) {
                alwaysMethod = alwaysNeverMethods.get(0);
                neverMethod = alwaysNeverMethods.get(1);
            } else {
                alwaysMethod = alwaysNeverMethods.get(1);
                neverMethod = alwaysNeverMethods.get(0);
            }
            Method writeReplaceMethod = predicate.getClass().getDeclaredMethod("writeReplace");
            writeReplaceMethod.trySetAccessible();
            SerializedLambda serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(predicate);
            String implClass = serializedLambda.getImplClass();
            if (implClass.endsWith("/" + Blocks.class.getSimpleName())) {
                String implMethodName = serializedLambda.getImplMethodName();
                if (implMethodName.equals(neverMethod.getName())) {
                    return NEVER;
                } else if (implMethodName.equals(alwaysMethod.getName())) {
                    return ALWAYS;
                }
            }
            throw new IllegalArgumentException("Not a known state predicate: " + predicate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static String temp(Object x) {
        java.util.List<Class<?>> c = new java.util.ArrayList<>();
        Class<?> C = x.getClass();
        while (C != Object.class) {
            c.add(C);
            C = C.getSuperclass();
        }
        return c.toString();
    }

    public static final Codec<KnownStatePredicate> CODEC = Identifier.CODEC.comapFlatMap(key -> {
        if (key.getNamespace().equals(Identifier.DEFAULT_NAMESPACE)) {
            try {
                return DataResult.success(valueOf(key.getPath().toUpperCase(Locale.ROOT)));
            } catch (IllegalArgumentException ignored) {
            }
        }
        return DataResult.error(() -> "Not a known state predicate: " + key);
    }, predicate -> Identifier.parse(predicate.name().toLowerCase(Locale.ROOT)));

}
