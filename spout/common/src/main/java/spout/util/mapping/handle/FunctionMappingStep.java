package spout.util.mapping.handle;

import java.util.function.Consumer;

/**
 * A {@link MappingStep} that is defined by a function.
 */
public record FunctionMappingStep<T, H extends AbstractMappingHandle<T>>(Consumer<H> code) implements MappingStep<H> {

    @Override
    public void apply(H handle) {
        this.code.accept(handle);
    }

}
