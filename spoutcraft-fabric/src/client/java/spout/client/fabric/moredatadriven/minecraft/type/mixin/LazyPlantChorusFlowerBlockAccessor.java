package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChorusFlowerBlock.class)
public interface LazyPlantChorusFlowerBlockAccessor {

    @Mutable
    @Accessor("plant")
    void setPlant(Block plant);

}
