package spout.clientview.packetmapping.blockstate.macro.processor;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMappingRegistryKey;
import spout.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.clientview.packetmapping.itemstack.registry.ItemStackMappingRegistryKey;
import spout.util.minecraft.registry.SpoutRegistryHookEvents;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class AddDerivedItemStackMappingsRegistryListener implements SpoutRegistryHookEvents.Listener<ItemStackMapping> {

    private static volatile boolean blockStateMappingsAreFrozen = false;

    public static final class BlockStateMappingRegistryFreezeListener implements SpoutRegistryHookEvents.Listener<BlockStateMapping> {

        @Override
        public Iterable<Pair<ResourceKey<Registry<BlockStateMapping>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
            return List.of(Pair.of(BlockStateMappingRegistryKey.BLOCK_STATE_MAPPING, SpoutRegistryHookEvents.EventType.POST_FREEZE));
        }

        @Override
        public void onRegistryHookEvent(SpoutRegistryHookEvents.EventType type, WritableRegistry<BlockStateMapping> registry) {
            blockStateMappingsAreFrozen = true;
        }

    }

    @Override
    public Iterable<Pair<ResourceKey<Registry<ItemStackMapping>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
        return List.of(Pair.of(ItemStackMappingRegistryKey.ITEM_STACK_MAPPING, SpoutRegistryHookEvents.EventType.PRE_FREEZE));
    }

    private static List<Pair<Identifier, Supplier<@Nullable ItemStackMapping>>> mappings = new ArrayList<>();

    public static void add(Identifier identifier, Supplier<@Nullable ItemStackMapping> mapping) {
        mappings.add(Pair.of(identifier, mapping));
    }

    @Override
    public void onRegistryHookEvent(final SpoutRegistryHookEvents.EventType type, final WritableRegistry<ItemStackMapping> registry) {
        // Wait for the macros to be processed
        int i = 0;
        while (!blockStateMappingsAreFrozen) {
            Thread.onSpinWait();
        }

        for (Pair<Identifier, Supplier<@Nullable ItemStackMapping>> pair : mappings) {
            @Nullable ItemStackMapping mapping = pair.right().get();
            if (mapping != null) {
                Registry.register(registry, pair.left(), mapping);
            }
        }

        // Dereference to reclaim memory
        mappings = null;
    }

}
