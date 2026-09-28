package spout.clientview.packetmapping.itemstack.builtin.mapdefaultitemnames;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import spout.branding.SpoutNamespace;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.clientview.packetmapping.itemstack.registry.ItemStackMappingRegistryKey;
import spout.util.minecraft.registry.SpoutRegistryHookEvents;
import java.util.Arrays;
import java.util.List;

public final class AddMapDefaultItemNamesItemStackMappingStepRegistryListener implements SpoutRegistryHookEvents.Listener<ItemStackMapping> {

    @Override
    public Iterable<Pair<ResourceKey<Registry<ItemStackMapping>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
        return List.of(Pair.of(ItemStackMappingRegistryKey.ITEM_STACK_MAPPING, SpoutRegistryHookEvents.EventType.PRE_POPULATE_STATIC_REGISTRY));
    }

    @Override
    public void onRegistryHookEvent(final SpoutRegistryHookEvents.EventType type, final WritableRegistry<ItemStackMapping> registry) {
        Registry.register(
            registry,
            Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, "map_default_item_names"),
            new ItemStackMapping(
                Arrays.asList(AwarenessLevels.getAll()),
                BuiltInRegistries.ITEM.stream().toList(),
                new MapDefaultItemNamesItemStackMappingStep()
            )
        );
    }

}
