package spout.clientview.packetmapping.blockstate.resourcepackclaims;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMaps;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.level.block.state.properties.SlabType;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMappingRegistryKey;
import spout.api.clientview.packetmapping.blockstate.resourcepackclaims.ClaimRequestPriority;
import spout.api.clientview.packetmapping.blockstate.resourcepackclaims.ClaimRequestPriorityComparator;
import spout.server.paper.impl.moredatadriven.minecraft.VanillaOnlyBlockStateRegistry;
import org.jspecify.annotations.Nullable;
import spout.util.minecraft.blockstate.visualduplicates.VisualDuplicateGroup;
import spout.util.minecraft.blockstate.visualduplicates.VisualDuplicates;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.Stream;

/**
 * Provides functionality to register claims to block states
 * as they are shown to {@link AwarenessLevels#RESOURCE_PACK} clients,
 * to avoid collisions with other plugins.
 *
 * <p>
 * When your plugin relies on the visual representation of a block state, as it appears to
 * {@link AwarenessLevels#RESOURCE_PACK} clients, you should register your claim
 * to that block state here.
 * </p>
 *
 * <p>
 * This ensures that other plugins (and Spout built-in functionality such as proxy mappings)
 * do not alter the visuals of that block state.
 * </p>
 *
 * <p>
 * Claims are not run immediately when you call them. Rather, various claims are collected
 * and evaluated according to their {@linkplain ClaimRequestPriority priority}.
 * </p>
 *
 * <p>
 * Successful claims should normally be followed up by registering the claimed states
 * with the {@link BlockStateMappingRegistryKey#BLOCK_STATE_MAPPING} registry.
 * </p>
 *
 * <p>
 * For most states, claims are governed by their {@linkplain VisualDuplicates visual duplicates}.
 * For such states, this class tracks whether they have already been claimed,
 * and keeps track of how many states in a visual duplicates group have already been claimed,
 * to make sure that at least one state remains unclaimed.
 * That state is then also used as the visual duplicate to map claimed block states to.
 * </p>
 *
 * <p>
 * Some states have a unique visual representation, but are never used in vanilla.
 * This includes redstone dust that only points in 1 direction, and waterlogged double slabs.
 * These can also be claimed. If it then turns out that they do actually occur on the server,
 * they will be mapped to the most appropriate vanilla block (such as a non-waterlogged double slab).
 * </p>
 *
 * <p>
 * Other states, i.e. those that do not fit either of the above claiming strategies,
 * are not claimable.
 * </p>
 *
 * <p>
 * Note that claim requests to some block states that are normally claimable
 * may be blocked by the server's configuration. TODO Implement configuration for this
 * </p>
 */
public final class ResourcePackBlockStateClaims {

    private ResourcePackBlockStateClaims() {
        throw new UnsupportedOperationException();
    }

    private static int nextClaimId;
    private static final TreeSet<ClaimRequest> requests;
    private static @Nullable AllRequestsProcessingState processingState;
    private static List<IntConsumer> claimListeners = new ArrayList<>();

    static {
        Comparator<ClaimRequestPriority> requestPriorityComparator = new ClaimRequestPriorityComparator(Object2DoubleMaps.emptyMap()); /* TODO fill with useful values based on how often block states occur */
        requests = new TreeSet<>(Comparator.comparing(ClaimRequest::priority, requestPriorityComparator).thenComparingInt(request -> -request.id));
    }

    public static void registerClaimListener(IntConsumer consumer) {
        claimListeners.add(consumer);
    }

    public static boolean isClaimed(BlockState state) {
        return processingState != null && processingState.isClaimed(state.indexInVanillaOnlyBlockStateRegistry);
    }

    public static boolean isClaimed(int state) {
        return processingState != null && processingState.isClaimed(state);
    }

    public static boolean isClaimedNonVanilla(BlockState state) {
        return processingState != null && processingState.isClaimedNonVanilla(state.indexInVanillaOnlyBlockStateRegistry);
    }

    public static boolean isClaimedNonVanilla(int state) {
        return processingState != null && processingState.isClaimedNonVanilla(state);
    }

    public static void processRequests() {
        processingState = new AllRequestsProcessingState();
        processingState.process();
    }

