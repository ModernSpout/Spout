package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CoralFanBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CoralFanBlock.class)
public interface LazyDeadBlockCoralFanBlockAccessor {

    @Mutable
    @Accessor("deadBlock")
    void setDeadBlock(Block deadBlock);

}
