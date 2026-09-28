package spout.clientview.packetmapping.blockstate.datapackblockextension;

import it.unimi.dsi.fastutil.Pair;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.registry.BlockStateMappingMacroRegistryKey;
import spout.util.minecraft.registry.SpoutRegistryHookEvents;

public final class LoadBlockStateMappingMacrosFromDataPackBlockExtensionsRegistryListener implements SpoutRegistryHookEvents.Listener<BlockStateMappingMacro> {

    static volatile boolean isDone = false;

    @Override
    public Iterable<Pair<ResourceKey<Registry<BlockStateMappingMacro>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
        return List.of(Pair.of(BlockStateMappingMacroRegistryKey.BLOCK_STATE_MAPPING_MACRO, SpoutRegistryHookEvents.EventType.PRE_POPULATE_STATIC_REGISTRY));
    }

    @Override
    public void onRegistryHookEvent(final SpoutRegistryHookEvents.EventType type, final WritableRegistry<BlockStateMappingMacro> registry) {
        for (Map.Entry<Block, List<DataPackBlockBlockStateMappingOrMacro>> entry : DataPackBlockBlockStateMappingExtensionStorage.mappings.entrySet()) {
            List<DataPackBlockBlockStateMappingOrMacro> mappings = entry.getValue();
            for (int i = 0; i < mappings.size(); i++) {
                DataPackBlockBlockStateMappingOrMacro mapping = mappings.get(i);
                if (mapping.isMacro()) {
                    mapping.applyAsMappingMacro(registry, entry.getKey(), i);
                }
            }
        }
        // Dereference to reclaim memory
        isDone = true;
        if (LoadBlockStateMappingsFromDataPackBlockExtensionsRegistryListener.isDone) {
            DataPackBlockBlockStateMappingExtensionStorage.mappings = null;
        }
    }

}
