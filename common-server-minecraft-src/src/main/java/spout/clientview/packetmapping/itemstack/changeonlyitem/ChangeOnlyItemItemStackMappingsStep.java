package spout.clientview.packetmapping.itemstack.changeonlyitem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Item;
import spout.clientview.packetmapping.itemstack.apply.ItemStackMappingHandle;
import spout.server.paper.impl.packetmapping.item.ItemMappingsStep;
import spout.util.mapping.handle.MappingStep;

/**
 * A {@link ItemMappingsStep} that always maps to a specific {@link Item}
 * while {@linkplain ChangeOnlyItemUtility#changeOnlyItem} preserving other properties}.
 */
public record ChangeOnlyItemItemStackMappingsStep(Item to) implements MappingStep<ItemStackMappingHandle> {

    public static Codec<ChangeOnlyItemItemStackMappingsStep> codec(Codec<Item> valueCodec) {
        return RecordCodecBuilder.create(
            instance -> instance.group(
                valueCodec.fieldOf("to").forGetter(ChangeOnlyItemItemStackMappingsStep::to)
            ).apply(instance, ChangeOnlyItemItemStackMappingsStep::new)
        );
    }

    @Override
    public void apply(ItemStackMappingHandle handle) {
        ChangeOnlyItemUtility.changeOnlyItem(handle, to);
    }

}
