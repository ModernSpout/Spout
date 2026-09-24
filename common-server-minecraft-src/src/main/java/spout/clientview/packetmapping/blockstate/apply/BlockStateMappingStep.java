package spout.clientview.packetmapping.blockstate.apply;

import spout.util.mapping.handle.MappingStep;

/**
 * A step that can be applied to a {@link BlockStateMappingHandle} as a single operation.
 *
 * <p>
 * This is an extension of {@link MappingStep}.
 * </p>
 */
public sealed interface BlockStateMappingStep extends MappingStep<BlockStateMappingHandle> permits DirectBlockStateMappingStep, FunctionBlockStateMappingStep {

    /**
     * @return Whether this step depends on the coordinates of the block state being mapped.
     */
    default boolean requiresCoordinates() {
        return false;
    }

}
