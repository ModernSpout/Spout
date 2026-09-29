package spout.clientview.packetmapping.itemstack.builtin.datapackitemextension;

import it.unimi.dsi.fastutil.Pair;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import spout.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.clientview.packetmapping.itemstack.registry.ItemStackMappingRegistryKey;
import spout.util.minecraft.registry.SpoutRegistryHookEvents;

public final class LoadItemStackMappingsFromDataPackItemExtensionsRegistryListener implements SpoutRegistryHookEvents.Listener<ItemStackMapping> {

    @Override
    public Iterable<Pair<ResourceKey<Registry<ItemStackMapping>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
        return List.of(Pair.of(ItemStackMappingRegistryKey.ITEM_STACK_MAPPING, SpoutRegistryHookEvents.EventType.PRE_POPULATE_STATIC_REGISTRY));
    }

    @Override
    public void onRegistryHookEvent(final SpoutRegistryHookEvents.EventType type, final WritableRegistry<ItemStackMapping> registry) {
        for (Map.Entry<Item, List<DataPackItemItemStackMapping>> entry : DataPackItemItemStackMappingExtensionStorage.mappings.entrySet()) {
            List<DataPackItemItemStackMapping> mappings = entry.getValue();
            for (int i = 0; i < mappings.size(); i++) {
                DataPackItemItemStackMapping mapping = mappings.get(i);
                mapping.apply(registry, entry.getKey(), i);
            }
        }
        // Dereference to reclaim memory
        DataPackItemItemStackMappingExtensionStorage.mappings = null;
    }

}
