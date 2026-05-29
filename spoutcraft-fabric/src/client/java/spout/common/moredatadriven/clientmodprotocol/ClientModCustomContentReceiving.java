package spout.common.moredatadriven.clientmodprotocol;

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
import spout.client.fabric.clientview.ClientModState;
import spout.client.fabric.clientview.SpoutProtocol;
import spout.client.fabric.moredatadriven.TemporaryRegistryModifiers;
import spout.client.fabric.moredatadriven.minecraft.block.BlockStateRegistryIdMappings;
import spout.client.fabric.moredatadriven.minecraft.block.RemappedBlockStateRegistry;
import spout.client.fabric.moredatadriven.minecraft.registry.RegistryIdMappings;
import spout.common.moredatadriven.minecraft.block.BlockStateRegistryEntryIdList;
import spout.common.moredatadriven.minecraft.block.ContextAwareBlockPropertiesDecoding;
import spout.common.moredatadriven.minecraft.block.SpoutNonBuiltInBlock;
import spout.common.moredatadriven.minecraft.common.subtypes.BlockStateStringConversion;
import spout.common.moredatadriven.minecraft.item.ContextAwareItemPropertiesDecoding;
import spout.common.moredatadriven.minecraft.item.SpoutNonBuiltInItem;
import spout.common.moredatadriven.minecraft.registry.RegistryEntryIdList;
import spout.common.protocol.ClientModCustomContent;
import spout.common.protocol.ClientModCustomContentPacketPayload;
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
                    // Set up registry id mappings where necessary
                    for (RegistryEntryIdList list : receivedContent.getRegistryEntryIdLists()) {
                        Registry registry = BuiltInRegistries.REGISTRY.getValue(list.registryIdentifier());
                        if (registry != null) {
                            for (IntObjectPair<Identifier> pair : list.entryIds()) {
                                int currentId = registry.getId(registry.getValue(pair.right()));
                                if (currentId != pair.leftInt()) {
                                    RegistryIdMappings.add(registry, currentId, pair.leftInt());
                                }
                            }
                        }
                    }
                    // Set up block state mappings where necessary
                    for (BlockStateRegistryEntryIdList list : receivedContent.getBlockStateRegistryEntryIdLists()) {
                        for (IntObjectPair<String> pair : list.entryIds()) {
                            BlockState state = BlockStateStringConversion.blockStateFromString(pair.right());
                            int idOnClient = ((RemappedBlockStateRegistry) Block.BLOCK_STATE_REGISTRY).getIdUnmapped(state);
                            int receivedId = pair.leftInt();
                            if (idOnClient != receivedId) {
                                BlockStateRegistryIdMappings.add(idOnClient, receivedId);
                            }
                        }
                    }
                    // Change the state
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

}
