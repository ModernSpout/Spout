package spout.api.clientview.packetmapping.itemstack.registry;

import io.papermc.paper.registry.RegistryBuilder;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import spout.api.clientview.model.ClientView;
import spout.api.clientview.packetmapping.common.builder.AwarenessLevelsMappingRegistryEntry;
import spout.api.clientview.packetmapping.common.builder.AwarenessLevelsMappingRegistryEntryBuilder;
import spout.api.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.api.clientview.packetmapping.itemstack.handle.ItemStackMappingHandle;
import spout.api.util.mapping.builder.FromRegistryEntry;
import spout.api.util.mapping.builder.FromRegistryEntryBuilder;
import spout.api.util.mapping.builder.FunctionRegistryEntryBuilder;
import spout.api.util.mapping.builder.ToRegistryEntry;
import spout.api.util.mapping.builder.ToRegistryEntryBuilder;

/**
 * A data-centric version-specific registry entry for the {@link ItemStackMapping} type.
 */
public interface ItemStackMappingRegistryEntry extends AwarenessLevelsMappingRegistryEntry, FromRegistryEntry<ItemType>, ToRegistryEntry<ItemType> {

    /**
     * @return Whether this mapping should set the {@code item_model} component of the item stack,
     * to the {@link #getItemModel()}.
     *
     * <p>
     * If null, it will be automatically treated as false for {@link ClientView.AwarenessLevel#VANILLA},
     * and true for all other {@link ClientView.AwarenessLevel}s.
     * </p>
     *
     * <p>
     * By default, this value is null.
     * </p>
     */
    @Nullable Boolean getOverrideItemModel();

    /**
     * @return The item model to use to override the {@code item_model} component of the item stack,
     * {@linkplain #getOverrideItemModel() if it is enabled}.
     *
     * <p>
     * If null, it will be automatically set to the {@link ItemType#getKey()}
     * of the {@link ItemType} set with {@link Builder#setFrom}.
     * </p>
     *
     * <p>
     * By default, this value is null.
     * </p>
     */
    @Nullable NamespacedKey getItemModel();

    /**
     * A mutable builder for a {@link ItemStackMappingRegistryEntry}.
     */
    @ApiStatus.NonExtendable
    interface Builder extends ItemStackMappingRegistryEntry, RegistryBuilder<ItemStackMapping>, AwarenessLevelsMappingRegistryEntryBuilder, FromRegistryEntryBuilder<ItemType>, ToRegistryEntryBuilder<ItemType>, FunctionRegistryEntryBuilder<ItemStackMappingHandle> {

        /**
         * Sets this builder to target all items.
         *
         * <p>
         * This negatively affects performance: try to target specific items instead.
         * </p>
         */
        default void setFromAllItems() {
            this.setFrom(Registry.ITEM.stream().toList());
        }

        /**
         * Sets {@link #getOverrideItemModel()} to the given value.
         */
        void setOverrideItemModel(@Nullable Boolean overrideItemModel);

        /**
         * Sets {@link #getItemModel()} to the given value.
         */
        void setItemModel(@Nullable NamespacedKey itemModel);

    }

}
