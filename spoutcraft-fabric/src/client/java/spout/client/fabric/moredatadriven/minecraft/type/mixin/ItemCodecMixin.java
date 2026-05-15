package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.Item;
import spout.common.moredatadriven.minecraft.type.ItemCodecs;
import spout.client.fabric.moredatadriven.minecraft.type.WithItemCodec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.class)
public abstract class ItemCodecMixin implements WithItemCodec {

    @Unique
    @Override
    public MapCodec<? extends Item> codec() {
        return ItemCodecs.ITEM_CODEC;
    }

}
