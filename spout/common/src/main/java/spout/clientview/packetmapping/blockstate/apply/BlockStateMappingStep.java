package spout.clientview.packetmapping.blockstate.apply;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import spout.util.mapping.handle.MappingStep;

/**
 * A step that can be applied to a {@link BlockStateMappingHandle} as a single operation.
 *
 * <p>
 * This is an extension of {@link MappingStep}.
 * </p>
 */
public sealed interface BlockStateMappingStep extends MappingStep<BlockStateMappingHandle> permits DirectBlockStateMappingStep, FunctionBlockStateMappingStep {

    /**
     * @return Whether this step depends on the coordinates of the block state being mapped.
     */
    default boolean requiresCoordinates() {
        return false;
    }

    static BlockState applyChain(BlockState state, BlockStateMappingsApplicationContext context, BlockStateMappingStep[] chain) {
        return MappingStep.applyChain(new BlockStateMappingHandle(state, context, false), chain);
    }

    static int applyChain(int stateIndexInRegistry, BlockStateMappingsApplicationContext context, BlockStateMappingStep[] chain) {
        return applyChain(Block.BLOCK_STATE_REGISTRY.byId(stateIndexInRegistry), context, chain).indexInBlockStateRegistry;
    }

}
