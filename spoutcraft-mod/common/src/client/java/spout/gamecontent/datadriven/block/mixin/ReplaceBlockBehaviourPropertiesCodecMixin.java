package spout.gamecontent.datadriven.block.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spout.gamecontent.datadriven.block.BlockPropertiesCodec;

@Mixin(BlockBehaviour.Properties.class)
public class ReplaceBlockBehaviourPropertiesCodecMixin {

    @Shadow
    @Final
    @Mutable
    public static Codec<BlockBehaviour.Properties> CODEC;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void spout$replaceCodec(CallbackInfo ci) {
        CODEC = BlockPropertiesCodec.CODEC;
    }

}
