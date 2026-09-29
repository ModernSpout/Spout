package spout.api.util.mapping.handle;

/**
 * A {@link MappingHandle} that can provide context for the current mapping.
 */
public interface WithContextMappingHandle<T, C> extends MappingHandle<T> {

    /**
     * @return The context for which the current mapping is being applied.
     */
    C getContext();

}
