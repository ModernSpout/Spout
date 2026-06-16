package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockMacro;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMapping;
import spout.server.paper.impl.moredatadriven.minecraft.VanillaOnlyBlockRegistry;
import spout.server.paper.impl.packetmapping.block.automatic.BlockDynamicClaimableStates;
import spout.server.paper.impl.packetmapping.block.automatic.DynamicClaimableStates;

/**
 * A {@link BlockStateMappingMacroProcessor} for {@link BlockStateMappingMacroTypes#ROTATED_PILLAR}.
 */
public class RotatedPillarRequestProcessor extends FilledArrayResultProcessor<FromToBlockMacro, ArrayResultProcessor.RequestBasedResult> {

    public RotatedPillarRequestProcessor(FromToBlockMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise kickoff) {
        return kickoff
            .then(this.attemptToClaimStatesFillPromiseForAllStatesAtOnceForBlock(ROTATED_PILLAR_PROXY_BLOCKS::get, Blocks.QUARTZ_PILLAR, false))
            .then(this.attemptToClaimStatesFillPromiseStateByState(FullBlockStateRequestProcessor.FULL_BLOCK_PROXY_STATES::get, false))
            .then(CLAIM_FALLBACK_PROMISE_GETTER.get(this))
            .then(new BlockFallbackFillPromise(this.macro.fallbackBlock));
    }

    /**
     * A new {@link DynamicClaimableStates} instance,
     * for {@link Block}s that can be attempted to be claimed as rotated pillar proxies.
     */
    public static final DynamicClaimableStates ROTATED_PILLAR_PROXY_BLOCKS = BlockDynamicClaimableStates.forProxy(() -> List.of(
        // Infested
        Blocks.DEEPSLATE,
        Blocks.INFESTED_DEEPSLATE
    ));

    public static final FillPromiseGetter<FromToBlockMacro, RequestBasedResult> CLAIM_FALLBACK_PROMISE_GETTER = claimFallbackStatesForAllStatesAtOnceByBlock(
        Stream.concat(Stream.of(Blocks.QUARTZ_PILLAR), StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false).filter(block -> block instanceof RotatedPillarBlock)).distinct().toList()
    );

}
