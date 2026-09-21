package spout.api.util.mapping.handle;

/**
 * A {@link MappingHandle} where the original data can be observed.
 */
public interface WithOriginalMappingHandle<T> extends MappingHandle<T> {

    /**
     * @return The original data, before any mappings were applied.
     * The returned instance must not be modified.
     */
    T getOriginal();

}
