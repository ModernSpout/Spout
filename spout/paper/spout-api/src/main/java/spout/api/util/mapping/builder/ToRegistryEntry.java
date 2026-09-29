package spout.api.util.mapping.builder;

import org.jspecify.annotations.Nullable;

/**
 * Provides getters for {@link ToRegistryEntryBuilder}.
 */
public interface ToRegistryEntry<T> {

    /**
     * @return The desired result of this builder,
     * or null if not set.
     */
    @Nullable T getTo();

}
