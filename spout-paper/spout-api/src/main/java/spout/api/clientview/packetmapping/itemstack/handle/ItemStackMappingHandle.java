package spout.api.clientview.packetmapping.itemstack.handle;

import org.bukkit.inventory.ItemStack;
import spout.api.clientview.packetmapping.itemstack.registry.ItemStackMappingRegistryEntry;
import spout.api.util.mapping.handle.MutableMappingHandle;
import spout.api.util.mapping.handle.WithContextMappingHandle;
import spout.api.util.mapping.handle.WithOriginalMappingHandle;

/**
 * A handle provided to code registered with {@link ItemStackMappingRegistryEntry.Builder#setToFunction}.
 */
public interface ItemStackMappingHandle extends WithContextMappingHandle<ItemStack, ItemStackMappingContext>, WithOriginalMappingHandle<ItemStack>, MutableMappingHandle<ItemStack, ItemStack> {
}
