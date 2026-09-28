package spout.clientview.packetmapping.component.apply.defaultitemnames;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import spout.clientview.packetmapping.component.ComponentInPacketMapper;
import spout.clientview.packetmapping.component.apply.ComponentMappingsApplicationContext;
import spout.clientview.packetmapping.itemstack.apply.ItemStackMappingHandle;
import spout.util.mapping.handle.MappingStep;

/**
 * A {@link MappingStep} that maps default item name components.
 */
public final class MapDefaultItemNamesItemStackMappingStep implements MappingStep<ItemStackMappingHandle> {

    @Override
    public void apply(ItemStackMappingHandle handle) {
        Component itemName = handle.getImmutable().getItemName().copy();
        Component mappedItemName = ComponentInPacketMapper.get().apply(itemName, new ComponentMappingsApplicationContext(handle.getContext().getClientView()));
        if (!mappedItemName.equals(itemName)) {
            handle.getMutable().set(DataComponents.ITEM_NAME, mappedItemName);
        }
    }

}
