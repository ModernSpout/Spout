package spout.util.mapping.handle;

/**
 * A step that can be applied to a {@link AbstractMappingHandle} as a single operation.
 */
public interface MappingStep<H extends AbstractMappingHandle<?>> {

    /**
     * Applies this mapping.
     *
     * @param handle The handle being mapped.
     */
    void apply(H handle);

    /**
     * @return Whether this step always maps to the same specific value.
     */
    default boolean isDirect() {
        return false;
    }

    static <T, H extends AbstractMappingHandle<T>> T applyChain(H handle, MappingStep<H>[] chain) {
        for (MappingStep<H> mapping : chain) {
            mapping.apply(handle);
        }
        return handle.getImmutable();
    }

}
