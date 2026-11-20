package spout.api.clientview.packetmapping.itemstack.registry;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import spout.api.clientview.packetmapping.itemstack.handle.ItemStackMappingHandleNMS;

/**
 * NMS extension for {@link ItemStackMappingRegistryEntry}.
 *
 * <p>
 * Every instance of {@link ItemStackMappingRegistryEntry}
 * is an instance of {@link ItemStackMappingRegistryEntryNMS}.
 * </p>
 */
public interface ItemStackMappingRegistryEntryNMS extends ItemStackMappingRegistryEntry {

    /**
     * NMS extension for {@link #getItemModel()}.
     */
    @Nullable Identifier getItemModelNMS();

    /**
     * NMS extension for {@link ItemStackMappingRegistryEntry.Builder}.
     *
     * <p>
     * Every instance of {@link ItemStackMappingRegistryEntry.Builder}
     * is an instance of {@link ItemStackMappingRegistryEntryNMS.Builder}.
     * </p>
     */
    @ApiStatus.NonExtendable
    interface Builder extends ItemStackMappingRegistryEntryNMS, ItemStackMappingRegistryEntry.Builder {

        /**
         * NMS extension for {@link #setFrom(Object)}.
         */
        default void setFromNMS(Item from) {
            this.setFromNMS(List.of(from));
        }

        /**
         * NMS extension for {@link #setFrom(Object[])}.
         */
        default void setFromNMS(Item[] from) {
            this.setFromNMS(Arrays.asList(from));
        }

        /**
         * NMS extension for {@link #setFrom(Collection)}.
         */
        void setFromNMS(Collection<? extends Item> from);

        /**
         * NMS extension for {@link #addFrom(Object)}.
         */
        void addFromNMS(Item from);

        /**
         * NMS extension for {@link #addFrom(Object[])}.
         */
        default void addFromNMS(Item[] from) {
            for (Item value : from) {
                this.addFromNMS(value);
            }
        }

        /**
         * NMS extension for {@link #addFrom(Collection)}.
         */
        default void addFromNMS(Collection<Item> from) {
            for (Item value : from) {
                this.addFromNMS(value);
            }
        }

        /**
         * NMS extension for {@link #setTo(Object)}.
         */
        void setToNMS(Item to);

        /**
         * NMS extension for {@link #setToFunction}.
         */
        void setToFunctionNMS(Consumer<ItemStackMappingHandleNMS> function);

        /**
         * NMS extension for {@link #setItemModel}.
         */
        void setItemModelNMS(@Nullable Identifier itemModel);

    }

}
