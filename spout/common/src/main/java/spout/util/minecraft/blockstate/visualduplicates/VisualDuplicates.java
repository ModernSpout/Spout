package spout.util.minecraft.blockstate.visualduplicates;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.AbstractBedBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.ShelfBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.WallSide;
import org.jspecify.annotations.Nullable;
import spout.gamecontent.datadriven.block.registry.VanillaOnlyBlockRegistry;
import spout.gamecontent.datadriven.block.registry.VanillaOnlyBlockStateRegistry;
import spout.util.datastructure.UnionFind;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Provides information about which {@linkplain BlockState block states} are visual duplicates of each other.
 *
 * <p>
 * Two block states are visual duplicates of each other if one block state can be
 * substituted with the other in packets sent to the client without any game-breaking effects
 * (such as that do not allow a state to be observed by the player visually,
 * or that do not allow a client to perform certain interactions with a block
 * due to a mismatch in expectations in what is possible).
 * In some cases, slight, but acceptable, temporary visual glitches may still occur (such as the client forcibly
 * updating the note block state based on the block below it) for 1 tick.
 * </p>
 *
 * <p>
 * Being a visual duplicates is a symmetric relation:
 * if A is a visual duplicate of B then B is a visual duplicate of A.
 * </p>
 *
 * <p>
 * Some blocks are visually similar but still not visually the same,
 * such as waterlogged double slabs (which have water particles, unlike their unwaterlogged counterpart).
 * Those do not count as visual duplicates.
 * </p>
 */
public final class VisualDuplicates {

    private VisualDuplicates() {
        throw new UnsupportedOperationException();
    }

    public static @Nullable VisualDuplicateGroup getVisualDuplicates(BlockState blockState) {
        return getVisualDuplicates(blockState.indexInVanillaOnlyBlockStateRegistry);
    }

    public static boolean hasVisualDuplicates(BlockState blockState) {
        return hasVisualDuplicates(blockState.indexInVanillaOnlyBlockStateRegistry);
    }

    public static @Nullable VisualDuplicateGroup getVisualDuplicates(int blockStateId) {
        initializeIfNecessary();
        return map[blockStateId];
    }

    public static boolean hasVisualDuplicates(int blockStateId) {
        return getVisualDuplicates(blockStateId) != null;
    }

    /**
     * The internal map used to lookup duplicates,
     * or null if not {@linkplain #initializeIfNecessary() initialized} yet.
     *
     * <p>
     * Keys in the map are the {@link BlockState#indexInVanillaOnlyBlockStateRegistry}
     * of block states. Not every key maps to a non-null value. If null, it means there are no visual duplicates
     * for that {@link BlockState}.
     * </p>
     */
    private static @Nullable VisualDuplicateGroup @Nullable [] map;

    private static void initializeIfNecessary() {
        if (map != null) return;
        // Create a union find structure
        int size = VanillaOnlyBlockStateRegistry.get().size();
        if (size == 0) {
            throw new IllegalStateException("Called VisualDuplicates while block state registry has not been initialized yet");
        }
        BlockStateUnionFind unionFind = new BlockStateUnionFind(size);
        // Add all the duplicates
        addDuplicates(unionFind);
        // Build the map
        int[] counts = new int[size];
        for (int i = 0; i < size; i++) {
            counts[unionFind.find(i)]++;
        }
        map = new VisualDuplicateGroup[size];
        for (int i = 0; i < size; i++) {
            if (counts[i] > 1) {
                map[i] = new VisualDuplicateGroup(counts[i]);
            }
        }
        int[] groupEndIndex = new int[size];
        for (int i = 0; i < size; i++) {
            int group = unionFind.find(i);
            if (counts[group] > 1) {
                map[group].stateIndices[groupEndIndex[group]++] = i;
                map[i] = map[group];
            }
        }
        for (int i = 0; i < size; i++) {
            if (map[i] != null) {
                map[i].sort();
            }
        }
    }

