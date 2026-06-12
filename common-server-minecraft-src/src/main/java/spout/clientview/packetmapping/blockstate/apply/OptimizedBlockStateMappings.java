package spout.clientview.packetmapping.blockstate.apply;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMapping;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMappingRegistry;
import spout.util.mapping.handle.DirectMappingStep;
import spout.util.mapping.handle.MappingStep;
import spout.util.minecraft.registry.SpoutRegistryHookEvents;
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

    public static final class RegistryFreezeListener implements SpoutRegistryHookEvents.Listener<BlockStateMapping> {

        @Override
        public Iterable<Pair<ResourceKey<Registry<BlockStateMapping>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
            return List.of(Pair.of(BlockStateMappingRegistry.BLOCK_STATE_MAPPING, SpoutRegistryHookEvents.EventType.POST_FREEZE));
        }

        @Override
        public void onRegistryHookEvent(SpoutRegistryHookEvents.EventType type, WritableRegistry<BlockStateMapping> registry) {
            build(registry);
        }

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
    private static MappingStep<BlockStateMappingHandle>[][][] chains;

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
    private static @Nullable BlockState[][] direct;

    /**
     * The same as {@link #direct}, but contains the {@link BlockState#indexInBlockStateRegistry},
     * or -1 if the value in {@link #direct} would be null.
     */
    private static int[][] directAsIndicesInRegistry;

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
    public static void build(Registry<BlockStateMapping> registry) {

        // Initialize the arrays
        int awarenessLevelSize = AwarenessLevels.getAll().length;
        chains = new MappingStep[awarenessLevelSize][][];
        direct = new BlockState[awarenessLevelSize][];
        directAsIndicesInRegistry = new int[awarenessLevelSize][];
        int registrySize = Block.BLOCK_STATE_REGISTRY.size();
        for (int i = 0; i < chains.length; i++) {
            chains[i] = new MappingStep[registrySize][];
            direct[i] = new BlockState[registrySize];
            directAsIndicesInRegistry[i] = new int[registrySize];
            Arrays.fill(directAsIndicesInRegistry[i], -1);
        }

        // Invert the mappings from id -> lists of steps to list of steps -> ids, so that we only have one reference per unique list
        Map<List<MappingStep<BlockStateMappingHandle>>, List<IntIntPair>> invertedChainMappings = new HashMap<>();
        Map<IntIntPair, List<BlockStateMapping>> registered = new HashMap<>();
        registry.stream()
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
