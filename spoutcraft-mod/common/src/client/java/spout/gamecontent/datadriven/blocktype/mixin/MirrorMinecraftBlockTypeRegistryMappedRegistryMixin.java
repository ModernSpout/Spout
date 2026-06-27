package spout.gamecontent.datadriven.blocktype.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import spout.gamecontent.datadriven.blocktype.MirrorMinecraftBlockTypeRegistry;

@Mixin(MappedRegistry.class)
public abstract class MirrorMinecraftBlockTypeRegistryMappedRegistryMixin {

    @Inject(
        method = "register(Lnet/minecraft/resources/ResourceKey;Ljava/lang/Object;Lnet/minecraft/core/RegistrationInfo;)Lnet/minecraft/core/Holder$Reference;",
        at = @At("HEAD")
    )
    private <T> void onRegister(ResourceKey<T> key, T value, RegistrationInfo registrationInfo, CallbackInfoReturnable<Holder.Reference<T>> cir) {
        MirrorMinecraftBlockTypeRegistry.mirrorFromMinecraftToSpoutIfNecessary((Registry<?>) this, key, value, registrationInfo); // Spout - Minecraft registries - Block type - Adapt existing block type registry
    }

}
