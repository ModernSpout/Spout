package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockMacro;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMapping;

/**
 * A {@link BlockStateMappingMacroProcessor} for {@link BlockStateMappingMacro}s
 * that are fulfilled using full blocks but backed by a block rather than a block state.
 */
public class FullBlockRequestProcessor extends StandardBlockTypeRequestProcessor {

    public FullBlockRequestProcessor(FromToBlockMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise kickoff) {
        return super.constructFillPromise(
            kickoff
                .then(attemptToClaimStatesFillPromiseStateByState(FullBlockStateRequestProcessor.FULL_BLOCK_PROXY_STATES::get, false))
        );
    }

    @Override
    protected List<Block> getFallbackBlocks() {
        return SAFE_FALLBACK_BLOCKS;
    }

    public static final List<Block> SAFE_FALLBACK_BLOCKS = List.of(Blocks.STONE, Blocks.OAK_PLANKS, Blocks.DIRT, Blocks.SAND, Blocks.GLASS, Blocks.HAY_BLOCK);

}
