package spout.clientview.packetmapping.itemstack.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import spout.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.util.minecraft.registry.RegistryKeyUtil;

/**
 * Holder for {@link #ITEM_STACK_MAPPING}.
 *
 * <p>
 * Analogous to {@link Registries}.
 * </p>
 */
public final class ItemStackMappingRegistryKey {

    private ItemStackMappingRegistryKey() {
        throw new UnsupportedOperationException();
    }

    /**
     * Key for the item stack mapping registry.
     */
    public static final ResourceKey<Registry<ItemStackMapping>> ITEM_STACK_MAPPING = RegistryKeyUtil.createWithSpoutNamespace("item_stack_mapping");

}
