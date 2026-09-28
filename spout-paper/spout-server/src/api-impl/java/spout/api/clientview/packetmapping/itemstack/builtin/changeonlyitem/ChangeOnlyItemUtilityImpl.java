package spout.api.clientview.packetmapping.itemstack.builtin.changeonlyitem;

import net.minecraft.world.item.Item;
import org.bukkit.craftbukkit.inventory.CraftItemType;
import org.bukkit.inventory.ItemType;
import spout.api.clientview.packetmapping.itemstack.handle.ItemStackMappingHandle;
import spout.api.clientview.packetmapping.itemstack.handle.ItemStackMappingHandleImpl;
import spout.api.clientview.packetmapping.itemstack.handle.ItemStackMappingHandleNMS;
import spout.api.clientview.packetmapping.itemstack.handle.ItemStackMappingHandleNMSImpl;

/**
 * The implementation for {@link ChangeOnlyItemUtility} and {@link ChangeOnlyItemUtilityNMS}.
 */
public final class ChangeOnlyItemUtilityImpl implements ChangeOnlyItemUtilityNMS {

    public static ChangeOnlyItemUtilityImpl get() {
        return (ChangeOnlyItemUtilityImpl) ChangeOnlyItemUtilityNMS.get();
    }

    @Override
    public boolean changeOnlyItem(ItemStackMappingHandle handle, ItemType newItemType) {
        return spout.clientview.packetmapping.itemstack.builtin.changeonlyitem.ChangeOnlyItemUtility.changeOnlyItem(((ItemStackMappingHandleImpl) handle).handle, CraftItemType.bukkitToMinecraftNew(newItemType));
    }

    @Override
    public boolean changeOnlyItem(ItemStackMappingHandleNMS handle, Item newItem) {
        return spout.clientview.packetmapping.itemstack.builtin.changeonlyitem.ChangeOnlyItemUtility.changeOnlyItem(((ItemStackMappingHandleNMSImpl) handle).handle, newItem);
    }

}
