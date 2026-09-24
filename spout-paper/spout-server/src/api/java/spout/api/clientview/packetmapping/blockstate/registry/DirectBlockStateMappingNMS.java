package spout.api.clientview.packetmapping.blockstate.registry;

import net.minecraft.world.level.block.state.BlockState;

public interface DirectBlockStateMappingNMS extends BlockStateMappingNMS, DirectBlockStateMapping {

    /**
     * @see #getTo()
     */
    BlockState getToNMS();

}
