package spout.clientview.packetmapping.blockstate.macro.processor.claimablestates;

import net.minecraft.world.level.block.state.BlockState;

/**
 * A producer of {@link SortedClaimableStates} instances
 * that may return different values over time.
 */
public interface DynamicClaimableStates {

    SortedClaimableStates get(BlockState from);

}
