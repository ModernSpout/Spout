package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockStateMacro;
import spout.clientview.packetmapping.blockstate.macro.processor.claimablestates.DynamicClaimableStates;
import spout.clientview.packetmapping.blockstate.macro.processor.claimablestates.SingletonBlockStateDynamicClaimableStates;

/**
 * Like {@link FullBlockStateRequestProcessor},
 * but for states that support transparent textures.
 */
public class TransparentFullBlockStateRequestProcessor extends StandardBlockStateRequestProcessor {

    public TransparentFullBlockStateRequestProcessor(FromToBlockStateMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<FromToBlockStateMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<FromToBlockStateMacro, RequestBasedResult>.FillPromise kickoff) {
        return super.constructFillPromise(
            kickoff
                .then(this.attemptToClaimStatesFillPromiseStateByState(TRANSPARENT_FULL_BLOCK_PROXY_STATES::get, false))
        );
    }

    @Override
    protected FillPromise getSafeFallbackFillPromise() {
        return FullBlockStateRequestProcessor.CLAIM_FALLBACK_PROMISE_GETTER.get(this);
    }

    /**
     * Analogous to {@link FullBlockStateRequestProcessor#FULL_BLOCK_PROXY_STATES}.
     */
    public static final DynamicClaimableStates TRANSPARENT_FULL_BLOCK_PROXY_STATES = SingletonBlockStateDynamicClaimableStates.forProxy(() -> {
        // Build the claimable states
        List<BlockState> states = new ArrayList<>();
        // Copper grate
        for (boolean waxed : new boolean[]{false, true}) {
            for (WeatheringCopper.WeatherState weatherState : WeatheringCopper.WeatherState.values()) {
                states.addAll((waxed ? Blocks.COPPER_GRATE.waxed() : Blocks.COPPER_GRATE.weathering()).pick(weatherState).getStateDefinition().getPossibleStates());
            }
        }
        return states;
    });

}