    public static void claim(int[] states, ClaimRequestPriority priority, @Nullable Consumer<int @Nullable []> resultConsumer, @Nullable Consumer<int[]> visualDuplicatesConsumer, boolean orSimilar, boolean usingVanillaLook) {
        if (Arrays.stream(states).distinct().count() != states.length) {
            throw new IllegalArgumentException("states cannot contain duplicates");
        }
        requests.add(new ClaimRequest(nextClaimId++, states, priority, resultConsumer, visualDuplicatesConsumer, orSimilar, usingVanillaLook));
    }

    public static void claim(List<BlockState> states, ClaimRequestPriority priority, @Nullable Consumer<@Nullable List<BlockState>> resultConsumer, @Nullable Consumer<List<BlockState>> visualDuplicatesConsumer, boolean orSimilar, boolean usingVanillaLook) {
        claim(
            states.stream().mapToInt(state -> state.indexInVanillaOnlyBlockStateRegistry).toArray(),
            priority,
            resultConsumer == null ? null : result -> resultConsumer.accept(result != null ? Arrays.stream(result).mapToObj(id -> VanillaOnlyBlockStateRegistry.get().byId(id)).toList() : null),
            visualDuplicatesConsumer == null ? null : visualDuplicates -> visualDuplicatesConsumer.accept(Arrays.stream(visualDuplicates).mapToObj(id -> VanillaOnlyBlockStateRegistry.get().byId(id)).toList()),
            orSimilar,
            usingVanillaLook
        );
    }

    public static void claim(BlockState state, ClaimRequestPriority priority, @Nullable BooleanConsumer resultConsumer, @Nullable Consumer<BlockState> visualDuplicateConsumer, boolean usingVanillaLook) {
        claimAll(
            List.of(state),
            priority,
            resultConsumer,
            visualDuplicateConsumer == null ? null : visualDuplicates -> visualDuplicateConsumer.accept(visualDuplicates.getFirst()),
            usingVanillaLook
        );
    }

    public static void claimAll(List<BlockState> states, ClaimRequestPriority priority, @Nullable BooleanConsumer resultConsumer, @Nullable Consumer<List<BlockState>> visualDuplicatesConsumer, boolean usingVanillaLook) {
        claim(
            states,
            priority,
            resultConsumer == null ? null : result -> resultConsumer.accept(result != null),
            visualDuplicatesConsumer,
            false,
            usingVanillaLook
        );
    }

    public static void claimOrSimilar(BlockState state, ClaimRequestPriority priority, @Nullable Consumer<@Nullable BlockState> resultConsumer, @Nullable Consumer<BlockState> visualDuplicateConsumer, boolean usingVanillaLook) {
        claimAllOrSimilar(
            List.of(state),
            priority,
            resultConsumer == null ? null : result -> resultConsumer.accept(result != null ? result.getFirst() : null),
            visualDuplicateConsumer == null ? null : visualDuplicates -> visualDuplicateConsumer.accept(visualDuplicates.getFirst()),
            usingVanillaLook
        );
    }

    public static void claimAllOrSimilar(List<BlockState> states, ClaimRequestPriority priority, @Nullable Consumer<@Nullable List<BlockState>> resultConsumer, @Nullable Consumer<List<BlockState>> visualDuplicatesConsumer, boolean usingVanillaLook) {
        claim(
            states,
            priority,
            resultConsumer,
            visualDuplicatesConsumer,
            true,
            usingVanillaLook
        );
    }

    public static void claim(BlockState state, ClaimRequestPriority priority, @Nullable BooleanConsumer resultConsumer, @Nullable Consumer<BlockState> visualDuplicateConsumer) {
        claim(state, priority, resultConsumer, visualDuplicateConsumer, false);
    }

    public static void claimAll(List<BlockState> states, ClaimRequestPriority priority, @Nullable BooleanConsumer resultConsumer, @Nullable Consumer<List<BlockState>> visualDuplicatesConsumer) {
        claimAll(states, priority, resultConsumer, visualDuplicatesConsumer, false);
    }

    public static void claimOrSimilar(BlockState state, ClaimRequestPriority priority, @Nullable Consumer<@Nullable BlockState> resultConsumer, @Nullable Consumer<BlockState> visualDuplicateConsumer) {
        claimOrSimilar(state, priority, resultConsumer, visualDuplicateConsumer, false);
    }

