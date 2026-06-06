package spout.gamecontent.datadriven.item;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.Pair;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import spout.common.moredatadriven.minecraft.item.ContextAwareItemPropertiesDecoding;
import spout.common.moredatadriven.minecraft.item.SpoutNonBuiltInItem;
import spout.server.paper.impl.moredatadriven.datapack.CopyResourcesFromDataPackRegistryToInternalRegistry;
import spout.server.paper.impl.moredatadriven.datapack.SpoutDataPackRegistries;
import spout.server.paper.impl.packetmapping.item.datadriven.UnappliedDataDrivenItemMapping;
import spout.util.minecraft.registry.SpoutRegistryHookEvents;

public class CopyDataPackRegistryToActualRegistryListener implements SpoutRegistryHookEvents.Listener<SpoutNonBuiltInItem> {

    @Override
    public Iterable<Pair<ResourceKey<Registry<SpoutNonBuiltInItem>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
        return List.of(Pair.of(SpoutDataPackRegistries.ITEM_FROM_DATA_PACK, SpoutRegistryHookEvents.EventType.POST_FREEZE));
    }

    @Override
    public void onRegistryHookEvent(final SpoutRegistryHookEvents.EventType type, final WritableRegistry<SpoutNonBuiltInItem> registry) {
        CopyResourcesFromDataPackRegistryToInternalRegistry.copyInitialized(
            registry,
            BuiltInRegistries.ITEM,
            _ -> (key, resource) -> {
                ContextAwareItemPropertiesDecoding.setKey(key);
                resource.initializeValueFromInput(false);
                ContextAwareItemPropertiesDecoding.clearKey();
                Item item = resource.getValue();
                Object mappingsInput = resource.getInput().input().get("mappings");
                if (mappingsInput != null) {
                    DataResult<com.mojang.datafixers.util.Pair<List<UnappliedDataDrivenItemMapping>, ?>> mappings = UnappliedDataDrivenItemMapping.LIST_CODEC.decode((DynamicOps) resource.getInput().ops(), mappingsInput);
                    item.unappliedDataPackMappings = mappings.getOrThrow().getFirst();
                }
                resource.clearInput();
                return item;
            }
        );
    }

}