    /**
     * Extension of {@link UnionFind} with useful methods for merging {@link BlockState}s.
     */
    private static class BlockStateUnionFind extends UnionFind {

        public BlockStateUnionFind(int size) {
            super(size);
        }

        public void merge(BlockState state1, BlockState state2) {
            this.merge(state1.indexInVanillaOnlyBlockStateRegistry, state2.indexInVanillaOnlyBlockStateRegistry);
        }

        public void mergeDefaults(Block block1, Block block2) {
            this.merge(block1.defaultBlockState(), block2.defaultBlockState());
        }

        public void mergeStateByState(Block block1, Block block2) {
            for (BlockState state1 : block1.getStateDefinition().getPossibleStates()) {
                BlockState state2 = block2.defaultBlockState();
                for (Property<?> property : block1.getStateDefinition().getProperties()) {
                    state2 = state2.setValue((Property) property, state1.getValue(property));
                }
                this.merge(state1, state2);
            }
        }

        public void mergeAll(List<BlockState> states) {
            for (int i = 1; i < states.size(); i++) {
                this.merge(states.getFirst(), states.get(i));
            }
        }

        public void mergeAll(BlockState... states) {
            this.mergeAll(Arrays.asList(states));
        }

        public void mergeAllStatesOf(Block block) {
            this.mergeAll(block.getStateDefinition().getPossibleStates());
        }

        public void mergeAllStatesWithOtherValuesOfProperties(List<BlockState> states, Property<?>... properties) {
            for (BlockState state : states) {
                BlockState stateNormalForm = state;
                for (Property<?> property : properties) {
                    stateNormalForm = stateNormalForm.setValue((Property) property, (Comparable) property.getPossibleValues().getFirst());
                }
                this.merge(stateNormalForm, state);
            }
        }

        public void mergeAllStatesWithOtherValuesOfProperties(Block block, Property<?>... properties) {
            this.mergeAllStatesWithOtherValuesOfProperties(block.getStateDefinition().getPossibleStates(), properties);
        }

        public void mergeDoubleSlabStateToFullBlockState(Block slab, BlockState fullBlockState) {
            this.merge(slab.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.DOUBLE), fullBlockState);
        }

