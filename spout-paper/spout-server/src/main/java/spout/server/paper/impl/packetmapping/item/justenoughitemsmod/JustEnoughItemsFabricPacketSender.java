package spout.server.paper.impl.packetmapping.item.justenoughitemsmod;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

public class JustEnoughItemsFabricPacketSender extends JustEnoughItemsPacketSender<JustEnoughItemsFabricPacketSender.FabricPayload> {

    public static final Identifier CHANNEL_ID = Identifier.fromNamespaceAndPath("fabric", "recipe_sync");

    private static @Nullable JustEnoughItemsFabricPacketSender instance;

    public static JustEnoughItemsFabricPacketSender get() {
        if (instance == null) {
            instance = new JustEnoughItemsFabricPacketSender();
        }
        return instance;
    }

    @Override
    protected Identifier getChannelId() {
        return CHANNEL_ID;
    }

    @Override
    protected StreamCodec<RegistryFriendlyByteBuf, FabricPayload> getStreamCodec() {
        return FabricPayload.STREAM_CODEC;
    }

    @Override
    protected FabricPayload createPayload() {
        return TODO();
    }

    public static class FabricPayload extends Payload {

        public static final StreamCodec<RegistryFriendlyByteBuf, FabricPayload> STREAM_CODEC = TODO();

    }

}
