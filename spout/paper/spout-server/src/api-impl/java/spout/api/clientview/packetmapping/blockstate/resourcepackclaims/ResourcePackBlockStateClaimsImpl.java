package spout.api.clientview.packetmapping.blockstate.resourcepackclaims;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jspecify.annotations.Nullable;
import java.util.List;
import java.util.function.Consumer;

/**
 * The implementation for {@link ResourcePackBlockStateClaims}.
 */
public final class ResourcePackBlockStateClaimsImpl implements ResourcePackBlockStateClaims {

    public static ResourcePackBlockStateClaimsImpl get() {
        return (ResourcePackBlockStateClaimsImpl) ResourcePackBlockStateClaims.get();
    }

    @Override
    public void claim(BlockData state, ClaimRequestPriority priority, @Nullable BooleanConsumer resultConsumer, @Nullable Consumer<BlockData> visualDuplicateConsumer) {
        spout.clientview.packetmapping.blockstate.resourcepackclaims.ResourcePackBlockStateClaims.claim(((CraftBlockData) state).getState(), priority, resultConsumer, visualDuplicateConsumer == null ? null : visualDuplicate -> visualDuplicateConsumer.accept(visualDuplicate.asBlockData()), false);
    }

    @Override
    public void claimAll(List<BlockData> states, ClaimRequestPriority priority, @Nullable BooleanConsumer resultConsumer, @Nullable Consumer<List<? extends BlockData>> visualDuplicatesConsumer) {
        spout.clientview.packetmapping.blockstate.resourcepackclaims.ResourcePackBlockStateClaims.claimAll(states.stream().map(state -> ((CraftBlockData) state).getState()).toList(), priority, resultConsumer, visualDuplicatesConsumer == null ? null : visualDuplicates -> visualDuplicatesConsumer.accept(visualDuplicates.stream().map(BlockBehaviour.BlockStateBase::asBlockData).toList()), false);
    }

    @Override
    public void claimOrSimilar(BlockData state, ClaimRequestPriority priority, @Nullable Consumer<@Nullable BlockData> resultConsumer, @Nullable Consumer<BlockData> visualDuplicateConsumer) {
        spout.clientview.packetmapping.blockstate.resourcepackclaims.ResourcePackBlockStateClaims.claimOrSimilar(((CraftBlockData) state).getState(), priority, resultConsumer == null ? null : result -> resultConsumer.accept(result == null ? null : result.asBlockData()), visualDuplicateConsumer == null ? null : visualDuplicate -> visualDuplicateConsumer.accept(visualDuplicate.asBlockData()), false);
    }

    @Override
    public void claimAllOrSimilar(List<BlockData> states, ClaimRequestPriority priority, @Nullable Consumer<@Nullable List<? extends BlockData>> resultConsumer, @Nullable Consumer<List<? extends BlockData>> visualDuplicatesConsumer) {
        spout.clientview.packetmapping.blockstate.resourcepackclaims.ResourcePackBlockStateClaims.claimAllOrSimilar(states.stream().map(state -> ((CraftBlockData) state).getState()).toList(), priority, resultConsumer == null ? null : result -> resultConsumer.accept(result == null ? null : result.stream().map(BlockBehaviour.BlockStateBase::asBlockData).toList()), visualDuplicatesConsumer == null ? null : visualDuplicates -> visualDuplicatesConsumer.accept(visualDuplicates.stream().map(BlockBehaviour.BlockStateBase::asBlockData).toList()), false);
    }

    @Override
    public void claimUsingVanillaLook(BlockData state, ClaimRequestPriority priority, @Nullable BooleanConsumer resultConsumer) {
        spout.clientview.packetmapping.blockstate.resourcepackclaims.ResourcePackBlockStateClaims.claim(((CraftBlockData) state).getState(), priority, resultConsumer, null, true);
    }

    @Override
    public void claimAllUsingVanillaLook(List<BlockData> states, ClaimRequestPriority priority, @Nullable BooleanConsumer resultConsumer) {
        spout.clientview.packetmapping.blockstate.resourcepackclaims.ResourcePackBlockStateClaims.claimAll(states.stream().map(state -> ((CraftBlockData) state).getState()).toList(), priority, resultConsumer, null, true);
    }

    @Override
    public void claimOrSimilarUsingVanillaLook(BlockData state, ClaimRequestPriority priority, @Nullable Consumer<@Nullable BlockData> resultConsumer) {
        spout.clientview.packetmapping.blockstate.resourcepackclaims.ResourcePackBlockStateClaims.claimOrSimilar(((CraftBlockData) state).getState(), priority, resultConsumer == null ? null : result -> resultConsumer.accept(result == null ? null : result.asBlockData()), null, true);
    }

    @Override
    public void claimAllOrSimilarUsingVanillaLook(List<BlockData> states, ClaimRequestPriority priority, @Nullable Consumer<@Nullable List<? extends BlockData>> resultConsumer) {
        spout.clientview.packetmapping.blockstate.resourcepackclaims.ResourcePackBlockStateClaims.claimAllOrSimilar(states.stream().map(state -> ((CraftBlockData) state).getState()).toList(), priority, resultConsumer == null ? null : result -> resultConsumer.accept(result == null ? null : result.stream().map(BlockBehaviour.BlockStateBase::asBlockData).toList()), null, true);
    }

}
