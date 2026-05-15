package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.BlockItem;
import spout.common.moredatadriven.minecraft.type.ItemCodecs;
import spout.client.fabric.moredatadriven.minecraft.type.WithItemCodec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockItem.class)
public abstract class BlockItemCodecMixin implements WithItemCodec {

    @Unique
    @Override
    public MapCodec<? extends BlockItem> codec() {
        return ItemCodecs.BLOCK_ITEM_CODEC;
    }

}
