package spout.clientview.packetmapping.itemstack.builtin.changeonlyitem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Item;
import spout.clientview.packetmapping.itemstack.apply.ItemStackMappingHandle;
import spout.util.mapping.handle.MappingStep;

/**
 * A {@link ItemMappingsStep} that always maps to a specific {@link Item}
 * while {@linkplain ChangeOnlyItemUtility#changeOnlyItem} preserving other properties}.
 */
public record ChangeOnlyItemItemStackMappingStep(Item to) implements MappingStep<ItemStackMappingHandle> {

    public static Codec<ChangeOnlyItemItemStackMappingStep> codec(Codec<Item> valueCodec) {
        return RecordCodecBuilder.create(
            instance -> instance.group(
                valueCodec.fieldOf("to").forGetter(ChangeOnlyItemItemStackMappingStep::to)
            ).apply(instance, ChangeOnlyItemItemStackMappingStep::new)
        );
    }

    @Override
    public void apply(ItemStackMappingHandle handle) {
        ChangeOnlyItemUtility.changeOnlyItem(handle, to);
    }

}
