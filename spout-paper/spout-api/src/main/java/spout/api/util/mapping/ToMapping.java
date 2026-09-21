package spout.api.util.mapping;

/**
 * A mapping that has one particular result.
 */
public interface ToMapping<T> {

    /**
     * @return The result of this mapping.
     */
    T getTo();

}
