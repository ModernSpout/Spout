package spout.clientview.packetmapping.blockstate.macro.processor;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import spout.api.clientview.model.ClientView;
import spout.api.clientview.packetmapping.blockstate.resourcepackclaims.ClaimRequestPriority;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMapping;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMappingRegistry;
import spout.clientview.packetmapping.blockstate.resourcepackclaims.ResourcePackBlockStateClaims;
import spout.server.paper.api.packetmapping.item.nms.ItemMappingUtilitiesNMS;
import spout.server.paper.impl.moredatadriven.minecraft.VanillaOnlyBlockStateRegistry;
import spout.server.paper.impl.packetmapping.block.automatic.FromToItemRequestBuilderImpl;
import spout.server.paper.impl.packetmapping.item.ItemMappingsImpl;
import spout.util.mapping.handle.DirectMappingStep;
import spout.util.minecraft.blockstate.HoneyLevelUtil;
import spout.util.minecraft.blockstate.visualduplicates.BlocksWithVisuallyDifferentBlockstates;
import spout.util.minecraft.resources.IdentifierUtil;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A processor for {@link BlockStateMappingMacro}s.
 *
 * <p>
 * Each processor instance is for one {@link BlockStateMappingMacro} instance.
 * </p>
 */
public abstract class BlockStateMappingMacroProcessor<M extends BlockStateMappingMacro> {

    protected final M macro;
    protected final Registry<BlockStateMappingMacro> sourceRegistry;
    protected final Registry<BlockStateMapping> targetRegistry;

    private @Nullable Identifier macroKey;

    protected BlockStateMappingMacroProcessor(M macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        this.sourceRegistry = sourceRegistry;
        this.macro = macro;
        this.targetRegistry = targetRegistry;
    }

    public Identifier getMacroKey() {
        if (this.macroKey == null) {
            this.macroKey = this.sourceRegistry.getKey(this.macro);
        }
        return this.macroKey;
    }

    public abstract void process();

    public void attemptClaimOfProxyOrFallbackStates(
        BlockState[] proxyCandidateStates,
        ClaimRequestPriority priority,
        @Nullable Consumer<int @Nullable []> resultConsumer,
        boolean isFallback
    ) {
        attemptClaimOfProxyOrFallbackStates(
            this.targetRegistry,
            proxyCandidateStates,
            priority,
            this.getMacroKey(),
            this.macro.createProxyToVisualDuplicateMappings,
            this.macro.createItemMappings,
            resultConsumer,
            isFallback
        );
    }

    /**
     * Attempts to {@linkplain ResourcePackBlockStateClaims#claim claim} block states for use as
     * proxy states for some given states.
     *
     * @param targetRegistry                             The {@linkplain BlockStateMappingRegistry#BLOCK_STATE_MAPPING target registry}.
     * @param proxyCandidateStates                 The candidate proxy states, which must be distinct.
     * @param priority                             A pre-computed {@link ClaimRequestPriority}.
     * @param createProxyToVisualDuplicateMappings If this is true and the claim is successful,
     *                                             the claimed proxy states will be mapped to visual duplicates
     *                                             for {@link AwarenessLevels#RESOURCE_PACK} clients.
     * @param createItemMappings                   If this is true, and {@code createProxyToVisualDuplicateMapping}
     *                                             is also true, and the claim is successful,
     *                                             item mappings may be created from items corresponding
     *                                             to the claimed proxy states to items corresponding to their
     *                                             visual duplicates.
     * @param resultConsumer                       The {@code resultConsumer} passed to
     *                                             {@link ResourcePackBlockStateClaims#claim}.
     * @param isFallback                           Whether this claim is for fallback states
     *                                             rather than proxy states.
     */
    public static void attemptClaimOfProxyOrFallbackStates(
        Registry<BlockStateMapping> targetRegistry,
        BlockState[] proxyCandidateStates,
        ClaimRequestPriority priority,
        Identifier macroKey,
        boolean createProxyToVisualDuplicateMappings,
        boolean createItemMappings,
        @Nullable Consumer<int @Nullable []> resultConsumer,
        boolean isFallback
    ) {
        // Translate the proxy candidates to their registry indices
        int[] proxyCandidateStateIndicesInRegistry = new int[proxyCandidateStates.length];
        for (int proxyCandidateStateIndex = 0; proxyCandidateStateIndex < proxyCandidateStateIndicesInRegistry.length; proxyCandidateStateIndex++) {
            proxyCandidateStateIndicesInRegistry[proxyCandidateStateIndex] = proxyCandidateStates[proxyCandidateStateIndex].indexInVanillaOnlyBlockStateRegistry;
        }
        boolean consumeVisualDuplicates = !isFallback && createProxyToVisualDuplicateMappings;
        int[][] claimedStates = consumeVisualDuplicates ? new int[1][] : null; // Filled by result consumer to then by used by visual duplicate consumer
        // Attempt the claim
        ResourcePackBlockStateClaims.claim(
            proxyCandidateStateIndicesInRegistry,
            priority,
            consumeVisualDuplicates ? result -> {
                claimedStates[0] = result;
                if (resultConsumer != null) {
                    resultConsumer.accept(result);
                }
            } : resultConsumer,
            consumeVisualDuplicates ? visualDuplicateStateIndicesInRegistry -> {
                // For resource pack clients, map the claimed proxy states to their visual duplicate
                String randomStringForInvocation = generateRandomStringForMappingIdentifiers();
                for (int i = 0; i < visualDuplicateStateIndicesInRegistry.length; i++) {
                    BlockState visualDuplicateState = VanillaOnlyBlockStateRegistry.get().byId(visualDuplicateStateIndicesInRegistry[i]);
                    BlockState claimedState = VanillaOnlyBlockStateRegistry.get().byId(claimedStates[0][i]);
                    // Block
                    Registry.register(
                        targetRegistry,
                        IdentifierUtil.addPathSuffix(macroKey, "_macro_vd_" + randomStringForInvocation + "_" + (i + 1)),
                        new BlockStateMapping(
                            List.of(AwarenessLevels.RESOURCE_PACK),
                            List.of(claimedState),
                            new DirectMappingStep<>(visualDuplicateState)
                        )
                    );
                    // Item
                    if (createItemMappings) {
                        createItemMappingForBlockStateMapping(
                            claimedState,
                            visualDuplicateState,
                            null,
                            null,
                            AwarenessLevels.RESOURCE_PACK
                        );
                    }
                }
            } : null,
            true,
            isFallback
        );
    }

