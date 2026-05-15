package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.Map;

@Mixin(FlowerPotBlock.class)
public interface LazyPottedFlowerPotBlockAccessor {

    @Accessor("POTTED_BY_CONTENT")
    static Map<Block, Block> getPottedByContent() {
        throw new AssertionError();
    }

    @Mutable
    @Accessor("potted")
    void setPotted(Block potted);

}
