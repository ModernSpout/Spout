package spout.gamecontent.datadriven.block.datapack;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import spout.gamecontent.datadriven.block.ContextAwareBlockPropertiesDecoding;
import spout.gamecontent.datadriven.block.SpoutNonBuiltInBlock;
import spout.server.paper.impl.moredatadriven.datapack.CopyResourcesFromDataPackRegistryToInternalRegistry;
import spout.server.paper.impl.moredatadriven.datapack.SpoutDataPackRegistries;
import spout.server.paper.impl.packetmapping.block.datadriven.UnappliedDataDrivenBlockMapping;
import spout.util.minecraft.registry.SpoutRegistryHookEvents;
import java.util.List;

public final class CopyDataPackRegistryToActualRegistryListener implements SpoutRegistryHookEvents.Listener<SpoutNonBuiltInBlock> {

    @Override
    public Iterable<Pair<ResourceKey<Registry<SpoutNonBuiltInBlock>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
        return List.of(Pair.of(SpoutDataPackRegistries.BLOCK_FROM_DATA_PACK, SpoutRegistryHookEvents.EventType.POST_FREEZE));
    }

    @Override
    public void onRegistryHookEvent(final SpoutRegistryHookEvents.EventType type, final WritableRegistry<SpoutNonBuiltInBlock> registry) {
        CopyResourcesFromDataPackRegistryToInternalRegistry.copyInitialized(
            registry,
            BuiltInRegistries.BLOCK,
            _ -> (key, resource) -> {
                ContextAwareBlockPropertiesDecoding.setKey(key);
                resource.initializeValueFromInput(false);
                ContextAwareBlockPropertiesDecoding.clearKey();
                Block block = resource.getValue();
                Object mappingsInput = resource.getInput().input().get("mappings");
                if (mappingsInput != null) {
                    DataResult<com.mojang.datafixers.util.Pair<List<UnappliedDataDrivenBlockMapping>, ?>> mappings = UnappliedDataDrivenBlockMapping.LIST_CODEC.decode((DynamicOps) resource.getInput().ops(), mappingsInput);
                    block.unappliedDataPackMappings = mappings.getOrThrow().getFirst();
                }
                resource.clearInput();
                return block;
            }
        );
    }

}
