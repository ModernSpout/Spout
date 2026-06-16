package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockMacro;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMapping;
import spout.server.paper.impl.moredatadriven.minecraft.VanillaOnlyBlockRegistry;
import spout.server.paper.impl.packetmapping.block.automatic.BlockDynamicClaimableStates;
import spout.server.paper.impl.packetmapping.block.automatic.DynamicClaimableStates;

/**
 * A {@link BlockStateMappingMacroProcessor} for {@link BlockStateMappingMacroTypes#STAIRS}.
 */
public class StairsRequestProcessor extends FilledArrayResultProcessor<FromToBlockMacro, ArrayResultProcessor.RequestBasedResult> {

    public StairsRequestProcessor(FromToBlockMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise kickoff) {
        return kickoff
            .then(this.attemptToClaimStatesFillPromiseForAllStatesAtOnceForBlock(STAIRS_PROXY_BLOCKS::get, Blocks.STONE_STAIRS, false))
            // TODO optionally claim inner/outer stair duplicates
            .then(CLAIM_FALLBACK_PROMISE_GETTER.get(this))
            .then(new BlockFallbackFillPromise(this.macro.fallbackBlock));
    }

    /**
     * A new {@link DynamicClaimableStates} instance,
     * for {@link Block}s that can be attempted to be claimed as stairs proxies.
     */
    public static final DynamicClaimableStates STAIRS_PROXY_BLOCKS = BlockDynamicClaimableStates.forProxy(() -> List.of(
        // Copper
        Blocks.CUT_COPPER_STAIRS,
        Blocks.EXPOSED_CUT_COPPER_STAIRS,
        Blocks.OXIDIZED_CUT_COPPER_STAIRS,
        Blocks.WEATHERED_CUT_COPPER_STAIRS,
        Blocks.WAXED_CUT_COPPER_STAIRS,
        Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS,
        Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS,
        Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS
    ));

    public static final FillPromiseGetter<FromToBlockMacro, RequestBasedResult> CLAIM_FALLBACK_PROMISE_GETTER = claimFallbackStatesForAllStatesAtOnceByBlock(
        Stream.concat(Stream.of(Blocks.STONE_STAIRS), StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false).filter(block -> block instanceof StairBlock)).distinct().toList()
    );

}
