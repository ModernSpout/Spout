package spout.server.paper.impl.packetmapping.item.justenoughitemsmod;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jspecify.annotations.Nullable;
import java.util.List;

public class JustEnoughItemsNeoforgePacketSender extends JustEnoughItemsPacketSender<JustEnoughItemsNeoforgePacketSender.NeoforgePayload> {

    public static final Identifier CHANNEL_ID = Identifier.fromNamespaceAndPath("neoforge", "recipe_content");

    private static @Nullable JustEnoughItemsNeoforgePacketSender instance;

    public static JustEnoughItemsNeoforgePacketSender get() {
        if (instance == null) {
            instance = new JustEnoughItemsNeoforgePacketSender();
        }
        return instance;
    }

    @Override
    protected Identifier getChannelId() {
        return CHANNEL_ID;
    }

    @Override
    protected StreamCodec<RegistryFriendlyByteBuf, NeoforgePayload> getStreamCodec() {
        return NeoforgePayload.STREAM_CODEC;
    }

    @Override
    protected NeoforgePayload createPayload() {
        return TODO();
    }

    public static class NeoforgePayload extends Payload {

        public static final StreamCodec<RegistryFriendlyByteBuf, NeoforgePayload> STREAM_CODEC = TODO();

        private final List<> recipeTypes;

        private final List<RecipeHolder<?>> recipes;

    }

}
