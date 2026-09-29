package spout.clientview.packetmapping.blockstate.macro;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.clientview.packetmapping.blockstate.macro.processor.BlockStateMappingMacroProcessor;
import spout.clientview.packetmapping.blockstate.macro.registry.BlockStateMappingMacroRegistryKey;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMappingRegistryKey;
import spout.util.minecraft.registry.SpoutRegistryHookEvents;
import java.util.List;

public final class ProcessBlockStateMappingMacrosRegistryListeners {

    private ProcessBlockStateMappingMacrosRegistryListeners() {
        throw new UnsupportedOperationException();
    }

    private static volatile WritableRegistry<BlockStateMappingMacro> macroRegistry = null;

    public static final class MacroRegistryListener implements SpoutRegistryHookEvents.Listener<BlockStateMappingMacro> {

        @Override
        public Iterable<Pair<ResourceKey<Registry<BlockStateMappingMacro>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
            return List.of(Pair.of(BlockStateMappingMacroRegistryKey.BLOCK_STATE_MAPPING_MACRO, SpoutRegistryHookEvents.EventType.POST_POPULATE_STATIC_REGISTRY));
        }

        @Override
        public void onRegistryHookEvent(final SpoutRegistryHookEvents.EventType type, final WritableRegistry<BlockStateMappingMacro> registry) {
            macroRegistry = registry;
        }

    }

    public static final class MappingRegistryListener implements SpoutRegistryHookEvents.Listener<BlockStateMapping> {

        @Override
        public Iterable<Pair<ResourceKey<Registry<BlockStateMapping>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
            return List.of(Pair.of(BlockStateMappingRegistryKey.BLOCK_STATE_MAPPING, SpoutRegistryHookEvents.EventType.PRE_POPULATE_STATIC_REGISTRY));
        }

        @Override
        public void onRegistryHookEvent(final SpoutRegistryHookEvents.EventType type, final WritableRegistry<BlockStateMapping> registry) {
            // Wait for the macro registry to be loaded
            while (macroRegistry == null) {
                Thread.onSpinWait();
            }

            macroRegistry.freeze();
            macroRegistry.forEach(macro -> {
                BlockStateMappingMacroProcessor<?> processor = macro.type.createProcessor(macro, macroRegistry, registry);
                processor.process();
            });
        }

    }

}