    public static void createItemMappingForBlockStateMapping(
        BlockState fromState,
        BlockState targetState,
        @Nullable Function<BlockState, @Nullable Item> fromItemFunction,
        @Nullable Function<BlockState, @Nullable Item> targetItemFunction,
        AwarenessLevel awarenessLevel
    ) {
        // Determine the from item
        Item fromItem;
        if (fromItemFunction != null) {
            fromItem = fromItemFunction.apply(fromState);
        } else {
            if (fromState == fromState.getBlock().defaultBlockState()) {
                fromItem = FromToItemRequestBuilderImpl.inferItem(fromState.getBlock());
            } else {
                fromItem = null;
            }
        }
        if (fromItem == null) {
            return;
        }
        // Determine the target item
        Item targetItem;
        if (targetItemFunction != null) {
            targetItem = targetItemFunction.apply(targetState);
        } else {
            targetItem = FromToItemRequestBuilderImpl.inferItem(targetState.getBlock());
        }
        if (targetItem == null) {
            return;
        }
        // Register the mapping
        ItemMappingsImpl.get().addEventInitializer(itemMappingsEvent -> {
            @Nullable BlockItemStateProperties toBlockItemStateProperties;
            Item toItem;
            if (targetState == targetState.getBlock().defaultBlockState() && BlocksWithVisuallyDifferentBlockstates.check(fromState.getBlock())) {
                // Changing the block state component will not help this mapping, and only potentially mess other mappings up
                toBlockItemStateProperties = null;
                toItem = targetItem;
            } else if (!HoneyLevelUtil.haveSameHoneyLevel(targetState, fromState)) {
                // Setting the block state component would show an undesired honey level meter, so we use barrier instead
                toBlockItemStateProperties = null;
                toItem = Items.BARRIER;
            } else {
                toBlockItemStateProperties = new BlockItemStateProperties(targetState.asBlockData().toStates(true));
                toItem = targetItem;
            }
            @Nullable Identifier toItemModel = (awarenessLevel == AwarenessLevels.VANILLA || toItem == fromItem) ? null : fromItem.getDefaultInstance().getOrDefault(DataComponents.ITEM_MODEL, fromItem.keyInItemRegistry);
            if (fromItem != toItem || toItemModel != null || toBlockItemStateProperties != null) {
                itemMappingsEvent.registerNMS(builder -> {
                    builder.awarenessLevel(ClientView.AwarenessLevel.getAll()[awarenessLevel.getId()]);
                    builder.from(fromItem);
                    builder.to(handle -> {
                        if (fromItem != toItem) {
                            ItemMappingUtilitiesNMS.get().setItemWhilePreservingRest(handle, toItem);
                        }
                        ItemStack itemStack = handle.getMutable();
                        if (toItemModel != null) {
                            itemStack.set(DataComponents.ITEM_MODEL, toItemModel);
                        }
                        if (toBlockItemStateProperties != null) {
                            itemStack.set(DataComponents.BLOCK_STATE, toBlockItemStateProperties);
                        }
                    });
                });
            }
        });
    }

    public static String generateRandomStringForMappingIdentifiers() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
