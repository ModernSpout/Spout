package spout.api.util.mapping.builder;

import org.jspecify.annotations.Nullable;
import java.util.function.Consumer;

/**
 * Provides getters for {@link FunctionRegistryEntryBuilder}.
 */
public interface FunctionRegistryEntry<T> {

    /**
     * @return The function that is applied for this mapping,
     * or null if not set.
     */
    @Nullable Consumer<T> getToFunction();

}
