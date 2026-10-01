package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopperCollection;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockStateMacro;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.clientview.packetmapping.blockstate.macro.processor.claimablestates.DynamicClaimableStates;
import spout.clientview.packetmapping.blockstate.macro.processor.claimablestates.SingletonBlockStateDynamicClaimableStates;

/**
 * A {@link BlockStateMappingMacroProcessor} for {@link BlockStateMappingMacroTypes#FULL_BLOCK}.
 */
public class FullBlockStateRequestProcessor extends FilledArrayResultProcessor<FromToBlockStateMacro, ArrayResultProcessor.RequestBasedResult> {

    public FullBlockStateRequestProcessor(FromToBlockStateMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<FromToBlockStateMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<FromToBlockStateMacro, RequestBasedResult>.FillPromise kickoff) {
        return kickoff
            .then(this.attemptToClaimStatesFillPromiseStateByState(FULL_BLOCK_PROXY_STATES::get, false))
            .then(CLAIM_FALLBACK_PROMISE_GETTER.get(this))
            .then(new StateFallbackFillPromise(this.macro.fallbackState));
    }

    /**
     * A new {@link DynamicClaimableStates} instance,
     * for {@link BlockState}s that can be attempted to be claimed as full block proxies.
     */
    public static final DynamicClaimableStates FULL_BLOCK_PROXY_STATES = SingletonBlockStateDynamicClaimableStates.forProxy(() -> {
        // Build the claimable states
        List<BlockState> states = new ArrayList<>();
        // Blocks for which every block state is a full block
        List.of(
            // Beehive and bee nest
            Blocks.BEEHIVE,
            Blocks.BEE_NEST
        ).forEach(block -> states.addAll(block.getStateDefinition().getPossibleStates()));
        // Copper
        List<WeatheringCopperCollection<Block>> copper = List.of(
            Blocks.CHISELED_COPPER,
            Blocks.COPPER_BLOCK,
            Blocks.COPPER_GRATE,
            Blocks.CUT_COPPER
        );
        for (boolean waxed : new boolean[]{false, true}) {
            for (WeatheringCopper.WeatherState weatherState : WeatheringCopper.WeatherState.values()) {
                copper.forEach(collection -> {
                    states.addAll((waxed ? collection.waxed() : collection.weathering()).pick(weatherState).getStateDefinition().getPossibleStates());
                });
            }
        }
        // Copper bulb
        for (boolean waxed : new boolean[]{false, true}) {
            for (WeatheringCopper.WeatherState weatherState : WeatheringCopper.WeatherState.values()) {
                states.addAll((waxed ? Blocks.COPPER_BULB.waxed() : Blocks.COPPER_BULB.weathering()).pick(weatherState).getStateDefinition().getPossibleStates());
            }
        }
        // More blocks for which every block state is a full block
        List.of(
            // Creaking heart
            Blocks.CREAKING_HEART,
            // Dispenser and dropper
            Blocks.DISPENSER,
            Blocks.DROPPER,
            // Every state is the same
            Blocks.JUKEBOX,
            Blocks.NOTE_BLOCK,
            Blocks.TARGET,
            Blocks.TNT,
            // Infested
            Blocks.CHISELED_STONE_BRICKS,
            Blocks.COBBLESTONE,
            Blocks.CRACKED_STONE_BRICKS,
            Blocks.DEEPSLATE,
            Blocks.MOSSY_STONE_BRICKS,
            Blocks.STONE,
            Blocks.STONE_BRICKS,
            Blocks.INFESTED_CHISELED_STONE_BRICKS,
            Blocks.INFESTED_COBBLESTONE,
            Blocks.INFESTED_CRACKED_STONE_BRICKS,
            Blocks.INFESTED_DEEPSLATE,
            Blocks.INFESTED_MOSSY_STONE_BRICKS,
            Blocks.INFESTED_STONE,
            Blocks.INFESTED_STONE_BRICKS,
            // Snowy mycelium and podzol
            Blocks.GRASS_BLOCK,
            Blocks.MYCELIUM,
            Blocks.PODZOL
        ).forEach(block -> states.addAll(block.getStateDefinition().getPossibleStates()));
        // Slabs
        List<Block> slabs = new ArrayList<>(List.of(
            Blocks.ACACIA_SLAB,
            Blocks.ANDESITE_SLAB,
            Blocks.BAMBOO_MOSAIC_SLAB,
            Blocks.BAMBOO_SLAB,
            Blocks.BIRCH_SLAB,
            Blocks.BLACKSTONE_SLAB,
            Blocks.BRICK_SLAB,
            Blocks.CHERRY_SLAB,
            Blocks.COBBLED_DEEPSLATE_SLAB,
            Blocks.COBBLESTONE_SLAB,
            Blocks.CRIMSON_SLAB,
            Blocks.CUT_RED_SANDSTONE_SLAB,
            Blocks.CUT_SANDSTONE_SLAB,
            Blocks.DARK_OAK_SLAB,
            Blocks.DARK_PRISMARINE_SLAB,
            Blocks.DEEPSLATE_BRICK_SLAB,
            Blocks.DEEPSLATE_TILE_SLAB,
            Blocks.DIORITE_SLAB,
            Blocks.END_STONE_BRICK_SLAB,
            Blocks.GRANITE_SLAB,
            Blocks.JUNGLE_SLAB,
            Blocks.MANGROVE_SLAB,
            Blocks.MOSSY_COBBLESTONE_SLAB,
            Blocks.MOSSY_STONE_BRICK_SLAB,
            Blocks.MUD_BRICK_SLAB,
            Blocks.NETHER_BRICK_SLAB,
            Blocks.OAK_SLAB,
            Blocks.PALE_OAK_SLAB,
            Blocks.PETRIFIED_OAK_SLAB,
            Blocks.POLISHED_ANDESITE_SLAB,
            Blocks.POLISHED_BLACKSTONE_BRICK_SLAB,
            Blocks.POLISHED_BLACKSTONE_SLAB,
            Blocks.POLISHED_DEEPSLATE_SLAB,
            Blocks.POLISHED_DIORITE_SLAB,
            Blocks.POLISHED_GRANITE_SLAB,
            Blocks.POLISHED_TUFF_SLAB,
            Blocks.PRISMARINE_BRICK_SLAB,
            Blocks.PRISMARINE_SLAB,
            Blocks.PURPUR_SLAB,
            Blocks.QUARTZ_SLAB,
            Blocks.RED_NETHER_BRICK_SLAB,
            Blocks.RED_SANDSTONE_SLAB,
            Blocks.RESIN_BRICK_SLAB,
            Blocks.SANDSTONE_SLAB,
            Blocks.SMOOTH_QUARTZ_SLAB,
            Blocks.SMOOTH_RED_SANDSTONE_SLAB,
            Blocks.SMOOTH_SANDSTONE_SLAB,
            Blocks.SPRUCE_SLAB,
            Blocks.STONE_BRICK_SLAB,
            Blocks.STONE_SLAB,
            Blocks.TUFF_BRICK_SLAB,
            Blocks.TUFF_SLAB,
            Blocks.WARPED_SLAB
        ));
        Blocks.CUT_COPPER_SLAB.forEach(slabs::add);
        slabs.forEach(block -> states.add(block.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.DOUBLE)));
        return states;
    });

    public static final FillPromiseGetter<FromToBlockStateMacro, RequestBasedResult> CLAIM_FALLBACK_PROMISE_GETTER = claimFallbackStatesForAllStatesAtOnceByBlockState(
        FullBlockRequestProcessor.SAFE_FALLBACK_BLOCKS.stream().map(Block::defaultBlockState).toList()
    );

}
