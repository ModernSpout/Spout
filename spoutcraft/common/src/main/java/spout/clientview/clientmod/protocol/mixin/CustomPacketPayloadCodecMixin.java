package spout.clientview.clientmod.protocol.mixin;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import spout.clientview.clientmod.protocol.ClientModCustomContentPacketPayload;

@Mixin(targets = "net.minecraft.network.protocol.common.custom.CustomPacketPayload$1")
public abstract class CustomPacketPayloadCodecMixin<B extends FriendlyByteBuf> {

    @Inject(method = "findCodec", at = @At("HEAD"), cancellable = true)
    private void findSpoutCodec(
        Identifier identifier,
        CallbackInfoReturnable<StreamCodec<? super B, ? extends CustomPacketPayload>> cir
    ) {
        if (ClientModCustomContentPacketPayload.TYPE.id().equals(identifier)) {
            cir.setReturnValue(ClientModCustomContentPacketPayload.STREAM_CODEC);
        }
    }

}
