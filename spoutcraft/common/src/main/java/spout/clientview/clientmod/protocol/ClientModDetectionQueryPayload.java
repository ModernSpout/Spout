package spout.clientview.clientmod.protocol;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.login.custom.CustomQueryPayload;
import net.minecraft.resources.Identifier;
import spout.branding.SpoutNamespace;

public record ClientModDetectionQueryPayload(
    int protocolMarker,
    int nonce,
    int minProtocolVersion,
    int maxProtocolVersion
) implements CustomQueryPayload {

    public static final Identifier CLIENT_MOD_DETECTION_PACKET_ID = Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, "detect_client_mod");

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

    public static ClientModDetectionQueryPayload parseClientModDetectionQuery(CustomQueryPayload payload) {
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

}
