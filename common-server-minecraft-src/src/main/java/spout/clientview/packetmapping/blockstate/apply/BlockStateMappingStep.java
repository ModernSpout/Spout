package spout.clientview.packetmapping.blockstate.apply;

import spout.util.mapping.handle.MappingStep;

/**
 * A step that can be applied to a {@link BlockStateMappingHandle} as a single operation.
 *
 * <p>
 *     This is an extension of {@link MappingStep}.
 * </p>
 */
public interface BlockStateMappingStep {

    /**
     * Applies this mapping.
     *
     * @param handle The handle being mapped.
     */
    void apply(BlockStateMappingHandle handle);

    /**
     * @return Whether this step always maps to the same specific value.
     */
    default boolean isDirect() {
        return false;
    }

    /**
     * @return Whether this step depends on the coordinates of the block state being mapped.
     */
    default boolean requiresCoordinates() {
        return false;
    }

}
