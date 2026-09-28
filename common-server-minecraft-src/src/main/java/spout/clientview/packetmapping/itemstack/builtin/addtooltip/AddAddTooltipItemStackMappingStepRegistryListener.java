package spout.clientview.packetmapping.itemstack.builtin.addtooltip;

import it.unimi.dsi.fastutil.Pair;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import spout.branding.SpoutNamespace;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.clientview.packetmapping.itemstack.registry.ItemStackMappingRegistryKey;
import spout.server.paper.impl.configuration.SpoutGlobalConfiguration;
import spout.util.minecraft.registry.SpoutRegistryHookEvents;

public final class AddAddTooltipItemStackMappingStepRegistryListener implements SpoutRegistryHookEvents.Listener<ItemStackMapping> {

    @Override
    public Iterable<Pair<ResourceKey<Registry<ItemStackMapping>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
        return List.of(Pair.of(ItemStackMappingRegistryKey.ITEM_STACK_MAPPING, SpoutRegistryHookEvents.EventType.PRE_POPULATE_STATIC_REGISTRY));
    }

    @Override
    public void onRegistryHookEvent(final SpoutRegistryHookEvents.EventType type, final WritableRegistry<ItemStackMapping> registry) {
        if (SpoutGlobalConfiguration.get().tooltips.items.namespace) {
            Registry.register(
                registry,
                Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, "add_tooltip"),
                new ItemStackMapping(
                    Arrays.asList(AwarenessLevels.getAll()),
                    BuiltInRegistries.ITEM.stream().filter(item -> {
                        if (item.isVanilla()) {
                            return false;
                        }
                        String namespace = item.keyInItemRegistry.getNamespace();
                        if (namespace.equals(Identifier.DEFAULT_NAMESPACE) || namespace.equals(SpoutNamespace.SPOUT)) {
                            return false;
                        }
                        return true;
                    }).toList(),
                    new AddTooltipItemStackMappingStep()
                )
            );
        }
    }

}
