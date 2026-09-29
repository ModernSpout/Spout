package spout.api.gamecontent.datadriven.itemtype.registry;

import io.papermc.paper.registry.RegistryBuilder;
import org.jetbrains.annotations.ApiStatus;
import spout.api.gamecontent.datadriven.itemtype.ItemTypeType;

/**
 * A data-centric version-specific registry entry for the {@link ItemTypeType} type.
 */
@ApiStatus.Experimental
@ApiStatus.NonExtendable
public interface ItemTypeTypeRegistryEntry {

    // Object getWrappedCodec();
    //
    // Object getCodec();

    /**
     * A mutable builder for the {@link ItemTypeTypeRegistryEntry},
     * that plugins may change in applicable registry events.
     *
     * <p>
     * Currently, this must be cast to {@code ItemTypeTypeRegistryEntryNMS.Builder} to be used.
     * </p>
     */
    @ApiStatus.Experimental
    @ApiStatus.NonExtendable
    interface Builder extends ItemTypeTypeRegistryEntry, RegistryBuilder<ItemTypeType> {
    }

}
