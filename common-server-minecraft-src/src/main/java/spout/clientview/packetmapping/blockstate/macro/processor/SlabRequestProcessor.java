package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.SlabMacro;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMapping;
import spout.server.paper.impl.moredatadriven.minecraft.VanillaOnlyBlockRegistry;
import spout.server.paper.impl.packetmapping.block.automatic.BlockDynamicClaimableStates;
import spout.server.paper.impl.packetmapping.block.automatic.DynamicClaimableStates;

/**
 * A {@link BlockStateMappingMacroProcessor} for {@link BlockStateMappingMacroTypes#SLAB}.
 */
public class SlabRequestProcessor extends FilledArrayResultProcessor<SlabMacro, ArrayResultProcessor.RequestBasedResult> {

    public SlabRequestProcessor(SlabMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<SlabMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<SlabMacro, RequestBasedResult>.FillPromise kickoff) {
        return kickoff
            .then(this.attemptToClaimStatesFillPromiseForAllStatesAtOnceForBlock(SLAB_PROXY_BLOCKS::get, Blocks.STONE_SLAB, false))
            // TODO claim full block fallbacks
            .then(CLAIM_FALLBACK_PROMISE_GETTER.get(this))
            .then(new BlockFallbackFillPromise(this.macro.fallbackBlock));
    }

    /**
     * A new {@link DynamicClaimableStates} instance,
     * for {@link Block}s that can be attempted to be claimed as slab proxies.
     */
    public static final DynamicClaimableStates SLAB_PROXY_BLOCKS = BlockDynamicClaimableStates.forProxy(() -> List.of(
        // Copper
        Blocks.CUT_COPPER_SLAB,
        Blocks.EXPOSED_CUT_COPPER_SLAB,
        Blocks.OXIDIZED_CUT_COPPER_SLAB,
        Blocks.WEATHERED_CUT_COPPER_SLAB,
        Blocks.WAXED_CUT_COPPER_SLAB,
        Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB,
        Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB,
        Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB,
        // Petrified oak
        Blocks.OAK_SLAB,
        Blocks.PETRIFIED_OAK_SLAB
    ));

    public static final FillPromiseGetter<SlabMacro, RequestBasedResult> CLAIM_FALLBACK_PROMISE_GETTER = claimFallbackStatesForAllStatesAtOnceByBlock(
        Stream.concat(Stream.of(Blocks.STONE_SLAB), StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false).filter(block -> block instanceof SlabBlock)).distinct().toList()
    );

}
