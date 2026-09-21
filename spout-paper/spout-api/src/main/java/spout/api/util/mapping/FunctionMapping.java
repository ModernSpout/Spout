package spout.api.util.mapping;

import java.util.function.Consumer;

/**
 * A mapping that applies a specific function.
 */
public interface FunctionMapping<T> {

    /**
     * @return The function that is applied for this mapping.
     */
    Consumer<T> getToFunction();

}
