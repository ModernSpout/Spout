package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockMacro;

/**
 * Like {@link FullBlockRequestProcessor},
 * but for blocks that support transparent textures.
 */
public class TransparentFullBlockRequestProcessor extends StandardBlockRequestProcessor {

    public TransparentFullBlockRequestProcessor(FromToBlockMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise kickoff) {
        return super.constructFillPromise(
            kickoff
                .then(attemptToClaimStatesFillPromiseStateByState(TransparentFullBlockStateRequestProcessor.TRANSPARENT_FULL_BLOCK_PROXY_STATES::get, false))
        );
    }

    @Override
    protected List<Block> getFallbackBlocks() {
        return FullBlockRequestProcessor.SAFE_FALLBACK_BLOCKS;
    }

}
