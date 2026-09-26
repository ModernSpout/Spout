package spout.clientui.resourcepack.toast.mixin;

import net.minecraft.client.resources.server.DownloadedPackSource;
import net.minecraft.util.HttpUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import spout.clientview.clientmod.protocol.ClientModState;
import spout.clientview.clientmod.protocol.SpoutProtocol;
import java.util.OptionalLong;

@Mixin(DownloadedPackSource.class)
public abstract class IgnoreResourcePackDownloadProgressMixin {

    @Inject(
        method = "createDownloadNotifier",
        at = @At("HEAD"),
        cancellable = true
    )
    private void spout$replaceDownloadNotifier(int i, CallbackInfoReturnable<HttpUtil.DownloadProgressListener> cir) {
        // Don't show notifier while on Spout servers // TODO make configurable
        ClientModState state = SpoutProtocol.getState();
        if (state == ClientModState.CLIENT_MOD_DETECTED || state == ClientModState.RECEIVED_CUSTOM_CONTENT || state == ClientModState.ADDED_CUSTOM_CONTENT) {
            cir.setReturnValue(new HttpUtil.DownloadProgressListener() {

                @Override
                public void requestStart() {
                }

                @Override
                public void downloadStart(OptionalLong optionalLong) {
                }

                @Override
                public void downloadedBytes(long l) {
                }

                @Override
                public void requestFinished(final boolean bl) {
                }

            });
        }
    }

}
