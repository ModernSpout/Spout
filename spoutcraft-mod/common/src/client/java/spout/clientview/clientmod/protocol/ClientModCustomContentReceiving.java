package spout.clientview.clientmod.protocol;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntObjectPair;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import spout.gamecontent.datadriven.common.registry.temporarymodification.TemporaryRegistryModifiers;
import spout.gamecontent.datadriven.block.BlockStateRegistryIdMappings;
import spout.clientview.clientmod.registryidmapping.RegistryIdMappings;
import spout.clientview.clientmod.registryidmapping.BlockStateRegistryEntryIdList;
import spout.gamecontent.datadriven.block.ContextAwareBlockPropertiesDecoding;
import spout.gamecontent.datadriven.block.SpoutNonBuiltInBlock;
import spout.util.minecraft.blockstate.BlockStateStringConversion;
import spout.gamecontent.datadriven.item.ContextAwareItemPropertiesDecoding;
import spout.gamecontent.datadriven.item.SpoutNonBuiltInItem;
import spout.clientview.clientmod.registryidmapping.RegistryEntryIdList;
import java.util.function.Supplier;

/**
 * Collects all custom content received.
 */
public final class ClientModCustomContentReceiving {

    private ClientModCustomContentReceiving() {
        throw new UnsupportedOperationException();
    }

    /**
     * The current received content, or null if not currently receiving.
     */
    private static volatile @Nullable ClientModCustomContent receivedContent;
    private static volatile LoadedContentSummary loadedContentSummary = LoadedContentSummary.empty();

    public static void handlePacket(ClientModCustomContentPacketPayload payload) {
        while (SpoutProtocol.getState() != ClientModState.RECEIVED_CUSTOM_CONTENT && !SpoutProtocol.tryChangeState(ClientModState.CLIENT_MOD_DETECTED, ClientModState.RECEIVED_CUSTOM_CONTENT)) {
            Thread.onSpinWait();
        }
        if (receivedContent == null) {
            receivedContent = ClientModCustomContent.createEmpty();
        }
        for (ClientModCustomContentPacketPayload.Element element : payload.getElements()) {
            ClientModCustomContentPacketPayload.Element.Contents contents = element.getContents();
            switch (contents.getType()) {
                case END -> {
                    // Add the received content to registries
                    TemporaryRegistryModifiers.prepareToAddCustomContent();
                    TemporaryRegistryModifiers.addCustomContent(
                        () -> receivedContent.getBlocks().stream().map(keyedValue -> {
                            ResourceKey<Block> key = ResourceKey.create(BuiltInRegistries.BLOCK.key(), keyedValue.identifier());
                            return Pair.of(key, (Supplier<Block>) () -> {
                                SpoutNonBuiltInBlock received = keyedValue.value();
                                ContextAwareBlockPropertiesDecoding.setKey(key);
                                received.initializeValueFromInput(true);
                                ContextAwareItemPropertiesDecoding.clearKey();
                                return received.getValue();
                            });
                        }).toList(),
                        () -> receivedContent.getItems().stream().map(keyedValue -> {
                            ResourceKey<Item> key = ResourceKey.create(BuiltInRegistries.ITEM.key(), keyedValue.identifier());
                            return Pair.of(key, (Supplier<Item>) () -> {
                                SpoutNonBuiltInItem received = keyedValue.value();
                                ContextAwareItemPropertiesDecoding.setKey(key);
                                received.initializeValueFromInput(true);
                                ContextAwareItemPropertiesDecoding.clearKey();
                                return received.getValue();
                            });
                        }).toList()
                    );
                    int blockStateCount = 0;
                    for (var keyedValue : receivedContent.getBlocks()) {
                        Block block = BuiltInRegistries.BLOCK.get(keyedValue.identifier()).orElseThrow().value();
                        for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                            state.initCache();
                            blockStateCount++;
                            if (Block.BLOCK_STATE_REGISTRY.getId(state) == -1) {
                                Block.BLOCK_STATE_REGISTRY.add(state);
                            }
                        }
                    }
                    // Set up registry id mappings where necessary
                    for (RegistryEntryIdList list : receivedContent.getRegistryEntryIdLists()) {
                        Registry<?> registry = BuiltInRegistries.REGISTRY.getValue(list.registryIdentifier());
                        if (registry != null) {
                            addRegistryIdMappings(registry, list);
                        }
                    }
                    // Set up block state mappings where necessary
                    for (BlockStateRegistryEntryIdList list : receivedContent.getBlockStateRegistryEntryIdLists()) {
                        for (IntObjectPair<String> pair : list.entryIds()) {
                            BlockState state = BlockStateStringConversion.blockStateFromString(pair.right());
                            int idOnClient = Block.BLOCK_STATE_REGISTRY.getId(state);
                            int receivedId = pair.leftInt();
                            if (idOnClient != receivedId) {
                                BlockStateRegistryIdMappings.add(idOnClient, receivedId);
                            }
                        }
                    }
                    // Change the state
                    loadedContentSummary = new LoadedContentSummary(
                        receivedContent.getBlocks().size(),
                        receivedContent.getItems().size(),
                        blockStateCount,
                        receivedContent.getRegistryEntryIdLists().size(),
                        receivedContent.getBlockStateRegistryEntryIdLists().size()
                    );
                    SpoutProtocol.changeState(ClientModState.RECEIVED_CUSTOM_CONTENT, ClientModState.ADDED_CUSTOM_CONTENT);
                    receivedContent = null;
                }
                case BLOCK ->
                    receivedContent.getBlocks().add(((ClientModCustomContentPacketPayload.Element.BlockContents) contents).value);
                case ITEM ->
                    receivedContent.getItems().add(((ClientModCustomContentPacketPayload.Element.ItemContents) contents).value);
                case REGISTRY_ENTRY_ID_LIST ->
                    receivedContent.getRegistryEntryIdLists().add(((ClientModCustomContentPacketPayload.Element.RegistryEntryIdListContents) contents).value);
                case BLOCK_STATE_REGISTRY_ENTRY_ID_LIST ->
                    receivedContent.getBlockStateRegistryEntryIdLists().add(((ClientModCustomContentPacketPayload.Element.BlockStateRegistryEntryIdListContents) contents).value);
            }
        }
    }

    private static <T> void addRegistryIdMappings(Registry<T> registry, RegistryEntryIdList list) {
        for (IntObjectPair<Identifier> pair : list.entryIds()) {
            T value = registry.getValue(pair.right());
            int currentId = registry.getId(value);
            if (currentId != pair.leftInt()) {
                RegistryIdMappings.add(registry, currentId, pair.leftInt());
            }
        }
    }

    static void clear() {
        receivedContent = null;
        loadedContentSummary = LoadedContentSummary.empty();
    }

    public static LoadedContentSummary getLoadedContentSummary() {
        return loadedContentSummary;
    }

    public record LoadedContentSummary(
        int blocks,
        int items,
        int blockStates,
        int registryIdLists,
        int blockStateIdLists
    ) {

        private static LoadedContentSummary empty() {
            return new LoadedContentSummary(0, 0, 0, 0, 0);
        }

    }

}
