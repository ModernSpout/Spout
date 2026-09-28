package spout.api.clientview.packetmapping.itemstack;

import org.bukkit.Keyed;
import org.bukkit.inventory.ItemType;
import spout.api.clientview.packetmapping.common.registry.AwarenessLevelsMapping;
import spout.api.clientview.packetmapping.itemstack.handle.ItemStackMappingHandle;
import spout.api.util.mapping.FromMapping;
import spout.api.util.mapping.FunctionMapping;

public interface ItemStackMapping extends AwarenessLevelsMapping, FromMapping<ItemType>, FunctionMapping<ItemStackMappingHandle>, Keyed {
}
