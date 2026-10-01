package spout.clientview.clientmod.protocol;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntObjectPair;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ServerboundCustomQueryAnswerPacket;
import net.minecraft.network.protocol.login.custom.CustomQueryAnswerPayload;
import net.minecraft.network.protocol.login.custom.CustomQueryPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import spout.clientui.resourcepack.loadingoverlay.SwitchOverlayStyle;
import spout.clientview.clientmod.protocol.mixin.ClientCommonPacketListenerImplAccessor;
import spout.clientview.clientmod.registryidmapping.BlockStateRegistryEntryIdList;
import spout.clientview.clientmod.registryidmapping.RegistryEntryIdList;
import spout.clientview.clientmod.registryidmapping.RegistryIdMappings;
import spout.gamecontent.datadriven.block.BlockStateRegistryIdMappings;
import spout.gamecontent.datadriven.block.ContextAwareBlockPropertiesDecoding;
import spout.gamecontent.datadriven.block.SpoutNonBuiltInBlock;
import spout.gamecontent.datadriven.common.registry.temporarymodification.TemporaryRegistryModifiers;
import spout.gamecontent.datadriven.item.ContextAwareItemPropertiesDecoding;
import spout.gamecontent.datadriven.item.SpoutNonBuiltInItem;
import spout.util.minecraft.blockstate.BlockStateStringConversion;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Handles the detection of the client mod by the server,
 * by interpreting related events and packets
 * and taking the appropriate action for each.
 *
 * <p>
 * All data is accessed with plain memory semantics, guarded by {@link #lock}.
 * </p>
 */
public final class ClientModStateHandler {

    private ClientModStateHandler() {
        throw new UnsupportedOperationException();
    }

    private static final int MIN_PROTOCOL_VERSION = 5;
    private static final int MAX_PROTOCOL_VERSION = 6;

    private static final ReentrantLock lock = new ReentrantLock();

    private static ClientModState state = ClientModState.IDLE;

    private static int serverMinProtocolVersion = -1;
    private static int serverMaxProtocolVersion = -1;
    private static int selectedProtocolVersion = -1;

    private static boolean expectingSpoutResourcePacks;

    /**
     * The current received content, or null if not received any custom content packets.
     */
    private static @Nullable ClientModCustomContent receivedContent;

    private static Summary.LoadedContent loadedContentSummary = new Summary.LoadedContent();

    private static void setVisualsToDefault() {
        SwitchOverlayStyle.setMojang();
    }

    private static void setVisualsToSpout() {
        SwitchOverlayStyle.setSpout();
    }

    private static void deleteAllSessionInformation() {
        serverMinProtocolVersion = -1;
        serverMaxProtocolVersion = -1;
        selectedProtocolVersion = -1;
    }

    private static void unloadCustomContent() {
        // Clear the received content, to reclaim memory
        receivedContent = null;
        // Reset the loaded content summary
        loadedContentSummary.reset();
        // Remove custom content
        if (state == ClientModState.ADDED_CUSTOM_CONTENT) {
            TemporaryRegistryModifiers.removeCustomContent();
            RegistryIdMappings.clear();
            BlockStateRegistryIdMappings.clear();
        }
    }

    private static void enableExpectingSpoutResourcePacks(ClientCommonPacketListenerImpl handler) {
        expectingSpoutResourcePacks = true;
        // Force resource pack accepting
        ClientCommonPacketListenerImplAccessor accessor = (ClientCommonPacketListenerImplAccessor) handler;
        ServerData serverData = accessor.getServerData();
        serverData.setResourcePackStatus(ServerData.ServerPackStatus.ENABLED);
    }

    private static void disableExpectingSpoutResourcePacks() {
        expectingSpoutResourcePacks = false;
    }

    /**
     * @return An answer payload for a received client mod detection packet,
     * or null if the packet cannot be interpreted,
     * paired with the selected protocol version, or -1 if no protocol was established.
     */
    private static Pair<@Nullable CustomQueryAnswerPayload, Integer> determineClientModDetectionPacketResult(ClientModDetectionQueryPayload payload) {
        // First, the server will send a 0
        // If not, then there must be a protocol difference that we are unaware of
        if (payload.protocolMarker() != 0
            || payload.minProtocolVersion() < 1
            || payload.maxProtocolVersion() < payload.minProtocolVersion()) {
            return Pair.of(null, -1);
        }
        // The best protocol version is the highest supported by both client and server
        int bestProtocolVersion = Math.min(
            payload.maxProtocolVersion(),
            MAX_PROTOCOL_VERSION
        );
        int selectedProtocolVersion = -1;
        if (bestProtocolVersion >= payload.minProtocolVersion() && bestProtocolVersion >= MIN_PROTOCOL_VERSION) {
            selectedProtocolVersion = bestProtocolVersion;
        }
        final int responseProtocolVersion = selectedProtocolVersion;
        return Pair.of(output -> {
            output.writeVarInt(0);
            output.writeVarInt(payload.nonce());
            output.writeVarInt(responseProtocolVersion);
        }, selectedProtocolVersion);
    }

    private static void processCustomContentEnd() {
        // Add the received content to internal data structures
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
            Registry<?> registry = BuiltInRegistries.REGISTRY.getValue(list.registryIdentifier());
            if (registry != null) {
                for (IntObjectPair<Identifier> pair : list.entryIds()) {
                    int currentId = ((Registry) registry).getId(registry.getValue(pair.right()));
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
                int idOnClient = Block.BLOCK_STATE_REGISTRY.getId(state);
                int receivedId = pair.leftInt();
                if (idOnClient != receivedId) {
                    BlockStateRegistryIdMappings.add(idOnClient, receivedId);
                }
            }
        }
        // Update the diagnostic summary
        loadedContentSummary.setRegistryEntryIdLists(receivedContent.getRegistryEntryIdLists().size());
        loadedContentSummary.setBlockStateRegistryEntryIdLists(receivedContent.getBlockStateRegistryEntryIdLists().size());
        // Change the state
        state = ClientModState.ADDED_CUSTOM_CONTENT;
        // Clear the received content, to reclaim memory
        receivedContent = null;
    }

    public static void onLoginStart() {
        lock.lock();
        try {
            if (state != ClientModState.IDLE) {
                setVisualsToDefault();
                deleteAllSessionInformation();
                disableExpectingSpoutResourcePacks();
                unloadCustomContent();
            }
            state = ClientModState.LOGIN_PHASE_STARTED;
        } finally {
            lock.unlock();
        }
    }

    public static void onReceiveClientModDetectionPacket(ClientboundCustomQueryPacket packet, Connection connection) {
        lock.lock();
        try {
            if (state == ClientModState.CLIENT_MOD_DETECTED) {
                // Protocol already established, ignore this packet
                return;
            }
            if (state != ClientModState.LOGIN_PHASE_STARTED) {
                // Wrong moment to establish protocol, ignore this packet
                return;
            }
            int transactionId = packet.transactionId();
            CustomQueryPayload payload = packet.payload();
            ClientModDetectionQueryPayload parsedPayload = ClientModDetectionQueryPayload.parseClientModDetectionQuery(payload);
            serverMinProtocolVersion = parsedPayload.minProtocolVersion();
            serverMaxProtocolVersion = parsedPayload.maxProtocolVersion();
            Pair<@Nullable CustomQueryAnswerPayload, Integer> result = determineClientModDetectionPacketResult(parsedPayload);
            selectedProtocolVersion = result.second();
            if (result.second() != -1) {
                SwitchOverlayStyle.setSpout();
                state = ClientModState.CLIENT_MOD_DETECTED;
            }
            if (result.first() != null) {
                ServerboundCustomQueryAnswerPacket answerPacket = new ServerboundCustomQueryAnswerPacket(transactionId, result.first());
                // Send the response while the lock is held to force any other events to back off
                connection.send(answerPacket);
            }
        } finally {
            lock.unlock();
        }
    }

    public static void onConfigurationStart(ClientCommonPacketListenerImpl handler) {
        lock.lock();
        try {
            if (state == ClientModState.CLIENT_MOD_DETECTED) {
                enableExpectingSpoutResourcePacks(handler);
            } else if (state == ClientModState.LOGIN_PHASE_STARTED) {
                state = ClientModState.CLIENT_MOD_NOT_DETECTED;
            } else {
                throw new IllegalStateException("Invalid state during configuration start: " + state);
            }
        } finally {
            lock.unlock();
        }
    }

    public static void onReceiveClientModCustomContentPacket(ClientModCustomContentPacketPayload payload) {
        lock.lock();
        try {
            if (state == ClientModState.CLIENT_MOD_DETECTED) {
                // Start receiving custom content
                receivedContent = ClientModCustomContent.createEmpty();
                state = ClientModState.RECEIVED_SOME_CUSTOM_CONTENT;
            } else if (state != ClientModState.RECEIVED_SOME_CUSTOM_CONTENT) {
                // Wrong moment to receive custom content, ignore this packet
                return;
            }
            // Process the payload
            for (ClientModCustomContentPacketPayload.Element element : payload.getElements()) {
                ClientModCustomContentPacketPayload.Element.Contents contents = element.getContents();
                switch (contents.getType()) {
                    case END -> processCustomContentEnd();
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
        } finally {
            lock.unlock();
        }
    }

    public static void onClearLevel() {
        lock.lock();
        try {
            if (state != ClientModState.IDLE) {
                setVisualsToDefault();
                deleteAllSessionInformation();
                disableExpectingSpoutResourcePacks();
                unloadCustomContent();
                state = ClientModState.IDLE;
            }
        } finally {
            lock.unlock();
        }
    }

    public static ClientModState getState() {
        lock.lock();
        try {
            return state;
        } finally {
            lock.unlock();
        }
    }

    public static boolean getExpectingSpoutResourcePacks() {
        lock.lock();
        try {
            return expectingSpoutResourcePacks;
        } finally {
            lock.unlock();
        }
    }

    /**
     * @return A coherent view of the internal state.
     */
    public static Summary getSummary() {
        lock.lock();
        try {
            return new Summary(
                state,
                serverMinProtocolVersion,
                serverMaxProtocolVersion,
                selectedProtocolVersion,
                new Summary.LoadedContent(loadedContentSummary)
            );
        } finally {
            lock.unlock();
        }
    }

    public static void updateLoadedContentSummary(Consumer<Summary.LoadedContent> consumer) {
        lock.lock();
        try {
            consumer.accept(loadedContentSummary);
        } finally {
            lock.unlock();
        }
    }

    public record Summary(
        ClientModState state,
        int serverMinProtocolVersion,
        int serverMaxProtocolVersion,
        int selectedProtocolVersion,
        ImmutableLoadedContent loadedContent
    ) {

        public interface ImmutableLoadedContent {

            int getBlocks();

            int getItems();

            int getBlockStates();

            int getRegistryEntryIdLists();

            int getBlockStateRegistryEntryIdLists();

        }

        public static final class LoadedContent implements ImmutableLoadedContent {

            private int blocks;
            private int items;
            private int blockStates;
            private int registryEntryIdLists;
            private int blockStateRegistryEntryIdLists;

            private LoadedContent() {
            }

            private LoadedContent(LoadedContent original) {
                this.blocks = original.blocks;
                this.items = original.items;
                this.blockStates = original.blockStates;
                this.registryEntryIdLists = original.registryEntryIdLists;
                this.blockStateRegistryEntryIdLists = original.blockStateRegistryEntryIdLists;
            }

            @Override
            public int getBlocks() {
                return this.blocks;
            }

            public void setBlocks(int blocks) {
                this.blocks = blocks;
            }

            @Override
            public int getItems() {
                return this.items;
            }

            public void setItems(int items) {
                this.items = items;
            }

            @Override
            public int getBlockStates() {
                return this.blockStates;
            }

            public void setBlockStates(int blockStates) {
                this.blockStates = blockStates;
            }

            @Override
            public int getRegistryEntryIdLists() {
                return this.registryEntryIdLists;
            }

            public void setRegistryEntryIdLists(int registryEntryIdLists) {
                this.registryEntryIdLists = registryEntryIdLists;
            }

            @Override
            public int getBlockStateRegistryEntryIdLists() {
                return this.blockStateRegistryEntryIdLists;
            }

            public void setBlockStateRegistryEntryIdLists(int blockStateRegistryEntryIdLists) {
                this.blockStateRegistryEntryIdLists = blockStateRegistryEntryIdLists;
            }

            private void reset() {
                this.blocks = 0;
                this.items = 0;
                this.blockStates = 0;
                this.registryEntryIdLists = 0;
                this.blockStateRegistryEntryIdLists = 0;
            }

        }

    }

}
