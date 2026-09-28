package spout.api.clientview.packetmapping.itemstack.builtin.changeonlyitem;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import spout.api.SpoutAPIServices;
import spout.api.clientview.packetmapping.itemstack.handle.ItemStackMappingHandle;

/**
 * Some utilities for the mapping of items.
 */
public interface ChangeOnlyItemUtility {

    /**
     * @return The {@link ChangeOnlyItemUtility} instance.
     */
    static ChangeOnlyItemUtility get() {
        return SpoutAPIServices.getChangeOnlyItemUtility();
    }

    /**
     * Changes the {@link ItemType} of the given handle's {@link ItemStack},
     * while attempting to keep the client-side appearance the same in most ways.
     *
     * @param handle      The handle being mapped.
     * @param newItemType The new {@link ItemType} for the item stack.
     * @return Whether any changes were made.
     */
    boolean changeOnlyItem(ItemStackMappingHandle handle, ItemType newItemType);

}
