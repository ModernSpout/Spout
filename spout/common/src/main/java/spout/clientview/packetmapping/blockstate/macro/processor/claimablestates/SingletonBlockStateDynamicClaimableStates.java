package spout.clientview.packetmapping.blockstate.macro.processor.claimablestates;

import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.blockstate.resourcepackclaims.ResourcePackBlockStateClaims;
import spout.util.minecraft.blockstate.visualduplicates.VisualDuplicateGroup;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.function.Supplier;

/**
 * A producer of {@link SortedClaimableStates} instances
 * that is based off an initial list of {@link BlockState}s.
 *
 * <p>
 * Each {@link SortedClaimableStates#get} call will return an array of length 1.
 * </p>
 */
public class SingletonBlockStateDynamicClaimableStates implements DynamicClaimableStates {

    /**
     * The backing value for {@link #get)},
     * or null if not initialized yet.
     */
    private @Nullable LinkedHashMap<Integer, BlockState> values;

    /**
     * A supplier of the initial states,
     * or null if dereferenced.
     */
    private @Nullable Supplier<Collection<BlockState>> initialStatesSupplier;

    /**
     * Whether these states are fallback states.
     */
    private final boolean isFallback;

    private SingletonBlockStateDynamicClaimableStates(Supplier<Collection<BlockState>> initialStatesSupplier, boolean isFallback) {
        this.initialStatesSupplier = initialStatesSupplier;
        this.isFallback = isFallback;
    }

    @Override
    public SortedClaimableStates get(BlockState from) {
        if (this.values == null) {
            Collection<BlockState> initialStates = this.initialStatesSupplier.get();
            this.initialStatesSupplier = null;
            this.values = new LinkedHashMap<>(initialStates.size());
            initialStates.stream()
                .filter(state -> !(this.isFallback ? ResourcePackBlockStateClaims.isClaimedNonVanilla(state) : ResourcePackBlockStateClaims.isClaimed(state)))
                .sorted(VisualDuplicateGroup.STATE_COMPARATOR)
                .forEach(state -> this.values.put(state.indexInVanillaOnlyBlockStateRegistry, state));
            if (!this.isFallback) {
                ResourcePackBlockStateClaims.registerClaimListener(this.values::remove);
            }
        }
        return SortedClaimableStates.of(from, this.values.values().toArray(BlockState[]::new));
    }

    public static SingletonBlockStateDynamicClaimableStates forProxy(Supplier<Collection<BlockState>> initialStatesSupplier) {
        return new SingletonBlockStateDynamicClaimableStates(initialStatesSupplier, false);
    }

    public static SingletonBlockStateDynamicClaimableStates forFallback(Supplier<Collection<BlockState>> initialStatesSupplier) {
        return new SingletonBlockStateDynamicClaimableStates(initialStatesSupplier, true);
    }

}
