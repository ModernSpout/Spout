package spout.clientview.packetmapping.blockstate.macro;

import net.minecraft.world.level.block.state.BlockState;

/**
 * A {@link BlockStateMappingMacro} that maps from and to block states.
 */
public interface FromToBlockStatesMacro {

    BlockState[] getFromStates();

    BlockState[] getFallbackStates();

}
