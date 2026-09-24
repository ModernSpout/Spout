package spout.api.clientview.packetmapping.blockstate.handle;

import spout.api.clientview.packetmapping.common.context.WithClientViewMappingContext;
import spout.server.paper.api.packetmapping.block.BlockMappingBuilder;

/**
 * A context to include in {@link BlockStateMappingHandle}s.
 */
public interface BlockStateMappingContext extends WithClientViewMappingContext {

    /**
     * @return Whether the block state on which this mapping is being applied
     * is the block state of a physical block, i.e. a block at certain coordinates in a world.
     *
     * <p>
     * An example of where this method would return false is the block state of a block display entity.
     * </p>
     */
    boolean isStateOfPhysicalBlockInWorld();

    /**
     * @return The x-coordinate of the physical block for which this mapping is being applied.
     * This is only available if {@link #isStateOfPhysicalBlockInWorld()} is true
     * and if {@link BlockMappingBuilder#to} had {@code requiresCoordinates = true}.
     * Otherwise, the returned value is meaningless.
     */
    int getPhysicalBlockX();

    /**
     * @return The y-coordinate of the physical block for which this mapping is being applied,
     * This is only available if {@link #isStateOfPhysicalBlockInWorld()} is true
     * and if {@link BlockMappingBuilder#to} had {@code requiresCoordinates = true}.
     * Otherwise, the returned value is meaningless.
     */
    int getPhysicalBlockY();

    /**
     * @return The z-coordinate of the physical block for which this mapping is being applied,
     * This is only available if {@link #isStateOfPhysicalBlockInWorld()} is true
     * and if {@link BlockMappingBuilder#to} had {@code requiresCoordinates = true}.
     * Otherwise, the returned value is meaningless.
     */
    int getPhysicalBlockZ();

}
