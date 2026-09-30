package spout.clientview.clientmod.protocol.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spout.clientview.clientmod.protocol.SpoutProtocol;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {

    @Inject(method = "clearLevel", at = @At("HEAD"))
    private void removeSpoutContentBeforeClearingLevel(CallbackInfo ci) {
        SpoutProtocol.onDisconnect();
    }

}
