package spout.clientview.packetmapping.blockstate.apply;

import java.util.function.Consumer;

import spout.util.mapping.handle.CodeMappingStep;

/**
 * A {@link BlockStateMappingStep} that is defined by code.
 *
 * <p>
 * This is an extension of {@link CodeMappingStep}.
 * </p>
 */
public record CodeBlockStateMappingStep(Consumer<BlockStateMappingHandle> code, boolean requiresCoordinates) implements BlockStateMappingStep {

    @Override
    public void apply(BlockStateMappingHandle handle) {
        this.code.accept(handle);
    }

}
