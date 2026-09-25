package spout.api.clientview.packetmapping.blockstate;

import net.minecraft.world.level.block.state.BlockState;

public interface DirectBlockStateMappingNMS extends BlockStateMappingNMS, DirectBlockStateMapping {

    /**
     * @see #getTo()
     */
    BlockState getToNMS();

}
