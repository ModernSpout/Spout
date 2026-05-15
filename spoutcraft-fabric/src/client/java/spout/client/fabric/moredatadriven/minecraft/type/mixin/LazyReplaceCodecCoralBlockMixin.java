package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.CoralBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spout.common.moredatadriven.minecraft.type.BlockCodecs;

@Mixin(CoralBlock.class)
public abstract class LazyReplaceCodecCoralBlockMixin {

    @Shadow
    @Final
    @Mutable
    public static MapCodec<CoralBlock> CODEC;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void spout$replaceCodec(CallbackInfo ci) {
        CODEC = BlockCodecs.coralCodec(CoralBlock::new);
    }

}
