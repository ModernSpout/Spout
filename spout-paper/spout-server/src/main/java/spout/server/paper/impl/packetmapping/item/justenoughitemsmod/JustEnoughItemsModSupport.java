package spout.server.paper.impl.packetmapping.item.justenoughitemsmod;

import net.minecraft.resources.Identifier;

/**
 * Provides support for the JustEnoughItems (JEI) client mod.
 */
public final class JustEnoughItemsModSupport {

    private JustEnoughItemsModSupport() {
        throw new UnsupportedOperationException();
    }

    private static final Identifier PACKET_ID = Identifier.fromNamespaceAndPath("neoforge", "recipe_content");
    public static final CustomPacketPayload.Type<ClientModCustomContentPacketPayload> TYPE = new CustomPacketPayload.Type<>(PACKET_ID);
    public static final StreamCodec<FriendlyByteBuf, ClientModCustomContentPacketPayload> STREAM_CODEC = CustomPacketPayload.codec(ClientModCustomContentPacketPayload::write, ClientModCustomContentPacketPayload::new);
    public static final CustomPacketPayload.TypeAndCodec<FriendlyByteBuf, ?> TYPE_AND_CODEC = new CustomPacketPayload.TypeAndCodec<>(TYPE, ClientModCustomContentPacketPayload.STREAM_CODEC);

    public static void initializeSupport() {

    }
}
