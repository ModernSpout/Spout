package spout.clientview.clientmod.protocol.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientConfigurationPacketListenerImpl;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spout.clientview.clientmod.protocol.SpoutProtocol;

@Mixin(ClientConfigurationPacketListenerImpl.class)
public abstract class ClientConfigurationPacketListenerImplMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void markConfigurationStart(
        Minecraft minecraft,
        Connection connection,
        CommonListenerCookie cookie,
        CallbackInfo ci
    ) {
        SpoutProtocol.onConfigurationStart((ClientCommonPacketListenerImpl) (Object) this);
    }

}
