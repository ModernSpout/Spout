package spout.gamecontent.datadriven.common.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapDecoder;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.Pair;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.function.Predicate;

/**
 * A utility class that inspects a given {@link MapCodec}
 * to see if it was created using a {@link RecordCodecBuilder} syntax,
 * and if so, with what {@link MapCodec#fieldOf} it was created.
 */
public final class RecordCodedFieldScanner {

    private RecordCodedFieldScanner() {
        throw new UnsupportedOperationException();
    }

    /**
     * @param fieldCodecPredicate A predicate for which field codecs should be included.
     * @return The list of field names, with their field codec,
     * that ws found.
     */
    public static List<Pair<String, Codec<?>>> getRecordCodecFields(MapCodec<?> codec, Predicate<Codec<?>> fieldCodecPredicate) {
        List<Pair<String, Codec<?>>> result = new ArrayList<>(0);
        IdentityHashMap<Object, Boolean> visited =
            new IdentityHashMap<>();
        recurse(codec, visited, result, fieldCodecPredicate);
        return result;
    }

    private static void recurse(
        Object codec,
        IdentityHashMap<Object, Boolean> visited,
        List<Pair<String, Codec<?>>> result,
        Predicate<Codec<?>> fieldCodecPredicate
    ) {
        if (codec == null) {
            return;
        }
        if (visited.put(codec, Boolean.TRUE) != null) {
            return;
        }
        inspectFieldCodec(codec, result, fieldCodecPredicate);
        Class<?> codecClass = codec.getClass();
        while (codecClass != null && codecClass != Object.class) {
            for (Field field : codecClass.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(codec);
                    if (value == null) {
                        continue;
                    }
                    // Only recurse into codec-related objects
                    if (
                        value instanceof MapCodec<?>
                            || value instanceof MapDecoder<?>
                            || value instanceof Codec<?>
                            || value instanceof RecordCodecBuilder<?, ?>
                    ) {
                        recurse(value, visited, result, fieldCodecPredicate);
                    }
                } catch (Throwable ignored) {
                }
            }
            codecClass = codecClass.getSuperclass();
        }
    }

    private static void inspectFieldCodec(
        Object codec,
        List<Pair<String, Codec<?>>> result,
        Predicate<Codec<?>> fieldCodecPredicate
    ) {
        String fieldName = null;
        Codec<?> foundFieldCodec = null;
        Class<?> codecClass = codec.getClass();
        while (codecClass != null && codecClass != Object.class) {
            for (Field field : codecClass.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(codec);
                    if (value instanceof String s) {
                        String lower = field.getName().toLowerCase();
                        if (
                            lower.contains("name")
                                || lower.contains("field")
                                || lower.equals("key")
                        ) {
                            fieldName = s;
                        }
                    }
                    if (value instanceof Codec<?> c) {
                        foundFieldCodec = c;
                    }
                } catch (Throwable ignored) {
                }
            }
            codecClass = codecClass.getSuperclass();
        }
        if (fieldName == null || foundFieldCodec == null) {
            return;
        }
        if (fieldCodecPredicate.test(foundFieldCodec)) {
            result.add(Pair.of(fieldName, foundFieldCodec));
        }
    }

}
