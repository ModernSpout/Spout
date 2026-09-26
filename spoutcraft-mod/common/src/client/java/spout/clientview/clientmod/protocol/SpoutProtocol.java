package spout.clientview.clientmod.protocol;

import io.netty.buffer.Unpooled;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.login.custom.CustomQueryAnswerPayload;
import net.minecraft.network.protocol.login.custom.CustomQueryPayload;
import net.minecraft.resources.Identifier;
import spout.clientview.clientmod.protocol.mixin.ClientCommonPacketListenerImplAccessor;
import spout.gamecontent.datadriven.common.registry.temporarymodification.TemporaryRegistryModifiers;
import spout.gamecontent.datadriven.block.BlockStateRegistryIdMappings;
import spout.clientview.clientmod.registryidmapping.RegistryIdMappings;
import spout.clientui.resourcepack.loadingoverlay.SwitchOverlayStyle;
import spout.branding.SpoutNamespace;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Handles the detection of the client mod by the server,
 * and the interpretation of related packets.
 */
public final class SpoutProtocol {

    public static final Identifier CLIENT_MOD_DETECTION_PACKET_ID = Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, "detect_client_mod");
    private static final int MIN_PROTOCOL_VERSION = 5;
    private static final int MAX_PROTOCOL_VERSION = 5;

    private static final AtomicReference<ClientModState> state = new AtomicReference<>(ClientModState.IDLE);
    private static volatile int serverMinProtocolVersion = -1;
    private static volatile int serverMaxProtocolVersion = -1;
    private static volatile int selectedProtocolVersion = -1;

    private SpoutProtocol() {
        throw new UnsupportedOperationException();
    }

    public static void onLoginStart() {
        SpoutProtocol.onDisconnect();
        state.set(ClientModState.HANDSHAKE_STARTED);
    }

    public static ClientModDetectionQueryPayload readClientModDetectionQuery(FriendlyByteBuf input) {
        ClientModDetectionQueryPayload payload = new ClientModDetectionQueryPayload(
            input.readVarInt(),
            input.readVarInt(),
            input.readVarInt(),
            input.readVarInt()
        );
        input.skipBytes(input.readableBytes());
        return payload;
    }

    public static CustomQueryAnswerPayload createClientModDetectionAnswer(CustomQueryPayload payload) {
        ClientModDetectionQueryPayload detectionPayload = getClientModDetectionQuery(payload);
        // First, the server will send a 0. If not, then there must be a protocol difference that we are unaware of.
        if (detectionPayload.protocolMarker != 0
            || detectionPayload.minProtocolVersion < 1
            || detectionPayload.maxProtocolVersion < detectionPayload.minProtocolVersion) {
            return null;
        }
        // The best protocol version is the highest supported by both client and server.
        int bestProtocolVersion = Math.min(
            detectionPayload.maxProtocolVersion,
            MAX_PROTOCOL_VERSION
        );
        int responseProtocolVersion =
            bestProtocolVersion >= detectionPayload.minProtocolVersion
                && bestProtocolVersion == MIN_PROTOCOL_VERSION ? bestProtocolVersion : -1;
        if (responseProtocolVersion != -1) {
            changeState(ClientModState.HANDSHAKE_STARTED, ClientModState.CLIENT_MOD_DETECTED);
            SwitchOverlayStyle.setSpout();
        }
        serverMinProtocolVersion = detectionPayload.minProtocolVersion;
        serverMaxProtocolVersion = detectionPayload.maxProtocolVersion;
        selectedProtocolVersion = responseProtocolVersion;
        return output -> {
            output.writeVarInt(0);
            output.writeVarInt(detectionPayload.nonce);
            output.writeVarInt(responseProtocolVersion);
        };
    }

    private static ClientModDetectionQueryPayload getClientModDetectionQuery(CustomQueryPayload payload) {
        if (payload instanceof ClientModDetectionQueryPayload detectionPayload) {
            return detectionPayload;
        }
        // Fixes Spout login when Fabric API is present; fabric-networking-api-v1 wraps login payloads in a generic buffer payload.
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        try {
            payload.write(buffer);
            return readClientModDetectionQuery(buffer);
        } finally {
            buffer.release();
        }
    }

    public static void onConfigurationStart(ClientCommonPacketListenerImpl handler) {
        if (SpoutProtocol.getState() == ClientModState.CLIENT_MOD_DETECTED) {
            // Force accepting of packs.
            ClientCommonPacketListenerImplAccessor accessor = (ClientCommonPacketListenerImplAccessor) handler;
            ServerData serverData = accessor.getServerData();
            serverData.setResourcePackStatus(ServerData.ServerPackStatus.ENABLED);
            return;
        }
        SpoutProtocol.tryChangeState(ClientModState.HANDSHAKE_STARTED, ClientModState.CLIENT_MOD_NOT_DETECTED);
    }

    public static void onDisconnect() {
        SwitchOverlayStyle.setMojang();
        ClientModState oldState = state.getAndSet(ClientModState.IDLE);
        serverMinProtocolVersion = -1;
        serverMaxProtocolVersion = -1;
        selectedProtocolVersion = -1;
        ClientModCustomContentReceiving.clear();
        if (oldState == ClientModState.ADDED_CUSTOM_CONTENT) {
            TemporaryRegistryModifiers.removeCustomContent();
            RegistryIdMappings.clear();
            BlockStateRegistryIdMappings.clear();
        }
    }

    public static boolean tryChangeState(ClientModState oldState, ClientModState newState) {
        return state.compareAndSet(oldState, newState);
    }

    public static void changeState(ClientModState oldState, ClientModState newState) {
        while (!tryChangeState(oldState, newState)) {
            Thread.onSpinWait();
        }
    }

    public static ClientModState getState() {
        return state.get();
    }

    public static int getServerMinProtocolVersion() {
        return serverMinProtocolVersion;
    }

    public static int getServerMaxProtocolVersion() {
        return serverMaxProtocolVersion;
    }

    public static int getSelectedProtocolVersion() {
        return selectedProtocolVersion;
    }

    public record ClientModDetectionQueryPayload(
        int protocolMarker,
        int nonce,
        int minProtocolVersion,
        int maxProtocolVersion
    ) implements CustomQueryPayload {

        @Override
        public Identifier id() {
            return CLIENT_MOD_DETECTION_PACKET_ID;
        }

        @Override
        public void write(FriendlyByteBuf output) {
            output.writeVarInt(this.protocolMarker);
            output.writeVarInt(this.nonce);
            output.writeVarInt(this.minProtocolVersion);
            output.writeVarInt(this.maxProtocolVersion);
        }

    }

}
