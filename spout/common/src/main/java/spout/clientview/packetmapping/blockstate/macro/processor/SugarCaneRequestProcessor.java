package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockMacro;
import spout.clientview.packetmapping.blockstate.macro.processor.claimablestates.DynamicClaimableStates;
import spout.clientview.packetmapping.blockstate.macro.processor.claimablestates.SingletonBlockStateDynamicClaimableStates;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;

/**
 * A {@link BlockStateMappingMacroProcessor} for {@link BlockStateMappingMacroTypes#SUGAR_CANE}.
 */
public class SugarCaneRequestProcessor extends StandardBlockRequestProcessor {

    public SugarCaneRequestProcessor(FromToBlockMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise kickoff) {
        return super.constructFillPromise(
            kickoff
                .then(this.attemptToClaimStatesFillPromiseStateByState(SUGAR_CANE_PROXY_STATES::get, false))
        );
    }

    @Override
    protected List<Block> getFallbackBlocks() {
        return SAFE_FALLBACK_BLOCKS;
    }

    /**
     * A new {@link DynamicClaimableStates} instance,
     * for {@link BlockState}s that can be attempted to be claimed as sugar cane proxies.
     */
    public static final DynamicClaimableStates SUGAR_CANE_PROXY_STATES = SingletonBlockStateDynamicClaimableStates.forProxy(
        () -> Blocks.SUGAR_CANE.getStateDefinition().getPossibleStates()
    );

    public static final List<Block> SAFE_FALLBACK_BLOCKS = List.of(Blocks.SUGAR_CANE);

}
