package spout.gamecontent.datadriven.block.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import spout.gamecontent.datadriven.block.BlockCodecAccessor;

/**
 * Mixin applying {@link BlockCodecAccessor}.
 */
@Mixin(Block.class)
public abstract class BlockCodecAccessorBlockMixin implements BlockCodecAccessor {

    @Invoker("codec")
    public abstract MapCodec<? extends Block> invokeCodec();

    @Override
    public MapCodec<? extends Block> spout$codec() {
        return this.invokeCodec();
    }

}
