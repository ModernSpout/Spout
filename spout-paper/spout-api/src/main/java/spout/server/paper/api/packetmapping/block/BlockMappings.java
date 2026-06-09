package spout.server.paper.api.packetmapping.block;

import spout.util.composable.Composable;
import spout.server.paper.impl.util.service.SpoutServices;

/**
 * A service for the block mappings that Spout applies.
 */
public interface BlockMappings extends Composable<BlockMappingsComposeEvent> {

    /**
     * @return The {@link BlockMappings} instance.
     */
    static BlockMappings get() {
        return SpoutServices.getBlockMappings();
    }

}
