package spout.api.util.mapping.builder;

import org.jspecify.annotations.Nullable;
import java.util.List;

/**
 * Provides getters for {@link FromRegistryEntryBuilder}.
 */
public interface FromRegistryEntry<T> {

    /**
     * The current target for this builder,
     * or null if not set.
     */
    @Nullable List<? extends T> getFrom();

}
