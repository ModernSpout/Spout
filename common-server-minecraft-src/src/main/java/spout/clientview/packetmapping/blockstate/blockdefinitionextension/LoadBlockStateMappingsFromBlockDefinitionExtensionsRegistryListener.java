package spout.clientview.packetmapping.blockstate.blockdefinitionextension;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMappingRegistryKey;
import spout.server.paper.impl.packetmapping.block.datadriven.UnappliedDataDrivenBlockMapping;
import spout.util.minecraft.registry.SpoutRegistryHookEvents;
import java.util.List;
import java.util.Map;

public final class LoadBlockStateMappingsFromBlockDefinitionExtensionsRegistryListener implements SpoutRegistryHookEvents.Listener<BlockStateMapping> {

    static volatile boolean isDone = false;

    @Override
    public Iterable<Pair<ResourceKey<Registry<BlockStateMapping>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
        return List.of(Pair.of(BlockStateMappingRegistryKey.BLOCK_STATE_MAPPING, SpoutRegistryHookEvents.EventType.PRE_POPULATE_STATIC_REGISTRY));
    }

    @Override
    public void onRegistryHookEvent(final SpoutRegistryHookEvents.EventType type, final WritableRegistry<BlockStateMapping> registry) {
        for (Map.Entry<Block, List<UnappliedDataDrivenBlockMapping>> entry : BlockDefinitionBlockStateMappingExtensionStorage.mappings.entrySet()) {
            List<UnappliedDataDrivenBlockMapping> mappings = entry.getValue();
            for (int i = 0; i < mappings.size(); i++) {
                UnappliedDataDrivenBlockMapping mapping = mappings.get(i);
                if (!mapping.isMacro()) {
                    mapping.applyAsMapping(registry, entry.getKey(), i);
                }
            }
        }
        // Dereference to reclaim memory
        isDone = true;
        if (LoadBlockStateMappingMacrosFromBlockDefinitionExtensionsRegistryListener.isDone) {
            BlockDefinitionBlockStateMappingExtensionStorage.mappings = null;
        }
    }

}
