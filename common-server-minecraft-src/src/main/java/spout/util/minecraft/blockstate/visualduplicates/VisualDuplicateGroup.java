package spout.util.minecraft.blockstate.visualduplicates;

import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.SlabType;
import org.jspecify.annotations.Nullable;
import spout.gamecontent.datadriven.block.VanillaOnlyBlockStateRegistry;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * A group of visual duplicates.
 */
public final class VisualDuplicateGroup {

    /**
     * The {@link BlockState#indexInVanillaOnlyBlockStateRegistry}
     * of every block state in this group.
     */
    public final int[] stateIndices;

    /**
     * The cached return value of {@link #getStates()},
     * or null if not initialized yet.
     */
    private @Nullable List<BlockState> states;

    VisualDuplicateGroup(int size) {
        this.stateIndices = new int[size];
    }

    /**
     * @return The {@linkplain BlockState block states} in this group.
     */
    public List<BlockState> getStates() {
        if (this.states == null) {
            this.states = Arrays.stream(this.stateIndices).mapToObj(index -> VanillaOnlyBlockStateRegistry.get().byId(index)).toList();
        }
        return this.states;
    }

    public boolean contains(int id) {
        for (int index : this.stateIndices) {
            if (index == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return A unique identifier for this group:
     * which is simply the first element of {@link #stateIndices}.
     */
    public int getId() {
        return this.stateIndices[0];
    }

    /**
     * Sorts {@link #stateIndices} from most to least desirable to be used as a target
     * for a mapping to a visual duplicate. Typically, these are the blocks that behave
     * most like an average block, or are very common.
     */
    void sort() {
        BlockState[] states = new BlockState[this.stateIndices.length];
        for (int i = 0; i < states.length; i++) {
            states[i] = VanillaOnlyBlockStateRegistry.get().byId(this.stateIndices[i]);
        }
        Arrays.sort(states, STATE_COMPARATOR);
        for (int i = 0; i < states.length; i++) {
            this.stateIndices[i] = states[i].indexInVanillaOnlyBlockStateRegistry;
        }
    }

    private static @Nullable IntSet moreCommonBlocks = null;

    public static IntSet getMoreCommonBlocks() {
        if (moreCommonBlocks == null) {
            moreCommonBlocks = IntSet.of(Set.of(
                // Air
                Blocks.AIR,
                // Copper
                Blocks.WAXED_CHISELED_COPPER,
                Blocks.WAXED_COPPER_BLOCK,
                Blocks.WAXED_COPPER_BULB,
                Blocks.WAXED_COPPER_CHEST,
                Blocks.WAXED_COPPER_DOOR,
                Blocks.WAXED_COPPER_GOLEM_STATUE,
                Blocks.WAXED_COPPER_GRATE,
                Blocks.WAXED_COPPER_TRAPDOOR,
                Blocks.WAXED_CUT_COPPER,
                Blocks.WAXED_CUT_COPPER_SLAB,
                Blocks.WAXED_CUT_COPPER_STAIRS,
                Blocks.WAXED_EXPOSED_CHISELED_COPPER,
                Blocks.WAXED_EXPOSED_COPPER,
                Blocks.WAXED_EXPOSED_COPPER_BULB,
                Blocks.WAXED_EXPOSED_COPPER_CHEST,
                Blocks.WAXED_EXPOSED_COPPER_DOOR,
                Blocks.WAXED_EXPOSED_COPPER_GOLEM_STATUE,
                Blocks.WAXED_EXPOSED_COPPER_GRATE,
                Blocks.WAXED_EXPOSED_COPPER_TRAPDOOR,
                Blocks.WAXED_EXPOSED_CUT_COPPER,
                Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB,
                Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS,
                Blocks.WAXED_EXPOSED_LIGHTNING_ROD,
                Blocks.WAXED_LIGHTNING_ROD,
                Blocks.WAXED_OXIDIZED_CHISELED_COPPER,
                Blocks.WAXED_OXIDIZED_COPPER,
                Blocks.WAXED_OXIDIZED_COPPER_BULB,
                Blocks.WAXED_OXIDIZED_COPPER_CHEST,
                Blocks.WAXED_OXIDIZED_COPPER_DOOR,
                Blocks.WAXED_OXIDIZED_COPPER_GOLEM_STATUE,
                Blocks.WAXED_OXIDIZED_COPPER_GRATE,
                Blocks.WAXED_OXIDIZED_COPPER_TRAPDOOR,
                Blocks.WAXED_OXIDIZED_CUT_COPPER,
                Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB,
                Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS,
                Blocks.WAXED_OXIDIZED_LIGHTNING_ROD,
                Blocks.WAXED_WEATHERED_CHISELED_COPPER,
                Blocks.WAXED_WEATHERED_COPPER,
                Blocks.WAXED_WEATHERED_COPPER_BULB,
                Blocks.WAXED_WEATHERED_COPPER_CHEST,
                Blocks.WAXED_WEATHERED_COPPER_DOOR,
                Blocks.WAXED_WEATHERED_COPPER_GOLEM_STATUE,
                Blocks.WAXED_WEATHERED_COPPER_GRATE,
                Blocks.WAXED_WEATHERED_COPPER_TRAPDOOR,
                Blocks.WAXED_WEATHERED_CUT_COPPER,
                Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB,
                Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS,
                Blocks.WAXED_WEATHERED_LIGHTNING_ROD,
                // Infested block
                Blocks.CHISELED_STONE_BRICKS,
                Blocks.COBBLESTONE,
                Blocks.CRACKED_STONE_BRICKS,
                Blocks.DEEPSLATE,
                Blocks.MOSSY_STONE_BRICKS,
                Blocks.STONE,
                Blocks.STONE_BRICKS,
                // Petrified oak slab
                Blocks.OAK_SLAB,
                // Snowy mycelium and podzol
                Blocks.GRASS_BLOCK
            ).stream().mapToInt(block -> block.indexInVanillaOnlyBlockRegistry).toArray());
        }
        return moreCommonBlocks;
    }

    private static final Comparator<BlockState> NOTE_BLOCK_STATE_COMPARATOR = Comparator
        // Don't choose the default state
        .<BlockState, Boolean>comparing(state -> state == state.getBlock().defaultBlockState())
        // Prefer unpowered states
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.POWERED, false))
        // Prefer common note block instruments
        .thenComparingInt(state -> {
            NoteBlockInstrument instrument = state.getValue(BlockStateProperties.NOTEBLOCK_INSTRUMENT);
            if (instrument == NoteBlockInstrument.HARP) {
                return -2;
            } else if (instrument == NoteBlockInstrument.BASS) {
                return -1;
            }
            return instrument.ordinal();
        })
        // Prefer note block default tone
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.NOTE, 0));

