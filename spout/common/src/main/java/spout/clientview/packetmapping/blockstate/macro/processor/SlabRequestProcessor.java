package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.SlabMacro;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.gamecontent.datadriven.block.registry.VanillaOnlyBlockRegistry;
import spout.clientview.packetmapping.blockstate.macro.processor.claimablestates.BlockDynamicClaimableStates;
import spout.clientview.packetmapping.blockstate.macro.processor.claimablestates.DynamicClaimableStates;

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
    public static final DynamicClaimableStates SLAB_PROXY_BLOCKS = BlockDynamicClaimableStates.forProxy(() -> Stream.concat(
        // Copper
        Stream.of(false, true).flatMap(waxed -> Arrays.stream(WeatheringCopper.WeatherState.values()).map(weatherState -> (waxed ? Blocks.CUT_COPPER_SLAB.waxed() : Blocks.CUT_COPPER_SLAB.weathering()).pick(weatherState))),
        Stream.of(
            // Petrified oak
            Blocks.OAK_SLAB,
            Blocks.PETRIFIED_OAK_SLAB
        )
    ).toList());

    public static final FillPromiseGetter<SlabMacro, RequestBasedResult> CLAIM_FALLBACK_PROMISE_GETTER = claimFallbackStatesForAllStatesAtOnceByBlock(
        Stream.concat(Stream.of(Blocks.STONE_SLAB), StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false).filter(block -> block instanceof SlabBlock)).distinct().toList()
    );

}
