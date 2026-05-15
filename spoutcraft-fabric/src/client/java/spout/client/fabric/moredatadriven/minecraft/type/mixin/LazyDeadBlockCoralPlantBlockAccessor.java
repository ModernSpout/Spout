package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CoralPlantBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CoralPlantBlock.class)
public interface LazyDeadBlockCoralPlantBlockAccessor {

    @Mutable
    @Accessor("deadBlock")
    void setDeadBlock(Block deadBlock);

}
