package spout.server.paper.impl.packetmapping.block.automatic;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.function.Supplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jspecify.annotations.Nullable;
import spout.server.paper.impl.moredatadriven.minecraft.VanillaOnlyBlockStateRegistry;
import spout.server.paper.impl.packetmapping.block.claim.ResourcePackBlockStateClaimsImpl;
import spout.server.paper.impl.packetmapping.block.claim.VisualDuplicatesImpl;

/**
 * A producer of {@link SortedClaimableStates} instances
 * that is based off an initial list of {@link Block}s that are claimed whole.
 *
 * <p>
 * Each {@link SortedClaimableStates#get} call will return an array with the same size
 * as the corresponding {@link StateDefinition#getPossibleStates()}.
 * </p>
 */
public class BlockDynamicClaimableStates implements DynamicClaimableStates {

    /**
     * The backing value for {@link #get)},
     * or null if not initialized yet.
     */
    private @Nullable LinkedHashSet<Block> values;

    /**
     * A supplier of the initial blocks,
     * or null if dereferenced.
     *
     * <p>
     * The {@link Block}s it returns must all have the exact same block state properties.
     * </p>
     */
    private @Nullable Supplier<Collection<Block>> initialBlocksSupplier;

    /**
     * Whether these states are fallback states.
     */
    private final boolean isFallback;

    private BlockDynamicClaimableStates(Supplier<Collection<Block>> initialBlocksSupplier, boolean isFallback) {
        this.initialBlocksSupplier = initialBlocksSupplier;
        this.isFallback = isFallback;
    }

    @Override
    public SortedClaimableStates get(BlockState from) {
        if (this.values == null) {
            Collection<Block> initialBlocks = this.initialBlocksSupplier.get();
            this.initialBlocksSupplier = null;
            this.values = new LinkedHashSet<>(initialBlocks.size());
            initialBlocks.stream()
                .filter(block -> block.getStateDefinition().getPossibleStates().stream().noneMatch(state -> this.isFallback ? ResourcePackBlockStateClaimsImpl.get().isClaimedNonVanilla(state) : ResourcePackBlockStateClaimsImpl.get().isClaimed(state)))
                .sorted(VisualDuplicatesImpl.VisualDuplicateGroupImpl.BLOCK_COMPARATOR)
                .forEach(this.values::add);
            if (!this.isFallback) {
                ResourcePackBlockStateClaimsImpl.get().registerClaimListener(state -> this.values.remove(VanillaOnlyBlockStateRegistry.get().byId(state).getBlock()));
            }
        }
        return SortedClaimableStates.of(from, this.values.stream().map(block -> block.getStateDefinition().getPossibleStates().toArray(BlockState[]::new)).toArray(BlockState[][]::new));
    }

    public static BlockDynamicClaimableStates forProxy(Supplier<Collection<Block>> initialBlocksSupplier) {
        return new BlockDynamicClaimableStates(initialBlocksSupplier, false);
    }

    public static BlockDynamicClaimableStates forFallback(Supplier<Collection<Block>> initialBlocksSupplier) {
        return new BlockDynamicClaimableStates(initialBlocksSupplier, true);
    }

}
