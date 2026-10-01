package spout.gamecontent.datadriven.block;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import net.minecraft.core.IdMapper;import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import spout.clientview.clientmod.protocol.ClientModStateHandler;import spout.gamecontent.datadriven.common.registry.temporarymodification.mixin.IdMapperAccessor;
import java.util.List;
import java.util.function.Supplier;

/**
 * A wrapper around {@link Block#BLOCK_STATE_REGISTRY}, that can make temporarily additions.
 */
public final class TemporaryBlockStateRegistryModifier {

    private @Nullable Integer originalNextId = null;

    public void add(List<Pair<ResourceKey<Block>, Supplier<Block>>> blocksToAdd) {
        IdMapper<BlockState> blockStateRegistry = Block.BLOCK_STATE_REGISTRY;
        int addedBlockStatesCount = 0;
        for (var blockToAdd : blocksToAdd) {
            Block block = BuiltInRegistries.BLOCK.get(blockToAdd.first().identifier()).orElseThrow().value();
            for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                state.initCache();
                if (blockStateRegistry.getId(state) == -1) {
                    blockStateRegistry.add(state);
                }
                addedBlockStatesCount++;
            }
        }
        final int finalAddedBlockStatesCount = addedBlockStatesCount;
        ClientModStateHandler.updateLoadedContentSummary(summary -> summary.setBlockStates(finalAddedBlockStatesCount));
    }

    public void remove() {
        if (this.originalNextId == null) {
            return;
        }
        int originalNextId = this.originalNextId;
        this.originalNextId = null;
        IdMapperAccessor<BlockState> accessor =
            (IdMapperAccessor<BlockState>) Block.BLOCK_STATE_REGISTRY;
        int modifiedNextId = accessor.getNextId();
        Reference2IntMap<BlockState> tToId = accessor.getTToId();
        List<BlockState> idToT = accessor.getIdToT();
        for (int i = modifiedNextId - 1; i >= originalNextId; i--) {
            BlockState state = idToT.remove(i);
            tToId.removeInt(state);
        }
        accessor.setNextId(originalNextId);
    }

}
