package spout.clientview.packetmapping.blockstate.apply;

import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.FallbackContextValueInPacketMapper;
import spout.clientview.packetmapping.clientviewlookup.ThreadLocalClientViewLookup;
import spout.util.mapping.handle.MappingStep;
import spout.util.mapping.pipeline.PipelineMapper;

/**
 * Provides the functions to map block states in packets.
 */
public final class BlockStateInPacketMapper implements FallbackContextValueInPacketMapper<BlockState, BlockState, BlockStateMappingsApplicationContext> {

    private static BlockStateInPacketMapper instance;

    public static BlockStateInPacketMapper get() {
        if (instance == null) {
            instance = new BlockStateInPacketMapper();
        }
        return instance;
    }

    @Override
    public BlockStateMappingsApplicationContext getFallbackContext() {
        return new BlockStateMappingsApplicationContext(ThreadLocalClientViewLookup.getThreadLocalClientViewOrFallback());
    }

    @Override
    public BlockState applyWithNonNullContext(BlockState value, BlockStateMappingsApplicationContext context) {
        int awarenessLevelId = context.getClientView().getAwarenessLevel().getId();
        int valueIndexInRegistry = value.indexInBlockStateRegistry;
        // If there is a direct mapping, apply it
        @Nullable BlockState directMapping = OptimizedBlockStateMappings.getDirect(awarenessLevelId, valueIndexInRegistry);
        if (directMapping != null) {
            return directMapping;
        }
        // If there is a mapping chain, apply it
        MappingStep<BlockStateMappingHandle> @Nullable [] chain = OptimizedBlockStateMappings.getChain(awarenessLevelId, valueIndexInRegistry);
        if (chain != null) {
            return PipelineMapper.apply(new BlockStateMappingHandle(value, context, false), chain);
        }
        // No mappings need to be applied
        return value;
    }

    public boolean hasAnyMapping(int awarenessLevelId, int stateIndexInRegistry) {
        return OptimizedBlockStateMappings.getDirectIndex(awarenessLevelId, stateIndexInRegistry) != -1 || OptimizedBlockStateMappings.getChain(awarenessLevelId, stateIndexInRegistry) != null;
    }

    public boolean hasAnyMapping(int awarenessLevelId, BlockState state) {
        return this.hasAnyMapping(awarenessLevelId, state.indexInBlockStateRegistry);
    }

}