    public static void claimAllOrSimilar(List<BlockState> states, ClaimRequestPriority priority, @Nullable Consumer<@Nullable List<BlockState>> resultConsumer, @Nullable Consumer<List<BlockState>> visualDuplicatesConsumer) {
        claimAllOrSimilar(states, priority, resultConsumer, visualDuplicatesConsumer, false);
    }

    public static void claimUsingVanillaLook(BlockState state, ClaimRequestPriority priority, @Nullable BooleanConsumer resultConsumer) {
        claim(state, priority, resultConsumer, null, true);
    }

    public static void claimAllUsingVanillaLook(List<BlockState> states, ClaimRequestPriority priority, @Nullable BooleanConsumer resultConsumer) {
        claimAll(states, priority, resultConsumer, null, true);
    }

    public static void claimOrSimilarUsingVanillaLook(BlockState state, ClaimRequestPriority priority, @Nullable Consumer<@Nullable BlockState> resultConsumer) {
        claimOrSimilar(state, priority, resultConsumer, null, true);
    }

    public static void claimAllOrSimilarUsingVanillaLook(List<BlockState> states, ClaimRequestPriority priority, @Nullable Consumer<@Nullable List<BlockState>> resultConsumer) {
        claimAllOrSimilar(states, priority, resultConsumer, null, true);
    }

    private static boolean isWaterloggedDoubleSlab(BlockState state) {
        return state.getBlock() instanceof SlabBlock && state.getValue(BlockStateProperties.SLAB_TYPE) == SlabType.DOUBLE && state.getValue(BlockStateProperties.WATERLOGGED);
    }

    private static boolean isSingleDirectionRedstoneWire(BlockState state) {
        return state.getBlock() == Blocks.REDSTONE_WIRE && Stream.of(BlockStateProperties.NORTH_REDSTONE, BlockStateProperties.EAST_REDSTONE, BlockStateProperties.SOUTH_REDSTONE, BlockStateProperties.WEST_REDSTONE).filter(property -> state.getValue(property) == RedstoneSide.NONE).count() == 3;
    }

    /**
     * Utility class that represents a claim request submitted to one of the
     * {@link ResourcePackBlockStateClaims} methods.
     */
    private record ClaimRequest(
        int id,
        int[] states,
        ClaimRequestPriority priority,
        @Nullable Consumer<@Nullable int[]> resultConsumer,
        @Nullable Consumer<int[]> visualDuplicatesConsumer,
        boolean orSimilar,
        boolean usingVanillaLook
    ) {
    }

    /**
     * Utility class that holds the state of processing during
     * {@link #processRequests()}.
     */
    private static final class AllRequestsProcessingState {

        /**
         * The cached {@link VanillaOnlyBlockStateRegistry}.
         */
        final VanillaOnlyBlockStateRegistry registry;

        /**
         * The cached {@link VanillaOnlyBlockStateRegistry#size()}.
         */
        final int registrySize;

        /**
         * Which block states have been claimed,
         * by their {@link BlockState#indexInVanillaOnlyBlockStateRegistry}.
         */
        final boolean[] claimed;

        /**
         * The same as {@link #claimed},
         * but only if the claim is a non-vanilla-look claim.
         */
        final boolean[] claimedNonVanilla;

        /**
         * Which block states must be mapped to a visual duplicate,
         * indexed by their {@link BlockState#indexInVanillaOnlyBlockStateRegistry}.
         *
         * <p>
         * The states that do require a visual duplicate will have a non-null {@link IntConsumer},
         * to which the {@link BlockState#indexInVanillaOnlyBlockStateRegistry} of the visual duplicate
         * must be passed.
         * </p>
         */
        final @Nullable IntConsumer[] visualDuplicateListeners;

        /**
         * The number of non-vanilla-look claims on a visual duplicate group returned by
         * {@link VisualDuplicates#getVisualDuplicates},
         * indexed by {@link VisualDuplicateGroup#getId()}.
         */
        final Int2IntMap visualDuplicateGroupNonVanillaClaimCount;

        /**
         * The same as {@link #visualDuplicateGroupNonVanillaClaimCount},
         * but for vanilla-look claims.
         */
        final Int2IntMap visualDuplicateGroupVanillaClaimCount;

