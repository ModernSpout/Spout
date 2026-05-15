package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.Map;

@Mixin(InfestedBlock.class)
public interface LazyHostBlockInfestedBlockAccessor {

    @Accessor("BLOCK_BY_HOST_BLOCK")
    static Map<Block, Block> getBlockByHostBlock() {
        throw new AssertionError();
    }

    @Mutable
    @Accessor("hostBlock")
    void setHostBlock(Block hostBlock);

}
