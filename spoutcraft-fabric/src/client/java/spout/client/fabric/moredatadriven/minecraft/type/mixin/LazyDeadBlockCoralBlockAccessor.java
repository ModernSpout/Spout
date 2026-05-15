package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CoralBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CoralBlock.class)
public interface LazyDeadBlockCoralBlockAccessor {

    @Mutable
    @Accessor("deadBlock")
    void setDeadBlock(Block deadBlock);

}
