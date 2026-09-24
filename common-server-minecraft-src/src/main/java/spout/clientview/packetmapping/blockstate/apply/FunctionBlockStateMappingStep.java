package spout.clientview.packetmapping.blockstate.apply;

import java.util.function.Consumer;

import spout.util.mapping.handle.FunctionMappingStep;

/**
 * A {@link BlockStateMappingStep} that is defined by a function.
 *
 * <p>
 * This is a conceptual extension of {@link FunctionMappingStep}.
 * </p>
 */
public record FunctionBlockStateMappingStep(Consumer<BlockStateMappingHandle> function, boolean requiresCoordinates) implements BlockStateMappingStep {

    @Override
    public void apply(BlockStateMappingHandle handle) {
        this.function.accept(handle);
    }

}
