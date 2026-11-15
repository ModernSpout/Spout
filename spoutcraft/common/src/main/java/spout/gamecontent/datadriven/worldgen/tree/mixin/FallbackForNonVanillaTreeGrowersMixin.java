package spout.gamecontent.datadriven.worldgen.tree.mixin;

import net.minecraft.world.level.block.grower.TreeGrower;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Map;
import java.util.function.Function;

@Mixin(TreeGrower.class)
public abstract class FallbackForNonVanillaTreeGrowersMixin {

    @Shadow
    @Final
    private static Map<String, TreeGrower> GROWERS;

    @ModifyArg(
        method = "<clinit>",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/serialization/Codec;stringResolver(Ljava/util/function/Function;Ljava/util/function/Function;)Lcom/mojang/serialization/Codec;"
        ),
        index = 1
    )
    private static Function<String, TreeGrower> modifyDecoder(
        Function<String, TreeGrower> original
    ) {
        return name -> GROWERS.getOrDefault(name, GROWERS.get("oak"));
    }
}
