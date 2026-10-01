package spout.clientview.clientmod.protocol.mixin;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
import net.minecraft.network.protocol.login.custom.CustomQueryPayload;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import spout.clientview.clientmod.protocol.ClientModDetectionQueryPayload;

@Mixin(ClientboundCustomQueryPacket.class)
public abstract class ClientboundCustomQueryPacketMixin {

    @Inject(method = "readPayload", at = @At("HEAD"), cancellable = true)
    private static void readSpoutPayload(
        Identifier identifier,
        FriendlyByteBuf input,
        CallbackInfoReturnable<CustomQueryPayload> cir
    ) {
        if (ClientModDetectionQueryPayload.CLIENT_MOD_DETECTION_PACKET_ID.equals(identifier)) {
            cir.setReturnValue(ClientModDetectionQueryPayload.readClientModDetectionQuery(input));
        }
    }

}
