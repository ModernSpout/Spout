package spout.api.clientview.packetmapping.itemstack.registry;

import java.util.function.Consumer;
import net.minecraft.resources.Identifier;
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
         * NMS extension for {@link #setToFunction}.
         */
        void setToFunctionNMS(Consumer<ItemStackMappingHandleNMS> function);

        /**
         * NMS extension for {@link #setItemModel}.
         */
        void setItemModelNMS(@Nullable Identifier itemModel);

    }

}
