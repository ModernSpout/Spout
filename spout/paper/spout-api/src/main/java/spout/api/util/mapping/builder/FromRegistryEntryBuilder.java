package spout.api.util.mapping.builder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * A mapping builder
 * that targets zero or more particular target elements.
 */
public interface FromRegistryEntryBuilder<T> extends FromRegistryEntry<T> {

    /**
     * Sets the {@link T} as target for this builder.
     *
     * <p>
     * This replaces any previous value set with {@link #setFrom}.
     * </p>
     */
    default void setFrom(T from) {
        this.setFrom(List.of(from));
    }

    /**
     * @see #setFrom(T)
     */
    default void setFrom(T[] from) {
        this.setFrom(Arrays.asList(from));
    }

    /**
     * @see #setFrom(T)
     */
    void setFrom(Collection<? extends T> from);

    /**
     * Adds a {@link T} as target for this builder.
     */
    void addFrom(T from);

    /**
     * @see #addFrom(T)
     */
    default void addFrom(T[] from) {
        for (T value : from) {
            this.addFrom(value);
        }
    }

    /**
     * @see #addFrom(T)
     */
    default void addFrom(Collection<T> from) {
        for (T value : from) {
            this.addFrom(value);
        }
    }

}