        public void mergeDoubleSlabStateToFullBlockDefaultState(Block slab, Block fullBlock) {
            this.mergeDoubleSlabStateToFullBlockState(slab, fullBlock.defaultBlockState());
        }

    }

    private static Stream<Block> getBlocksOfType(Class<? extends Block> type) {
        return StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false).filter(type::isInstance);
    }

    private static <T extends Comparable<T>> List<BlockState> getStatesWhere(Block block, Property<T> property, Object value) {
        return block.getStateDefinition().getPossibleStates().stream().filter(state -> state.getValue(property).equals(value)).toList();
    }

    private static <T extends Comparable<T>> List<BlockState> getStatesWhereFulfills(Block block, Property<T> property, Predicate<Object> predicate) {
        return block.getStateDefinition().getPossibleStates().stream().filter(state -> predicate.test(state.getValue(property))).toList();
    }

    private static void addDuplicates(BlockStateUnionFind unionFind) {
        // Air
        unionFind.mergeAll(Blocks.AIR.defaultBlockState(), Blocks.CAVE_AIR.defaultBlockState(), Blocks.VOID_AIR.defaultBlockState());
        // Bamboo
        unionFind.mergeAllStatesWithOtherValuesOfProperties(Blocks.BAMBOO, BlockStateProperties.STAGE);
        // Bed and straw bed
        getBlocksOfType(AbstractBedBlock.class).forEach(block -> unionFind.mergeAllStatesWithOtherValuesOfProperties(block, BlockStateProperties.OCCUPIED));
        // Beehive and bee nest
        for (Block block : new Block[]{Blocks.BEEHIVE, Blocks.BEE_NEST}) {
            unionFind.mergeAllStatesWithOtherValuesOfProperties(getStatesWhereFulfills(block, BlockStateProperties.LEVEL_HONEY, honeyLevel -> !honeyLevel.equals(5)), BlockStateProperties.LEVEL_HONEY);
        }
        // Bell
        unionFind.mergeAllStatesWithOtherValuesOfProperties(Blocks.BELL, BlockStateProperties.POWERED);
        // Cave vines, kelp, twisting vines and weeping vines
        for (Block block : new Block[]{Blocks.CAVE_VINES, Blocks.KELP, Blocks.TWISTING_VINES, Blocks.WEEPING_VINES}) {
            unionFind.mergeAllStatesWithOtherValuesOfProperties(block, BlockStateProperties.AGE_25);
        }
        // Chorus flower
        unionFind.mergeAllStatesWithOtherValuesOfProperties(getStatesWhereFulfills(Blocks.CHORUS_FLOWER, BlockStateProperties.AGE_5, age -> !age.equals(5)), BlockStateProperties.AGE_5);
        // Copper
        Stream.of(
            Blocks.CHISELED_COPPER,
            Blocks.COPPER_BARS,
            Blocks.COPPER_BLOCK,
            Blocks.COPPER_BULB,
            Blocks.COPPER_CHAIN,
            Blocks.COPPER_CHEST,
            Blocks.COPPER_DOOR,
            Blocks.COPPER_GOLEM_STATUE,
            Blocks.COPPER_GRATE,
            Blocks.COPPER_LANTERN,
            Blocks.COPPER_TRAPDOOR,
            Blocks.CUT_COPPER,
            Blocks.CUT_COPPER_SLAB,
            Blocks.CUT_COPPER_STAIRS,
            Blocks.LIGHTNING_ROD
        ).forEach(collection -> {
            Arrays.stream(WeatheringCopper.WeatherState.values()).forEach(weatherState -> {
                unionFind.mergeStateByState(collection.waxed().pick(weatherState), collection.weathering().pick(weatherState));
            });
        });
        // Creaking heart
        unionFind.mergeAllStatesWithOtherValuesOfProperties(Blocks.CREAKING_HEART, BlockStateProperties.NATURAL);
        // Crop
        for (Block block : new Block[]{Blocks.CARROTS, Blocks.POTATOES}) {
            unionFind.merge(block.defaultBlockState().setValue(BlockStateProperties.AGE_7, 1), block.defaultBlockState().setValue(BlockStateProperties.AGE_7, 0));
            unionFind.merge(block.defaultBlockState().setValue(BlockStateProperties.AGE_7, 3), block.defaultBlockState().setValue(BlockStateProperties.AGE_7, 2));
            unionFind.merge(block.defaultBlockState().setValue(BlockStateProperties.AGE_7, 5), block.defaultBlockState().setValue(BlockStateProperties.AGE_7, 4));
            unionFind.merge(block.defaultBlockState().setValue(BlockStateProperties.AGE_7, 6), block.defaultBlockState().setValue(BlockStateProperties.AGE_7, 4));
        }
        unionFind.merge(Blocks.NETHER_WART.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), Blocks.NETHER_WART.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1));
        // Daylight detector
        unionFind.mergeAllStatesWithOtherValuesOfProperties(Blocks.DAYLIGHT_DETECTOR, BlockStateProperties.POWER);
        // Dispenser and dropper
        for (Block block : new Block[]{Blocks.DISPENSER, Blocks.DROPPER}) {
            unionFind.mergeAllStatesWithOtherValuesOfProperties(block, BlockStateProperties.TRIGGERED);
        }
        // Door
        getBlocksOfType(DoorBlock.class).forEach(block -> unionFind.mergeAllStatesWithOtherValuesOfProperties(block, BlockStateProperties.POWERED));
        // TODO there are some more duplicate door states, due to the way open doors in one direction can be the same as closed doors in another direction
        // Double slab
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.ACACIA_SLAB, Blocks.ACACIA_PLANKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.ANDESITE_SLAB, Blocks.ANDESITE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.BAMBOO_MOSAIC_SLAB, Blocks.BAMBOO_MOSAIC);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.BAMBOO_SLAB, Blocks.BAMBOO_BLOCK);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.BIRCH_SLAB, Blocks.BIRCH_PLANKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.BLACKSTONE_SLAB, Blocks.BLACKSTONE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.BRICK_SLAB, Blocks.BRICKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.CHERRY_SLAB, Blocks.CHERRY_PLANKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.COBBLED_DEEPSLATE_SLAB, Blocks.COBBLED_DEEPSLATE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.COBBLESTONE_SLAB, Blocks.COBBLESTONE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.CRIMSON_SLAB, Blocks.CRIMSON_PLANKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.CUT_RED_SANDSTONE_SLAB, Blocks.CUT_RED_SANDSTONE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.CUT_SANDSTONE_SLAB, Blocks.CUT_SANDSTONE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.DARK_OAK_SLAB, Blocks.DARK_OAK_PLANKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.DARK_PRISMARINE_SLAB, Blocks.DARK_PRISMARINE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.DEEPSLATE_BRICK_SLAB, Blocks.DEEPSLATE_BRICKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.DEEPSLATE_TILE_SLAB, Blocks.DEEPSLATE_TILES);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.DIORITE_SLAB, Blocks.DIORITE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.END_STONE_BRICK_SLAB, Blocks.END_STONE_BRICKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.GRANITE_SLAB, Blocks.GRANITE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.JUNGLE_SLAB, Blocks.JUNGLE_PLANKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.MANGROVE_SLAB, Blocks.MANGROVE_PLANKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.MOSSY_STONE_BRICKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.MUD_BRICK_SLAB, Blocks.MUD_BRICKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.NETHER_BRICK_SLAB, Blocks.NETHER_BRICKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.OAK_SLAB, Blocks.OAK_PLANKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.PALE_OAK_SLAB, Blocks.PALE_OAK_PLANKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.PETRIFIED_OAK_SLAB, Blocks.OAK_PLANKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.POLISHED_ANDESITE_SLAB, Blocks.POLISHED_ANDESITE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, Blocks.POLISHED_BLACKSTONE_BRICKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.POLISHED_BLACKSTONE_SLAB, Blocks.POLISHED_BLACKSTONE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.POLISHED_DEEPSLATE_SLAB, Blocks.POLISHED_DEEPSLATE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.POLISHED_DIORITE_SLAB, Blocks.POLISHED_DIORITE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.POLISHED_GRANITE_SLAB, Blocks.POLISHED_GRANITE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.POLISHED_TUFF_SLAB, Blocks.POLISHED_TUFF);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.PRISMARINE_BRICK_SLAB, Blocks.PRISMARINE_BRICKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.PRISMARINE_SLAB, Blocks.PRISMARINE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.PURPUR_SLAB, Blocks.PURPUR_BLOCK);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.QUARTZ_SLAB, Blocks.QUARTZ_BLOCK);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.RED_NETHER_BRICK_SLAB, Blocks.RED_NETHER_BRICKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.RED_SANDSTONE_SLAB, Blocks.RED_SANDSTONE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.RESIN_BRICK_SLAB, Blocks.RESIN_BRICKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.SANDSTONE_SLAB, Blocks.SANDSTONE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.SMOOTH_QUARTZ_SLAB, Blocks.SMOOTH_QUARTZ);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.SMOOTH_RED_SANDSTONE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_SANDSTONE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.SPRUCE_SLAB, Blocks.SPRUCE_PLANKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.STONE_BRICK_SLAB, Blocks.STONE_BRICKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.STONE_SLAB, Blocks.STONE);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.TUFF_BRICK_SLAB, Blocks.TUFF_BRICKS);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.TUFF_SLAB, Blocks.TUFF);
        unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.WARPED_SLAB, Blocks.WARPED_PLANKS);
        // Copper double slab
        Arrays.stream(WeatheringCopper.WeatherState.values()).forEach(weatherState -> {
            unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.CUT_COPPER_SLAB.weathering().pick(weatherState), Blocks.CUT_COPPER.weathering().pick(weatherState));
            unionFind.mergeDoubleSlabStateToFullBlockDefaultState(Blocks.CUT_COPPER_SLAB.waxed().pick(weatherState), Blocks.CUT_COPPER.weathering().pick(weatherState));
        });
        // End portal frame
        getStatesWhere(Blocks.END_PORTAL_FRAME, BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH).forEach(state -> unionFind.merge(state, state.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)));
        getStatesWhere(Blocks.END_PORTAL_FRAME, BlockStateProperties.HORIZONTAL_FACING, Direction.WEST).forEach(state -> unionFind.merge(state, state.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)));
        // Every state is the same
        unionFind.mergeAllStatesOf(Blocks.CACTUS);
        unionFind.mergeAllStatesOf(Blocks.JUKEBOX);
        unionFind.mergeAllStatesOf(Blocks.NOTE_BLOCK);
        unionFind.mergeAllStatesOf(Blocks.POTENT_SULFUR);
        unionFind.mergeAllStatesOf(Blocks.SUGAR_CANE);
        unionFind.mergeAllStatesOf(Blocks.TARGET);
        unionFind.mergeAllStatesOf(Blocks.TNT);
        // Farmland
        unionFind.mergeAll(getStatesWhereFulfills(Blocks.FARMLAND, BlockStateProperties.MOISTURE, moisture -> !moisture.equals(7)));
        // Fence gate
        getBlocksOfType(FenceGateBlock.class).forEach(block -> {
            for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                BlockState normalFormState = state;
                normalFormState = normalFormState.setValue(BlockStateProperties.POWERED, false);
                if (!state.getValue(BlockStateProperties.OPEN)) {
                    if (state.getValue(BlockStateProperties.HORIZONTAL_FACING) == Direction.SOUTH) {
                        normalFormState = normalFormState.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH);
                    } else if (state.getValue(BlockStateProperties.HORIZONTAL_FACING) == Direction.WEST) {
                        normalFormState = normalFormState.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST);
                    }
                }
                unionFind.merge(normalFormState, state);
            }
        });
        // Hopper
        unionFind.mergeAllStatesWithOtherValuesOfProperties(Blocks.HOPPER, BlockStateProperties.ENABLED);
        // Growing plant head
        getBlocksOfType(GrowingPlantHeadBlock.class).forEach(block -> unionFind.mergeAllStatesWithOtherValuesOfProperties(block, BlockStateProperties.AGE_25));
        // Infested
        unionFind.mergeDefaults(Blocks.INFESTED_CHISELED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS);
        unionFind.mergeDefaults(Blocks.INFESTED_COBBLESTONE, Blocks.COBBLESTONE);
        unionFind.mergeDefaults(Blocks.INFESTED_CRACKED_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
        unionFind.mergeStateByState(Blocks.INFESTED_DEEPSLATE, Blocks.DEEPSLATE);
        unionFind.mergeDefaults(Blocks.INFESTED_MOSSY_STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS);
        unionFind.mergeDefaults(Blocks.INFESTED_STONE, Blocks.STONE);
        unionFind.mergeDefaults(Blocks.INFESTED_STONE_BRICKS, Blocks.STONE_BRICKS);
        // Leaves
        getBlocksOfType(LeavesBlock.class).forEach(block -> unionFind.mergeAllStatesWithOtherValuesOfProperties(block, BlockStateProperties.DISTANCE, BlockStateProperties.PERSISTENT));
        // Lectern
        unionFind.mergeAllStatesWithOtherValuesOfProperties(Blocks.LECTERN, BlockStateProperties.POWERED);
        // Mangrove propagule
        unionFind.mergeAllStatesWithOtherValuesOfProperties(getStatesWhere(Blocks.MANGROVE_PROPAGULE, BlockStateProperties.HANGING, true), BlockStateProperties.STAGE);
        unionFind.mergeAllStatesWithOtherValuesOfProperties(getStatesWhere(Blocks.MANGROVE_PROPAGULE, BlockStateProperties.HANGING, false), BlockStateProperties.STAGE, BlockStateProperties.HANGING);
        // Petrified oak slab
        unionFind.mergeStateByState(Blocks.PETRIFIED_OAK_SLAB, Blocks.OAK_SLAB);
        // Sapling
        getBlocksOfType(SaplingBlock.class).filter(block -> !(block instanceof MangrovePropaguleBlock)).forEach(block -> unionFind.mergeAllStatesWithOtherValuesOfProperties(block, BlockStateProperties.STAGE));
        // Scaffolding
        unionFind.mergeAllStatesWithOtherValuesOfProperties(Blocks.SCAFFOLDING, BlockStateProperties.STABILITY_DISTANCE);
        // Sculk sensor
        for (Block block : new Block[]{Blocks.CALIBRATED_SCULK_SENSOR, Blocks.SCULK_SENSOR}) {
            unionFind.mergeAllStatesWithOtherValuesOfProperties(block, BlockStateProperties.POWER);
        }
        // Shelf
        getBlocksOfType(ShelfBlock.class).forEach(block -> unionFind.mergeAllStatesWithOtherValuesOfProperties(getStatesWhere(block, BlockStateProperties.POWERED, false), BlockStateProperties.SIDE_CHAIN_PART));
        // Small dripleaf
        unionFind.mergeAllStatesWithOtherValuesOfProperties(getStatesWhere(Blocks.SMALL_DRIPLEAF, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER), BlockStateProperties.HORIZONTAL_FACING);
        // Snowy mycelium and podzol
        for (Block block : new Block[]{Blocks.MYCELIUM, Blocks.PODZOL}) {
            unionFind.merge(block.defaultBlockState().setValue(BlockStateProperties.SNOWY, true), Blocks.GRASS_BLOCK.defaultBlockState().setValue(BlockStateProperties.SNOWY, true));
        }
        // TODO some stair states are duplicates (one direction's left inner state is the same as the other direction's right inner state, and so on)
        // Trapdoor
        getBlocksOfType(TrapDoorBlock.class).forEach(block -> unionFind.mergeAllStatesWithOtherValuesOfProperties(block, BlockStateProperties.POWERED));
        // Tripwire
        unionFind.mergeAllStatesWithOtherValuesOfProperties(Blocks.TRIPWIRE, BlockStateProperties.ATTACHED, BlockStateProperties.POWERED); // TODO Potentially not accurate anymore for 26.1
        // Tripwire hook
        unionFind.mergeAllStatesWithOtherValuesOfProperties(getStatesWhere(Blocks.TRIPWIRE_HOOK, BlockStateProperties.ATTACHED, false), BlockStateProperties.POWERED);
        // Wall
        getBlocksOfType(WallBlock.class).forEach(block -> {
            BlockState airState = block.defaultBlockState()
                .setValue(BlockStateProperties.UP, false)
                .setValue(BlockStateProperties.EAST_WALL, WallSide.NONE)
                .setValue(BlockStateProperties.NORTH_WALL, WallSide.NONE)
                .setValue(BlockStateProperties.SOUTH_WALL, WallSide.NONE)
                .setValue(BlockStateProperties.WEST_WALL, WallSide.NONE)
                .setValue(BlockStateProperties.WATERLOGGED, false);
            unionFind.merge(Blocks.AIR.defaultBlockState(), airState);
            unionFind.merge(Blocks.WATER.defaultBlockState(), airState.setValue(BlockStateProperties.WATERLOGGED, true));
        });
        // Weighted pressure plate
        for (Block block : new Block[]{Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE}) {
            unionFind.mergeAll(getStatesWhereFulfills(block, BlockStateProperties.POWER, power -> !power.equals(0)));
        }
    }

}
