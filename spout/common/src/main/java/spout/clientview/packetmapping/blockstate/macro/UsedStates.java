package spout.clientview.packetmapping.blockstate.macro;

import it.unimi.dsi.fastutil.objects.ObjectBooleanPair;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The states that are chosen as mapping targets for a
 * requested proxy mapping.
 */
public interface UsedStates {

    /**
     * @return The used block state for the given 'from' block state (i.e. the block state
     * for which the mapping was requested), and whether the used block state is a proxy
     * (if false, it is fallback).
     */
    ObjectBooleanPair<BlockState> get(BlockState from);

}