        AllRequestsProcessingState() {
            this.registry = VanillaOnlyBlockStateRegistry.get();
            this.registrySize = registry.size();
            this.claimed = new boolean[registrySize];
            this.claimedNonVanilla = new boolean[registrySize];
            this.visualDuplicateListeners = new IntConsumer[registrySize];
            this.visualDuplicateGroupVanillaClaimCount = new Int2IntArrayMap();
            this.visualDuplicateGroupNonVanillaClaimCount = new Int2IntArrayMap();
            // Claim some block states using their vanilla look
            for (BlockState state : getBlockStatesToClaimUsingVanillaLookByDefault()) {
                int id = state.indexInVanillaOnlyBlockStateRegistry;
                this.claimed[id] = true;
                @Nullable VisualDuplicateGroup group = VisualDuplicates.getVisualDuplicates(id);
                if (group != null) {
                    this.visualDuplicateGroupVanillaClaimCount.put(group.getId(), 1);
                }
            }
        }

        boolean isClaimed(int state) {
            return this.claimed[state];
        }

        boolean isClaimedNonVanilla(int state) {
            return this.claimedNonVanilla[state];
        }

        boolean isClaimedVanilla(int state) {
            return this.isClaimed(state) && !this.isClaimedNonVanilla(state);
        }

        int getFirstUnclaimedOrVanillaClaimed(int[] states) {
            for (int state : states) {
                if (!this.claimedNonVanilla[state]) {
                    return state;
                }
            }
            throw new IllegalStateException("No unclaimed or vanilla-look claimed state in given states");
        }

        int getFirstUnclaimedOrVanillaClaimed(VisualDuplicateGroup group) {
            return this.getFirstUnclaimedOrVanillaClaimed(group.stateIndices);
        }

        /**
         * Calls any {@link ClaimRequest#visualDuplicatesConsumer}s.
         */
        void callVisualDuplicatesConsumers() {
            Int2IntMap visualDuplicateGroupMappingTarget = new Int2IntArrayMap();
            for (int state = 0; state < this.visualDuplicateListeners.length; state++) {
                @Nullable IntConsumer listener = this.visualDuplicateListeners[state];
                if (listener == null) continue;

                @Nullable VisualDuplicateGroup group = VisualDuplicates.getVisualDuplicates(state);
                int target;
                if (group != null) {

                    if (visualDuplicateGroupMappingTarget.containsKey(group.getId())) {
                        // Use the already chosen target for the visual duplicate group
                        target = visualDuplicateGroupMappingTarget.get(group.getId());
                    } else {
                        // Choose the best target from the visual duplicate group
                        target = this.getFirstUnclaimedOrVanillaClaimed(group);
                        visualDuplicateGroupMappingTarget.put(group.getId(), target);
                    }

                } else {

                    // The block state should not be mapped to a visual duplicate from a group, but from unused to used block state
                    BlockState blockState = VanillaOnlyBlockStateRegistry.get().byId(state);
                    if (isWaterloggedDoubleSlab(blockState)) {
                        target = blockState.setValue(BlockStateProperties.WATERLOGGED, false).indexInVanillaOnlyBlockStateRegistry;
                    } else if (isSingleDirectionRedstoneWire(blockState)) {
                        if (blockState.getValue(BlockStateProperties.SOUTH_REDSTONE) != RedstoneSide.NONE) {
                            target = blockState.setValue(BlockStateProperties.NORTH_REDSTONE, RedstoneSide.SIDE).indexInVanillaOnlyBlockStateRegistry;
                        } else if (blockState.getValue(BlockStateProperties.NORTH_REDSTONE) != RedstoneSide.NONE) {
                            target = blockState.setValue(BlockStateProperties.SOUTH_REDSTONE, RedstoneSide.SIDE).indexInVanillaOnlyBlockStateRegistry;
                        } else if (blockState.getValue(BlockStateProperties.WEST_REDSTONE) != RedstoneSide.NONE) {
                            target = blockState.setValue(BlockStateProperties.EAST_REDSTONE, RedstoneSide.SIDE).indexInVanillaOnlyBlockStateRegistry;
                        } else if (blockState.getValue(BlockStateProperties.EAST_REDSTONE) != RedstoneSide.NONE) {
                            target = blockState.setValue(BlockStateProperties.WEST_REDSTONE, RedstoneSide.SIDE).indexInVanillaOnlyBlockStateRegistry;
                        } else {
                            throw new IllegalStateException("Invalid single direction redstone wire: " + blockState);
                        }
                    } else {
                        throw new IllegalStateException("State without known visual duplicate mapping: " + blockState);
                    }

                }

                listener.accept(target);
            }
        }

