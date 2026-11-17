package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockStateMacro;
import spout.clientview.packetmapping.blockstate.macro.processor.claimablestates.DynamicClaimableStates;
import spout.clientview.packetmapping.blockstate.macro.processor.claimablestates.SingletonBlockStateDynamicClaimableStates;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;

/**
 * A {@link BlockStateMappingMacroProcessor} for {@link BlockStateMappingMacroTypes#SUGAR_CANE_STATE}.
 */
public class SugarCaneStateRequestProcessor extends StandardBlockStateRequestProcessor {

    public SugarCaneStateRequestProcessor(FromToBlockStateMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<FromToBlockStateMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<FromToBlockStateMacro, RequestBasedResult>.FillPromise kickoff) {
        return super.constructFillPromise(
            kickoff
                .then(this.attemptToClaimStatesFillPromiseStateByState(SUGAR_CANE_PROXY_STATES::get, false))
        );
    }

    @Override
    protected FillPromise getSafeFallbackFillPromise() {
        return CLAIM_FALLBACK_PROMISE_GETTER.get(this);
    }

    /**
     * A new {@link DynamicClaimableStates} instance,
     * for {@link BlockState}s that can be attempted to be claimed as soul fire proxies.
     */
    public static final DynamicClaimableStates SUGAR_CANE_PROXY_STATES = SingletonBlockStateDynamicClaimableStates.forProxy(
        () -> Blocks.SUGAR_CANE.getStateDefinition().getPossibleStates()
    );

    public static final List<BlockState> SAFE_FALLBACK_STATES = SugarCaneRequestProcessor.SAFE_FALLBACK_BLOCKS
        .stream().map(Block::defaultBlockState).toList();

    public static final FillPromiseGetter<FromToBlockStateMacro, RequestBasedResult> CLAIM_FALLBACK_PROMISE_GETTER = claimFallbackStatesForAllStatesAtOnceByBlockState(
        SAFE_FALLBACK_STATES
    );

}