    private static final Comparator<BlockState> NON_NOTE_BLOCK_STATE_COMPARATOR = Comparator
        // Choose regular blocks over blocks with properties
        .<BlockState, Boolean>comparing(state -> !state.getBlock().getStateDefinition().getProperties().isEmpty())
        // Choose anything over a double slab
        .thenComparing(state -> (boolean) state.getOptionalValue(BlockStateProperties.SLAB_TYPE).map(value -> value == SlabType.DOUBLE).orElse(false))
        // Don't choose the default state
        .thenComparing(state -> state == state.getBlock().defaultBlockState())
        // Never prefer blocks that are more common than a visual duplicate of themselves
        .thenComparing(state -> getMoreCommonBlocks().contains(state.getBlock().indexInVanillaOnlyBlockRegistry))
        // Prefer unpowered states
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.POWERED, false))
        // Prefer non-waterlogged states
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.WATERLOGGED, false))
        // Prefer inactive states
        .thenComparingInt(state -> state.getValueOrElse(BlockStateProperties.POWER, 0))
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.TRIGGERED, false))
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.CAN_SUMMON, false))
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.ENABLED, false))
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.HAS_BOOK, false))
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.LIT, false))
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.OCCUPIED, false))
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.SHRIEKING, false))
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.SIGNAL_FIRE, false))
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.SNOWY, false))
        // Prefer persistent leaves
        .thenComparing(state -> !state.getValueOrElse(BlockStateProperties.PERSISTENT, true))
        // Prefer stable TNT
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.UNSTABLE, false))
        // Prefer low age
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.AGE_1, 0))
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.AGE_2, 0))
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.AGE_3, 0))
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.AGE_4, 0))
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.AGE_5, 0))
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.AGE_7, 0))
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.AGE_15, 0))
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.AGE_25, 0))
        // Prefer honey (or not applicable) over no honey
        .thenComparing(state -> state.getValueOrElse(BlockStateProperties.LEVEL_HONEY, 1) == 0);

    public static final Comparator<BlockState> STATE_COMPARATOR = (state1, state2) -> {
        if (state1.getBlock() == Blocks.NOTE_BLOCK && state2.getBlock() == Blocks.NOTE_BLOCK) {
            // Fast path for performance due to how many duplicate note block states there are
            return NOTE_BLOCK_STATE_COMPARATOR.compare(state1, state2);
        }
        return NON_NOTE_BLOCK_STATE_COMPARATOR.compare(state1, state2);
    };

    /**
     * Compares 2 {@link Block}s by their first state.
     */
    public static final Comparator<Block> BLOCK_COMPARATOR = Comparator.comparing(block -> block.getStateDefinition().getPossibleStates().getFirst(), STATE_COMPARATOR);

}
