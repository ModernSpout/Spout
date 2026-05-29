package spout.client.fabric.moredatadriven.minecraft.block.mixin;

import net.minecraft.core.IdMapper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spout.client.fabric.moredatadriven.minecraft.block.RemappedBlockStateRegistry;

@Mixin(Block.class)
public abstract class MapBlockStateRegistryIdsBlockMixin {

    @Shadow
    @Final
    @Mutable
    public static IdMapper<BlockState> BLOCK_STATE_REGISTRY;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void replaceBlockStateRegistry(CallbackInfo ci) {
        BLOCK_STATE_REGISTRY = new RemappedBlockStateRegistry();
    }

}