        void process() {

            // Process the claims
            while (true) {
                @Nullable ClaimRequest highestPriorityRequest = requests.pollLast();
                if (highestPriorityRequest == null) {
                    break;
                }
                new SingleRequestProcessingState(highestPriorityRequest).process();
            }

            // Apply the visual duplicate mappings
            this.callVisualDuplicatesConsumers();

        }

        /**
         * Utility class that holds the state of processing for each request during
         * {@link AllRequestsProcessingState#process()}.
         */
        private final class SingleRequestProcessingState {

            /**
             * The {@link ClaimRequest}.
             */
            final ClaimRequest request;

            /**
             * The visual duplicate group used, for each of the requested states,
             * or null at an index where no visual duplicate group is used.
             */
            final @Nullable VisualDuplicateGroup[] visualDuplicateGroupPerRequestedState;

            /**
             * The number of claims that will be made to a visual duplicate group,
             * indexed by the {@link VisualDuplicateGroup#getId()}.
             */
            final Int2IntMap neededNewClaimCountPerVisualDuplicateGroup;

            /**
             * The actual state to claim, for each of the request states,
             * or -1 if not known yet.
             */
            final int[] definitiveStateToClaimPerRequestedState;

            SingleRequestProcessingState(ClaimRequest request) {
                this.request = request;
                this.visualDuplicateGroupPerRequestedState = new VisualDuplicateGroup[request.states.length];
                this.neededNewClaimCountPerVisualDuplicateGroup = new Int2IntArrayMap();
                this.definitiveStateToClaimPerRequestedState = new int[request.states.length];
                Arrays.fill(this.definitiveStateToClaimPerRequestedState, -1);
            }

            private @Nullable Boolean tryPreprocessRequestStateUsingVisualDuplicateGroup(int requestedStateI, int requestedState) {

                @Nullable VisualDuplicateGroup visualDuplicateGroup = VisualDuplicates.getVisualDuplicates(requestedState);
                if (visualDuplicateGroup == null) {
                    // Not in a visual duplicate group
                    return null;
                }

                this.visualDuplicateGroupPerRequestedState[requestedStateI] = visualDuplicateGroup;
                // Save that we need a claim for this visual duplicate group
                this.neededNewClaimCountPerVisualDuplicateGroup.compute(visualDuplicateGroup.getId(), ($, value) -> value != null ? value + 1 : 1);
                return true;

            }

            private @Nullable Boolean tryPreprocessRequestStateFromUnusedState(int requestedStateI, int requestedState) {
                BlockState blockState = VanillaOnlyBlockStateRegistry.get().byId(requestedState);

                if (isWaterloggedDoubleSlab(blockState) || isSingleDirectionRedstoneWire(blockState)) {

                    // There are no identical alternatives for any such state, so it must not be claimed
                    if (this.request.usingVanillaLook ? AllRequestsProcessingState.this.isClaimedNonVanilla(requestedState) : AllRequestsProcessingState.this.isClaimed(requestedState)) {
                        return false;
                    }
                    // Can claim the unused state
                    this.definitiveStateToClaimPerRequestedState[requestedStateI] = requestedState;
                    return true;

                }

                // Not an unused state
                return null;
            }

            private boolean preprocessState(int requestedStateI) {

                // Get the state
                int requestedState = this.request.states[requestedStateI];

                // Check if the state is already claimed as vanilla
                if (this.request.usingVanillaLook) {
                    if (AllRequestsProcessingState.this.isClaimedVanilla(requestedState)) {
                        // The block state is already claimed as vanilla
                        this.definitiveStateToClaimPerRequestedState[requestedStateI] = requestedState;
                        return true;
                    }
                }
                if (!this.request.orSimilar && AllRequestsProcessingState.this.isClaimed(requestedState)) {
                    // The block state is already claimed
                    return false;
                }

                // Try claiming it using visual duplicates
                @Nullable Boolean result = this.tryPreprocessRequestStateUsingVisualDuplicateGroup(requestedStateI, requestedState);
                if (result != null) {
                    return result;
                }
                // Check if the requested state is unused
                result = this.tryPreprocessRequestStateFromUnusedState(requestedStateI, requestedState);
                if (result != null) {
                    return result;
                }
                // Try claiming the block state directly if we desire the vanilla look
                if (this.request.usingVanillaLook) {
                    if (AllRequestsProcessingState.this.isClaimed(requestedState)) {
                        // The block state is already claimed as non-vanilla
                        return false;
                    }
                    // Claim it directly
                    this.definitiveStateToClaimPerRequestedState[requestedStateI] = requestedState;
                    return true;
                }

                // The block state is not claimable at all
                return false;

            }

