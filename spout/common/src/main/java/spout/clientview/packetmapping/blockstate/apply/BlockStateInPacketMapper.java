package spout.clientview.packetmapping.blockstate.apply;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import spout.clientview.model.ClientView;
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
    public @Nullable BlockStateMappingsApplicationContext getDefaultActiveContext() {
        @Nullable ClientView clientView = ThreadLocalClientViewLookup.getThreadLocalClientView();
        if (clientView == null) {
            return null;
        }
        return new BlockStateMappingsApplicationContext(clientView);
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

    /**
     * Analogous to {@link #applyWithDefaultActiveContext} but for {@link Block}s.
     */
    public Block applyToBlockWithDefaultActiveContext(Block value) {
        @Nullable BlockStateMappingsApplicationContext activeContext = this.getDefaultActiveContext();
        if (activeContext == null) {
            return value;
        }
        return apply(value.defaultBlockState(), activeContext).getBlock();
    }

    /**
     * Analogous to {@link #wrapBlockCodecWithDefaultActiveContextMapping} but for {@link Block}s.
     */
    public Codec<Block> wrapBlockCodecWithDefaultActiveContextMapping(Codec<Block> codec) {
        return new Codec<>() {

            @Override
            public <T> DataResult<T> encode(final Block i, final DynamicOps<T> dynamicOps, final T t) {
                return codec.encode(BlockStateInPacketMapper.this.applyToBlockWithDefaultActiveContext(i), dynamicOps, t);
            }

            @Override
            public <T> DataResult<Pair<Block, T>> decode(final DynamicOps<T> dynamicOps, final T t) {
                return codec.decode(dynamicOps, t);
            }

        };
    }

}
