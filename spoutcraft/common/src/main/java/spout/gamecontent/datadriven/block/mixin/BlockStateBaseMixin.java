package spout.gamecontent.datadriven.block.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import spout.gamecontent.datadriven.block.OverrideSuffocationCheckWhilePlayerMoving;

/**
 * @see OverrideSuffocationCheckWhilePlayerMoving
 */
@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

    @Inject(
        method = "Lnet/minecraft/world/level/block/state/BlockBehaviour$BlockStateBase;isSuffocating(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z",
        at = @At("HEAD"),
        cancellable = true
    )
    private void isSuffocating(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockBehaviour.BlockStateBase base = (BlockBehaviour.BlockStateBase) (Object) this;
        if (!base.getVisualShape(level, pos, CollisionContext.empty()).equals(Shapes.block()) && OverrideSuffocationCheckWhilePlayerMoving.isMoving && !base.getBlock().builtInRegistryHolder().key().identifier().getNamespace().equals(Identifier.DEFAULT_NAMESPACE)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
        method = "Lnet/minecraft/world/level/block/state/BlockBehaviour$BlockStateBase;isViewBlocking(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/AABB;)Z",
        at = @At("HEAD"),
        cancellable = true
    )
    private void isViewBlocking(BlockGetter level, BlockPos blockPos, AABB nearPlaneBox, CallbackInfoReturnable<Boolean> cir) {
        BlockBehaviour.BlockStateBase base = (BlockBehaviour.BlockStateBase) (Object) this;
        if (!base.getVisualShape(level, blockPos, CollisionContext.empty()).equals(Shapes.block()) && !base.getBlock().builtInRegistryHolder().key().identifier().getNamespace().equals(Identifier.DEFAULT_NAMESPACE)) {
            cir.setReturnValue(false);
        }
    }

}
