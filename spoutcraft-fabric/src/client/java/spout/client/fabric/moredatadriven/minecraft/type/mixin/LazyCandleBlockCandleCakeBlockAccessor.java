package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.Map;

@Mixin(CandleCakeBlock.class)
public interface LazyCandleBlockCandleCakeBlockAccessor {

    @Accessor("BY_CANDLE")
    static Map<CandleBlock, CandleCakeBlock> getByCandle() {
        throw new AssertionError();
    }

    @Mutable
    @Accessor("candleBlock")
    void setCandleBlock(CandleBlock candleBlock);

}
