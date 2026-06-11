package spout.clientview.packetmapping.blockstate.apply;

import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMapping;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMappingRegistry;
import spout.server.paper.impl.moredatadriven.minecraft.BlockStateRegistry;
import spout.util.mapping.handle.DirectMappingStep;
import spout.util.mapping.handle.MappingStep;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds all block state mappings
 * in an optimized data structure.
 */
public final class OptimizedBlockStateMappings {

    private OptimizedBlockStateMappings() {
        throw new UnsupportedOperationException();
    }

    /**
     * The registered mappings that must be performed in chains:
     * this includes only the mappings for {@link BlockState}s for which there was
     * at least 1 non-{@linkplain MappingStep#isDirect() direct} step.
     *
     * <p>
     * The mappings are organized in an array where {@link AwarenessLevel#getId()}
     * is the index, and then in an array where {@link BlockState#indexInBlockStateRegistry} is the index.
     * The lowest-level array may be null, but will never be empty.
     * </p>
     */
    private static final MappingStep<BlockStateMappingHandle>[][][] chains = new MappingStep[AwarenessLevels.getAll().length][][];

    /**
     * The registered mappings that can be applied directly:
     * this includes only the mappings for {@link BlockState}s for which there were only
     * {@linkplain MappingStep#isDirect() direct} steps,
     * meaning the mapping always returns the same value.
     *
     * <p>
     * The mappings are organized in an array where {@link AwarenessLevel#getId()}
     * is the index, and then in an array where {@link BlockState#indexInBlockStateRegistry} is the index.
     * The lowest-level value may be null.
     * </p>
     */
    private static final @Nullable BlockState[][] direct = new BlockState[chains.length][];

    /**
     * The same as {@link #direct}, but contains the {@link BlockState#indexInBlockStateRegistry},
     * or -1 if the value in {@link #direct} would be null.
     */
    private static final int[][] directAsIndicesInRegistry = new int[chains.length][];

    public static @Nullable BlockState getDirect(int awarenessLevelId, int stateIndexInRegistry) {
        return direct[awarenessLevelId][stateIndexInRegistry];
    }

    public static int getDirectIndex(int awarenessLevelId, int stateIndexInRegistry) {
        return directAsIndicesInRegistry[awarenessLevelId][stateIndexInRegistry];
    }

    public static MappingStep<BlockStateMappingHandle> @Nullable [] getChain(int awarenessLevelId, int stateIndexInRegistry) {
        return chains[awarenessLevelId][stateIndexInRegistry];
    }

    /**
     * Fills this class from the mappings in the corresponding registry.
     */
    public static void build() {

        // Initialize the steps
        int registrySize = BlockStateRegistry.get().size();
        for (int i = 0; i < chains.length; i++) {
            chains[i] = new MappingStep[registrySize][];
            direct[i] = new BlockState[registrySize];
            directAsIndicesInRegistry[i] = new int[registrySize];
            Arrays.fill(directAsIndicesInRegistry[i], -1);
        }

        // Invert the mappings from id -> lists of steps to list of steps -> ids, so that we only have one reference per unique list
        Map<List<MappingStep<BlockStateMappingHandle>>, List<IntIntPair>> invertedChainMappings = new HashMap<>();
        Map<IntIntPair, List<BlockStateMapping>> registered = new HashMap<>();
        MinecraftServer.getServer().registryAccess().lookupOrThrow(BlockStateMappingRegistry.BLOCK_STATE_MAPPING).stream()
            .forEach(mapping -> {
                for (AwarenessLevel awarenessLevel : mapping.awarenessLevels()) {
                    for (BlockState fromState : mapping.targets()) {
                        registered.computeIfAbsent(IntIntPair.of(awarenessLevel.getId(), fromState.indexInBlockStateRegistry), _ -> new ArrayList<>(1)).add(mapping);
                    }
                }
            });
        registered.forEach((key, mappings) -> {
            // Do a check for if the list contains any function step
            boolean containsFunction = false;
            for (BlockStateMapping mapping : mappings) {
                if (!mapping.operation().isDirect()) {
                    containsFunction = true;
                    break;
                }
            }
            if (containsFunction) {
                // If there is a function step, add the list of steps as a chain
                // But first we can simplify it a bit by keeping only the last simple step in any contiguous subsequence of simple mappings
                List<MappingStep<BlockStateMappingHandle>> optimizedMappings = new ArrayList<>(mappings.size());
                for (int i = 0; i < mappings.size(); i++) {
                    MappingStep<BlockStateMappingHandle> mapping = mappings.get(i).operation();
                    if (i == mappings.size() - 1 || !mapping.isDirect() || !mappings.get(i + 1).operation().isDirect()) {
                        optimizedMappings.add(mapping);
                    }
                }
                invertedChainMappings.computeIfAbsent(optimizedMappings, $ -> new ArrayList<>()).add(key);
            } else {
                // If there is no complex mapping, add this to the direct mappings
                BlockState to = ((DirectMappingStep<BlockState, BlockStateMappingHandle>) mappings.get(mappings.size() - 1).operation()).to();
                direct[key.firstInt()][key.secondInt()] = to;
                directAsIndicesInRegistry[key.firstInt()][key.secondInt()] = to.indexInBlockStateRegistry;
            }
        });

        // Invert back
        for (Map.Entry<List<MappingStep<BlockStateMappingHandle>>, List<IntIntPair>> entry : invertedChainMappings.entrySet()) {
            for (IntIntPair target : entry.getValue()) {
                chains[target.firstInt()][target.secondInt()] = entry.getKey().toArray(MappingStep[]::new);
            }
        }

    }

}
