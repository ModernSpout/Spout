package spout.gamecontent.datadriven.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spout.gamecontent.datadriven.common.registry.bootstrap.SpoutBuiltInDataDrivenRegistryBootstrap;

/**
 * Extends {@link BuiltInRegistries#bootStrap()}
 * to include the {@link BuiltInSpoutMoreDataDrivenRegistries}.
 */
@Mixin(BuiltInRegistries.class)
public abstract class IncludeSpoutBootstrapBuiltInRegistriesMixin {

    @Inject(
        method = "createContents",
        at = @At("HEAD")
    )
    private static void onCreateContents(CallbackInfo ci) {
        SpoutBuiltInDataDrivenRegistryBootstrap.bootstrap();
    }

}
