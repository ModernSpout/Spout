package spout.clientview.clientmod.protocol.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spout.clientview.clientmod.protocol.ClientModCustomContentPacketPayload;
import spout.clientview.clientmod.protocol.ClientModCustomContentReceiving;

@Mixin(ClientCommonPacketListenerImpl.class)
public abstract class ClientCommonPacketListenerImplMixin {

    @Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
    private void handleSpoutCustomPayload(ClientboundCustomPayloadPacket packet, CallbackInfo ci) {
        CustomPacketPayload payload = packet.payload();
        if (payload instanceof ClientModCustomContentPacketPayload spoutPayload) {
            Minecraft client = Minecraft.getInstance();
            if (client.isSameThread()) {
                ClientModCustomContentReceiving.handlePacket(spoutPayload);
            } else {
                client.submit(() -> ClientModCustomContentReceiving.handlePacket(spoutPayload)).join();
            }
            ci.cancel();
        }
    }

}
