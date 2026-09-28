package spout.api.clientview.packetmapping.itemstack.handle;

import net.minecraft.world.item.ItemStack;
import spout.api.util.mapping.handle.MutableMappingHandle;
import spout.api.util.mapping.handle.WithContextMappingHandle;
import spout.api.util.mapping.handle.WithOriginalMappingHandle;

/**
 * NMS analogue for {@link ItemStackMappingHandle}.
 *
 * <p>
 * Note: instances of {@link ItemStackMappingHandle}
 * are not instances of {@link ItemStackMappingHandleNMS}.
 * </p>
 */
public interface ItemStackMappingHandleNMS extends WithContextMappingHandle<ItemStack, ItemStackMappingContext>, WithOriginalMappingHandle<ItemStack>, MutableMappingHandle<ItemStack, ItemStack> {
}
