package spout.util.mapping.handle;

import java.util.function.Consumer;

/**
 * A step that can be applied to a {@link AbstractMappingHandle} as a single operation.
 */
public interface MappingStep<H extends AbstractMappingHandle<?>> extends Consumer<H> {

    /**
     * Applies this mapping.
     *
     * @param handle The handle being mapped.
     */
    void apply(H handle);

    @Override
    default void accept(H handle) {
        this.apply(handle);
    }

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

    static <T, H extends AbstractMappingHandle<T>> T applyChain(H handle, Consumer<H>[] chain) {
        for (Consumer<H> mapping : chain) {
            mapping.accept(handle);
        }
        return handle.getImmutable();
    }

}
