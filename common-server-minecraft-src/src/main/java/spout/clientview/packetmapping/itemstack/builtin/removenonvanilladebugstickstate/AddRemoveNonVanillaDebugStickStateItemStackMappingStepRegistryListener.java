package spout.clientview.packetmapping.itemstack.builtin.removenonvanilladebugstickstate;

import it.unimi.dsi.fastutil.Pair;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import spout.branding.SpoutNamespace;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.clientview.packetmapping.itemstack.registry.ItemStackMappingRegistryKey;
import spout.util.minecraft.registry.SpoutRegistryHookEvents;

public final class AddRemoveNonVanillaDebugStickStateItemStackMappingStepRegistryListener implements SpoutRegistryHookEvents.Listener<ItemStackMapping> {

    @Override
    public Iterable<Pair<ResourceKey<Registry<ItemStackMapping>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
        return List.of(Pair.of(ItemStackMappingRegistryKey.ITEM_STACK_MAPPING, SpoutRegistryHookEvents.EventType.PRE_POPULATE_STATIC_REGISTRY));
    }

    @Override
    public void onRegistryHookEvent(final SpoutRegistryHookEvents.EventType type, final WritableRegistry<ItemStackMapping> registry) {
        Registry.register(
            registry,
            Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, "remove_non_vanilla_debug_stick_state"),
            new ItemStackMapping(
                Arrays.asList(AwarenessLevels.getThatDoNotAlwaysUnderstandsAllServerSideBlocks()),
                List.of(Items.DEBUG_STICK),
                new RemoveNonVanillaDebugStickStateItemStackMappingStep()
            )
        );
    }

}