            /**
             * Checks if enough claims available for each visual duplicate group from which we need claims.
             */
            private boolean checkEnoughClaimsAvailable() {
                if (!this.request.usingVanillaLook) {
                    for (Int2IntMap.Entry entry : this.neededNewClaimCountPerVisualDuplicateGroup.int2IntEntrySet()) {
                        int alreadyClaimedVanilla = AllRequestsProcessingState.this.visualDuplicateGroupVanillaClaimCount.get(entry.getIntKey());
                        int alreadyClaimed = AllRequestsProcessingState.this.visualDuplicateGroupNonVanillaClaimCount.get(entry.getIntKey()) + alreadyClaimedVanilla;
                        if (alreadyClaimed + entry.getIntValue() >= VisualDuplicates.getVisualDuplicates(entry.getIntKey()).stateIndices.length - (alreadyClaimedVanilla > 0 ? 1 : 0)) {
                            // Too many claims on a visual duplicate group
                            return false;
                        }
                    }
                }
                return true;
            }

            private void storeSuccessfulClaim() {

                // Initialize the visual duplicate container if we need it for visual duplicates consumers
                class VisualDuplicatesContainer {

                    int[] visualDuplicates;
                    int setCount;
                    Consumer<int[]> visualDuplicatesConsumer;

                    VisualDuplicatesContainer(int size, Consumer<int[]> visualDuplicatesConsumer) {
                        this.visualDuplicates = new int[size];
                        this.visualDuplicatesConsumer = visualDuplicatesConsumer;
                    }

                    void set(int i, int value) {
                        this.visualDuplicates[i] = value;
                        if (++this.setCount == this.visualDuplicates.length) {
                            this.visualDuplicatesConsumer.accept(this.visualDuplicates);
                        }
                    }

                }
                @Nullable VisualDuplicatesContainer visualDuplicateContainer = this.request.visualDuplicatesConsumer == null ? null : new VisualDuplicatesContainer(this.request.states.length, this.request.visualDuplicatesConsumer);

                for (int requestedStateI = 0; requestedStateI < this.request.states.length; requestedStateI++) {
                    int finalRequestedStateI = requestedStateI;
                    int stateToClaim = this.definitiveStateToClaimPerRequestedState[requestedStateI];

                    // Determine the state to claim if not known yet: only needed in the case of a state in a visual duplicate group
                    if (stateToClaim == -1) {

                        int state = this.request.states[requestedStateI];
                        @Nullable VisualDuplicateGroup group = this.visualDuplicateGroupPerRequestedState[requestedStateI];
                        if (group == null) {
                            throw new IllegalStateException("No visual duplicate group for requested state without an already determined state to claim");
                        }

                        if (this.request.usingVanillaLook) {

                            if (!this.request.orSimilar) {
                                // We simply claim the state itself
                                stateToClaim = this.request.states[requestedStateI];
                            } else {
                                // We must choose another state from the visual duplicate group
                                // First try an already claimed vanilla state
                                OptionalInt existingClaimed = Arrays.stream(VisualDuplicates.getVisualDuplicates(state).stateIndices).filter(alternative -> AllRequestsProcessingState.this.isClaimedVanilla(alternative)).findFirst();
                                if (existingClaimed.isPresent()) {
                                    stateToClaim = existingClaimed.getAsInt();
                                } else {
                                    // Make a new claim
                                    stateToClaim = Arrays.stream(VisualDuplicates.getVisualDuplicates(state).stateIndices).filter(alternative -> !AllRequestsProcessingState.this.isClaimed(alternative)).findFirst().getAsInt();
                                }
                            }
                            if (!AllRequestsProcessingState.this.isClaimedVanilla(stateToClaim)) {
                                // Increment the used claims count of the group
                                AllRequestsProcessingState.this.visualDuplicateGroupVanillaClaimCount.compute(group.getId(), ($, value) -> value != null ? value + 1 : 1);
                            }

                        } else {

                            if (!AllRequestsProcessingState.this.isClaimed(state) || !this.request.orSimilar) {
                                // We simply claim the state itself
                                stateToClaim = this.request.states[requestedStateI];
                            } else {
                                // We must choose another state from the visual duplicate group
                                stateToClaim = Arrays.stream(VisualDuplicates.getVisualDuplicates(state).stateIndices).filter(alternative -> !AllRequestsProcessingState.this.isClaimed(alternative) && Arrays.stream(this.request.states).noneMatch(given -> given == alternative)).findFirst().getAsInt();
                            }
                            // Increment the used claims count of the group
                            AllRequestsProcessingState.this.visualDuplicateGroupNonVanillaClaimCount.compute(group.getId(), ($, value) -> value != null ? value + 1 : 1);

                        }

                        // Save the state to claim
                        this.definitiveStateToClaimPerRequestedState[requestedStateI] = stateToClaim;

                    }

                    // Store the claim
                    AllRequestsProcessingState.this.claimed[stateToClaim] = true;
                    if (!this.request.usingVanillaLook) {
                        AllRequestsProcessingState.this.claimedNonVanilla[stateToClaim] = true;
                    }
                    for (IntConsumer claimListener : claimListeners) {
                        claimListener.accept(stateToClaim);
                    }

                    // Mark if we need a mapping to a visual duplicate
                    if (visualDuplicateContainer != null) {
                        AllRequestsProcessingState.this.visualDuplicateListeners[stateToClaim] = visualDuplicate -> {
                            visualDuplicateContainer.set(finalRequestedStateI, visualDuplicate);
                        };
                    }

                }
            }

