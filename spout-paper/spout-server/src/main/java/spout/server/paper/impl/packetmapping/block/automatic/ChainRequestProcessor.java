package spout.server.paper.impl.packetmapping.block.automatic;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import spout.server.paper.impl.packetmapping.block.BlockMappingsComposeEventImpl;

/**
 * A {@link RequestProcessor} for {@link AutomaticBlockMappingsImpl#chain}.
 */
public class ChainRequestProcessor extends FilledArrayResultRequestProcessor<FromToBlockTypeRequestBuilderImpl, ArrayResultRequestProcessor.RequestBasedResult> {

    public ChainRequestProcessor(FromToBlockTypeRequestBuilderImpl request, BlockMappingsComposeEventImpl event) {
        super(request, event);
    }

    @Override
    protected FilledArrayResultRequestProcessor<FromToBlockTypeRequestBuilderImpl, RequestBasedResult>.FillPromise constructFillPromise(final FilledArrayResultRequestProcessor<FromToBlockTypeRequestBuilderImpl, RequestBasedResult>.FillPromise kickoff) {
        return kickoff
            .then(attemptToClaimStatesFillPromiseForAllStatesAtOnceForBlock(CHAIN_PROXY_BLOCKS::get, Blocks.IRON_CHAIN))
            .then(new BlockFallbackFillPromise(this.request.fallback));
    }

    /**
     * A new {@link DynamicClaimableStates} instance,
     * for {@link Block}s that can be attempted to be claimed as chain proxies.
     */
    public static final DynamicClaimableStates CHAIN_PROXY_BLOCKS = new BlockDynamicClaimableStates(() -> Blocks.COPPER_CHAIN.asList());

}
