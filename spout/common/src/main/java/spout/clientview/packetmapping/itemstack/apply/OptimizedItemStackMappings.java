package spout.clientview.packetmapping.itemstack.apply;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.clientview.packetmapping.itemstack.registry.ItemStackMappingRegistryKey;
import spout.util.minecraft.registry.SpoutRegistryHookEvents;

/**
 * Holds all item stack mappings
 * in an optimized data structure.
 */
public final class OptimizedItemStackMappings {

    private OptimizedItemStackMappings() {
        throw new UnsupportedOperationException();
    }

    public static final class RegistryFreezeListener implements SpoutRegistryHookEvents.Listener<ItemStackMapping> {

        @Override
        public Iterable<Pair<ResourceKey<Registry<ItemStackMapping>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
            return List.of(Pair.of(ItemStackMappingRegistryKey.ITEM_STACK_MAPPING, SpoutRegistryHookEvents.EventType.POST_FREEZE));
        }

        @Override
        public void onRegistryHookEvent(SpoutRegistryHookEvents.EventType type, WritableRegistry<ItemStackMapping> registry) {
            build(registry);
        }

    }

    /**
     * The registered mappings.
     *
     * <p>
     * The mappings are organized in an array where {@link AwarenessLevel#getId()}
     * is the index, and then in an array where {@link Item#indexInItemRegistry} is the index.
     * The lowest-level array may be null, but will never be empty.
     * </p>
     */
    private static Consumer<ItemStackMappingHandle>[][][] mappings;

    public static Consumer<ItemStackMappingHandle> @Nullable [] getChain(ItemStackMappingHandle handle) {
        return mappings[handle.getContext().getClientView().getAwarenessLevel().getId()][handle.getOriginal().getItem().indexInItemRegistry];
    }

    public static Consumer<ItemStackMappingHandle> @Nullable [] getChain(int awarenessLevelId, int itemIndexInRegistry) {
        return mappings[awarenessLevelId][itemIndexInRegistry];
    }

    /**
     * Fills this class from the mappings in the corresponding registry.
     */
    public static void build(Registry<ItemStackMapping> registry) {

        // Initialize the arrays
        int awarenessLevelSize = AwarenessLevels.getAll().length;
        mappings = new Consumer[awarenessLevelSize][][];
        int registrySize = BuiltInRegistries.ITEM.size();
        for (int i = 0; i < mappings.length; i++) {
            mappings[i] = new Consumer[registrySize][];
        }

        // Populate
        Map<IntIntPair, List<Consumer<ItemStackMappingHandle>>> registered = new HashMap<>();
        registry.stream()
            .forEach(mapping -> {
                // Base mapping
                for (AwarenessLevel awarenessLevel : mapping.awarenessLevels()) {
                    for (Item target : mapping.targets()) {
                        registered.computeIfAbsent(IntIntPair.of(awarenessLevel.getId(), target.indexInItemRegistry), _ -> new ArrayList<>(1)).add(mapping.operation());
                    }
                }
                // Item model override
                if (mapping.overrideItemModel() == null || mapping.overrideItemModel()) {
                    List<AwarenessLevel> awarenessLevelsToOverrideItemModelFor;
                    if (mapping.overrideItemModel() == null && mapping.awarenessLevels().contains(AwarenessLevels.VANILLA)) {
                        awarenessLevelsToOverrideItemModelFor = mapping.awarenessLevels().stream().filter(level -> level != AwarenessLevels.VANILLA).toList();
                    } else {
                        awarenessLevelsToOverrideItemModelFor = mapping.awarenessLevels();
                    }
                    if (!awarenessLevelsToOverrideItemModelFor.isEmpty()) {
                        if (mapping.itemModel() != null) {
                            Identifier itemModelToUse = mapping.itemModel();
                            for (AwarenessLevel awarenessLevel : awarenessLevelsToOverrideItemModelFor) {
                                for (Item target : mapping.targets()) {
                                    registered.computeIfAbsent(IntIntPair.of(awarenessLevel.getId(), target.indexInItemRegistry), _ -> new ArrayList<>(1)).add(handle -> {
                                        handle.getMutable().set(DataComponents.ITEM_MODEL, itemModelToUse);
                                    });
                                }
                            }
                        } else {
                            for (Item target : mapping.targets()) {
                                Identifier itemModelToUse = target.keyInItemRegistry;
                                for (AwarenessLevel awarenessLevel : awarenessLevelsToOverrideItemModelFor) {
                                    registered.computeIfAbsent(IntIntPair.of(awarenessLevel.getId(), target.indexInItemRegistry), _ -> new ArrayList<>(1)).add(handle -> {
                                        handle.getMutable().set(DataComponents.ITEM_MODEL, itemModelToUse);
                                    });
                                }
                            }
                        }
                    }
                }
            });
        // TODO invert and re-invert
        registered.forEach((key, mappings) -> {
            OptimizedItemStackMappings.mappings[key.firstInt()][key.secondInt()] = mappings.toArray(Consumer[]::new);
        });

    }

}
