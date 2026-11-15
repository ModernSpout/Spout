package spout.clientview.packetmapping.itemstack.builtin.changeonlyitem;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Bees;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.itemstack.apply.ItemStackMappingHandle;
import java.util.Objects;

public final class ChangeOnlyItemUtility {

    private ChangeOnlyItemUtility() {
        throw new UnsupportedOperationException();
    }

    public static boolean changeOnlyItem(ItemStackMappingHandle handle, Item newItem) {

        // Don't make changes if the item is already present
        ItemStack reference = handle.getImmutable();
        Item originalItem = reference.getItem();
        if (originalItem == newItem) {
            return false;
        }

        // Change the item
        ItemStack changed = handle.getMutable();
        if (reference == changed) {
            reference = reference.copy();
        }
        changed.setItem(newItem);

        // Restore the item name
        Component originalItemName = reference.getItemName();
        if (originalItemName.equals(CommonComponents.EMPTY)) {
            originalItemName = null;
        }
        Component newItemName = changed.getItemName();
        if (newItemName.equals(CommonComponents.EMPTY)) {
            newItemName = null;
        }
        if (newItemName == null && originalItemName == null) {
            if (!newItem.getDescriptionId().equals(originalItem.getDescriptionId())) {
                changed.set(DataComponents.ITEM_NAME, Component.translatable(originalItem.getDescriptionId()));
            }
        } else if (!Objects.equals(newItemName, originalItemName)) {
            changed.set(DataComponents.ITEM_NAME, originalItemName != null ? originalItemName : Component.translatable(originalItem.getDescriptionId()));
        }

        // Restore the rarity
        Rarity originalRarity = reference.getRarity();
        Rarity newRarity = changed.getRarity();
        if (!newRarity.equals(originalRarity)) {
            Rarity newRarityComponentValue;
            if (changed.isEnchanted()) {
                // Set the rarity component to one lower
                newRarityComponentValue = switch (originalRarity) {
                    case EPIC -> Rarity.RARE;
                    case RARE -> Rarity.UNCOMMON;
                    default -> Rarity.COMMON;
                };
            } else {
                newRarityComponentValue = originalRarity;
            }
            @Nullable Rarity existingRarityComponentValue = changed.get(DataComponents.RARITY);
            if (existingRarityComponentValue == null || !existingRarityComponentValue.equals(newRarityComponentValue)) {
                changed.set(DataComponents.RARITY, newRarityComponentValue);
            }
        }

        // Restore the max damage and damage
        int originalMaxDamage = reference.getMaxDamage();
        int originalDamage = reference.getDamageValue();
        int newMaxDamage = changed.getMaxDamage();
        if (newMaxDamage != originalMaxDamage) {
            changed.set(DataComponents.MAX_DAMAGE, originalMaxDamage);
        }
        int newDamage = changed.getDamageValue();
        if (newDamage != originalDamage) {
            changed.set(DataComponents.DAMAGE, originalDamage);
        }

        // Restore the bees
        @Nullable Bees originalBees = reference.get(DataComponents.BEES);
        @Nullable Bees newBees = reference.get(DataComponents.BEES);
        if (Objects.equals(originalBees, newBees)) {
            changed.set(DataComponents.BEES, originalBees);
        }

        // We made changes
        return true;

    }

}
