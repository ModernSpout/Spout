package spout.clientview.packetmapping.itemstack.apply;

import net.minecraft.world.item.ItemStack;
import spout.util.mapping.handle.AbstractMappingHandle;
import spout.util.mapping.handle.SimpleWithContextMappingHandle;

/**
 * The implementation of {@link AbstractMappingHandle} for item stack mappings.
 */
public final class ItemStackMappingHandle extends SimpleWithContextMappingHandle<ItemStack, ItemStack, ItemStackMappingsApplicationContext> {

    public ItemStackMappingHandle(ItemStack data, ItemStackMappingsApplicationContext context, boolean isDataMutable) {
        super(data, context, isDataMutable);
    }

}
