package spout.clientview.clientmod.protocol.mixin;

import com.google.common.hash.HashCode;
import net.minecraft.client.resources.server.ServerPackManager;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spout.clientview.clientmod.protocol.ClientModStateHandler;
import java.net.URL;
import java.util.UUID;

/**
 * Makes the client silently accept all resource packs from Spout servers.
 */
@Mixin(ServerPackManager.class)
public abstract class AllowSpoutServerPacksMixin {

    @Shadow
    public abstract void allowServerPacks();

    @Inject(method = "pushPack", at = @At("HEAD"))
    private void autoAccept(UUID uUID, URL uRL, @Nullable HashCode hashCode, CallbackInfo ci) {
        if (ClientModStateHandler.getExpectingSpoutResourcePacks()) {
            this.allowServerPacks();
        }
    }

}
