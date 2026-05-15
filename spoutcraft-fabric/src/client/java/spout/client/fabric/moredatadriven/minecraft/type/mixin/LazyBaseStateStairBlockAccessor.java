package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StairBlock.class)
public interface LazyBaseStateStairBlockAccessor {

    @Mutable
    @Accessor("base")
    void setBase(Block base);

    @Mutable
    @Accessor("baseState")
    void setBaseState(BlockState baseState);

}
