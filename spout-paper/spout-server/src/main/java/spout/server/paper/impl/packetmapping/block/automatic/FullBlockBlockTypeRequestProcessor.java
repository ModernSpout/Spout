package spout.server.paper.impl.packetmapping.block.automatic;

import spout.server.paper.impl.packetmapping.block.BlockMappingsComposeEventImpl;

/**
 * A {@link RequestProcessor} for {@link AutomaticBlockMappingsImpl} requests
 * that are fulfilled using full blocks but backed by a block rather than a block state.
 */
public class FullBlockBlockTypeRequestProcessor extends StandardBlockTypeRequestProcessor {

    public FullBlockBlockTypeRequestProcessor(FromToBlockTypeRequestBuilderImpl request, BlockMappingsComposeEventImpl event) {
        super(request, event);
    }

    @Override
    protected FilledArrayResultRequestProcessor<FromToBlockTypeRequestBuilderImpl, RequestBasedResult>.FillPromise constructFillPromise(final FilledArrayResultRequestProcessor<FromToBlockTypeRequestBuilderImpl, RequestBasedResult>.FillPromise kickoff) {
        return super.constructFillPromise(
            kickoff
                .then(attemptToClaimStatesFillPromiseStateByState(FullBlockRequestProcessor.FULL_BLOCK_PROXY_STATES::get))
        );
    }

}
