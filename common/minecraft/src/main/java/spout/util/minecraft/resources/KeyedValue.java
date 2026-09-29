package spout.util.minecraft.resources;

import net.minecraft.resources.Identifier;

/**
 * A pair of an object and an {@link Identifier}.
 */
public record KeyedValue<T>(Identifier identifier, T value) {

    public static <T> KeyedValue<T> of(Identifier identifier, T value) {
        return new KeyedValue<>(identifier, value);
    }

}
