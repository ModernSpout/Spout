package spout.api.util.mapping.builder;

/**
 * A mapping builder
 * that has zero or one particular result.
 */
public interface ToRegistryEntryBuilder<T> extends ToRegistryEntry<T> {

    /**
     * Sets the desired result of this builder.
     *
     * <p>
     * This replaces any previous value set.
     * </p>
     */
    void setTo(T to);

}
