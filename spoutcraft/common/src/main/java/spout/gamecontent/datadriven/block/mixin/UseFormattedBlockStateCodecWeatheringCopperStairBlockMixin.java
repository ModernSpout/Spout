package spout.gamecontent.datadriven.block.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.WeatheringCopperStairBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spout.gamecontent.datadriven.block.BlockCodecs;

@Mixin(WeatheringCopperStairBlock.class)
public class UseFormattedBlockStateCodecWeatheringCopperStairBlockMixin {

    @Shadow
    @Final
    @Mutable
    public static MapCodec<WeatheringCopperStairBlock> CODEC;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void spout$replaceCodec(CallbackInfo ci) {
        CODEC = BlockCodecs.weatheringCopperStairCodec(WeatheringCopperStairBlock::new);
    }

}
