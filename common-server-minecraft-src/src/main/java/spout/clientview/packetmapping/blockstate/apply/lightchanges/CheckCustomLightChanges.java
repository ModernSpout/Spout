package spout.clientview.packetmapping.blockstate.apply.lightchanges;

import net.minecraft.world.level.block.state.BlockState;
import spout.clientview.model.ClientViewImpl;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.packetmapping.blockstate.apply.BlockStateInPacketMapper;
import spout.clientview.packetmapping.blockstate.apply.BlockStateMappingsApplicationContext;
import java.util.Arrays;

/**
 * A utility class to check whether light changes require extra sending
 * to client who will not simulate them the same.
 */
public final class CheckCustomLightChanges {

    private CheckCustomLightChanges() {
        throw new UnsupportedOperationException();
    }

    private static final BlockStateMappingsApplicationContext[] simulatedContexts = Arrays.stream(AwarenessLevels.getThatDoNotAlwaysUnderstandsAllServerSideBlocks()).map(awarenessLevel ->
        new BlockStateMappingsApplicationContext(ClientViewImpl.getSimulatedForAwarenessLevel(awarenessLevel))
    ).toArray(BlockStateMappingsApplicationContext[]::new);

    public static boolean requiresExtraLightPacketSending(BlockState oldState, BlockState newState) {
        for (BlockStateMappingsApplicationContext simulatedContext : simulatedContexts) {
            if (BlockStateInPacketMapper.get().apply(oldState, simulatedContext).getLightEmission() != oldState.getLightEmission() || BlockStateInPacketMapper.get().apply(newState, simulatedContext).getLightEmission() != newState.getLightEmission()) {
                return true;
            }
        }
        return false;
    }

}
