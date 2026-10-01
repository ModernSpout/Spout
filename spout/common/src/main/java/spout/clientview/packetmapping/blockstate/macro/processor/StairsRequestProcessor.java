package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockMacro;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.gamecontent.datadriven.block.registry.VanillaOnlyBlockRegistry;
import spout.clientview.packetmapping.blockstate.macro.processor.claimablestates.BlockDynamicClaimableStates;
import spout.clientview.packetmapping.blockstate.macro.processor.claimablestates.DynamicClaimableStates;

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
    public static final DynamicClaimableStates STAIRS_PROXY_BLOCKS = BlockDynamicClaimableStates.forProxy(() ->
        // Copper
        Stream.of(false, true).flatMap(waxed -> Arrays.stream(WeatheringCopper.WeatherState.values()).map(weatherState -> (waxed ? Blocks.CUT_COPPER_STAIRS.waxed() : Blocks.CUT_COPPER_STAIRS.weathering()).pick(weatherState))).toList()
    );

    public static final FillPromiseGetter<FromToBlockMacro, RequestBasedResult> CLAIM_FALLBACK_PROMISE_GETTER = claimFallbackStatesForAllStatesAtOnceByBlock(
        Stream.concat(Stream.of(Blocks.STONE_STAIRS), StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false).filter(block -> block instanceof StairBlock)).distinct().toList()
    );

}