            void process() {

                // Check for each state what we need to do, and keep track of it
                for (int requestedStateI = 0; requestedStateI < this.request.states.length; requestedStateI++) {
                    boolean success = this.preprocessState(requestedStateI);
                    if (!success) {
                        if (this.request.resultConsumer != null) this.request.resultConsumer.accept(null);
                        return;
                    }
                }

                // Check if enough claims are available for visual duplicate groups
                if (!this.checkEnoughClaimsAvailable()) {
                    if (this.request.resultConsumer != null) this.request.resultConsumer.accept(null);
                    return;
                }

                // We will be successful, make the claim definite
                this.storeSuccessfulClaim();

                // Run the result consumer
                if (this.request.resultConsumer != null)
                    this.request.resultConsumer.accept(this.definitiveStateToClaimPerRequestedState);

            }

        }

    }

    /**
     * @return Block states that we always claim using their vanilla look.
     * This includes {@link Blocks#STONE}, because it is used as a fallback of last resort,
     * and some other block states that may have generally more undesirable visual duplicates,
     * such as oak planks (being sometimes replaced by a double petrified oak slab, which is very undesirable
     * since it gives the very common oak planks block a different destroy speed and tool),
     * or because they are so common that we'd prefer to avoid having to map them to something else
     * in every packet.
     */
    // Claim some block states using their vanilla look, because they have duplicates but we rea
    private static Iterable<BlockState> getBlockStatesToClaimUsingVanillaLookByDefault() {
        Set<BlockState> result = new HashSet<>();
        Stream.of(
            Blocks.STONE, // Fallback of last resort
            Blocks.CHISELED_STONE_BRICKS,
            Blocks.COBBLESTONE,
            Blocks.CRACKED_STONE_BRICKS,
            Blocks.DEEPSLATE,
            Blocks.MOSSY_STONE_BRICKS,
            Blocks.OAK_PLANKS,
            Blocks.STONE_BRICKS
        ).map(block -> block.getStateDefinition().getPossibleStates()).forEach(result::addAll);
        Stream.of(
            Blocks.OAK_SLAB
        ).flatMap(block -> block.getStateDefinition().getPossibleStates().stream()).filter(state -> !(state.getValue(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.SLAB_TYPE) == SlabType.DOUBLE)).forEach(result::add);
        return result;
    }

}
