package spout.api.clientview.packetmapping.itemstack.builtin.changeonlyitem;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import spout.api.clientview.packetmapping.itemstack.handle.ItemStackMappingHandleNMS;

/**
 * An extension to {@link ChangeOnlyItemUtility} using Minecraft internals.
 */
public interface ChangeOnlyItemUtilityNMS extends ChangeOnlyItemUtility {

    /**
     * @return The {@link ChangeOnlyItemUtilityNMS} instance.
     */
    static ChangeOnlyItemUtilityNMS get() {
        return (ChangeOnlyItemUtilityNMS) ChangeOnlyItemUtility.get();
    }

    /**
     * Changes the {@link Item} of the given handle's {@link ItemStack},
     * while attempting to keep the client-side appearance the same in most ways.
     *
     * @param handle  The handle being mapped.
     * @param newItem The new {@link Item} for the item stack.
     * @return Whether any changes were made.
     */
    boolean changeOnlyItem(ItemStackMappingHandleNMS handle, Item newItem);

}
