package spout.server.paper.impl.packetmapping.item.justenoughitemsmod;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.DiscardedPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jspecify.annotations.Nullable;

public abstract class JustEnoughItemsPacketSender<P extends JustEnoughItemsPacketSender.Payload> {

    private @Nullable P cachedPayload; // While changes may happen afterward, it's not really worth the CPU time needed to re-compute it every time

    protected abstract Identifier getChannelId();

    protected abstract StreamCodec<RegistryFriendlyByteBuf, P> getStreamCodec();

    protected abstract P createPayload();

    public void send(ServerPlayer player) {
        ByteBuf buf = Unpooled.buffer();
        if (this.cachedPayload == null) {
            this.cachedPayload = this.createPayload();
        }
        this.getStreamCodec().encode(new RegistryFriendlyByteBuf(buf, MinecraftServer.getServer().registryAccess()), this.cachedPayload);
        byte[] message = new byte[buf.readableBytes()];
        buf.readBytes(message);
        player.connection.send(new ClientboundCustomPayloadPacket(new DiscardedPayload(this.getChannelId(), message)));
    }

    public static abstract class Payload {



    }
    
}
