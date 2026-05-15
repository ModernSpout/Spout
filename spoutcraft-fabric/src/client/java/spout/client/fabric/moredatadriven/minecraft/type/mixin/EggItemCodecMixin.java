package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.EggItem;
import spout.common.moredatadriven.minecraft.type.ItemCodecs;
import spout.client.fabric.moredatadriven.minecraft.type.WithItemCodec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EggItem.class)
public abstract class EggItemCodecMixin implements WithItemCodec {

    @Unique
    @Override
    public MapCodec<? extends EggItem> codec() {
        return ItemCodecs.EGG_ITEM_CODEC;
    }

}
