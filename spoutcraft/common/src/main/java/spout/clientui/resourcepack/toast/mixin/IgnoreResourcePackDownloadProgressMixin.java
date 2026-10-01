package spout.clientui.resourcepack.toast.mixin;

import net.minecraft.client.resources.server.DownloadedPackSource;
import net.minecraft.util.HttpUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import spout.clientview.clientmod.protocol.ClientModStateHandler;
import java.util.OptionalLong;

@Mixin(DownloadedPackSource.class)
public abstract class IgnoreResourcePackDownloadProgressMixin {

    @Inject(
        method = "createDownloadNotifier",
        at = @At("HEAD"),
        cancellable = true
    )
    private void spout$replaceDownloadNotifier(int i, CallbackInfoReturnable<HttpUtil.DownloadProgressListener> cir) {
        if (ClientModStateHandler.getExpectingSpoutResourcePacks()) {
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
