package spout.clientview.packetmapping.component.apply;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.Nullable;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.packetmapping.component.ComponentMapping;
import spout.clientview.packetmapping.component.ComponentTarget;
import spout.clientview.packetmapping.component.ComponentTargetUtil;
import spout.clientview.packetmapping.component.registry.ComponentMappingRegistryKey;
import spout.util.minecraft.registry.SpoutRegistryHookEvents;

/**
 * Holds all component mappings
 * in an optimized data structure.
 */
public final class OptimizedComponentMappings {

    private OptimizedComponentMappings() {
        throw new UnsupportedOperationException();
    }

    public static final class RegistryFreezeListener implements SpoutRegistryHookEvents.Listener<ComponentMapping> {

        @Override
        public Iterable<Pair<ResourceKey<Registry<ComponentMapping>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
            return List.of(Pair.of(ComponentMappingRegistryKey.COMPONENT_MAPPING, SpoutRegistryHookEvents.EventType.POST_FREEZE));
        }

        @Override
        public void onRegistryHookEvent(SpoutRegistryHookEvents.EventType type, WritableRegistry<ComponentMapping> registry) {
            build(registry);
        }

    }

    /**
     * The registered mappings.
     *
     * <p>
     * The mappings are organized in an array where {@link AwarenessLevel#getId()}
     * is the index, and then in an array where {@link ComponentTarget#ordinal()} is the index.
     * The lowest-level array may be null, but will never be empty.
     * </p>
     */
    private static Consumer<ComponentMappingHandle>[][][] mappings;

    public static Consumer<ComponentMappingHandle> @Nullable [] getChain(ComponentMappingHandle handle) {
        return mappings[handle.getContext().getClientView().getAwarenessLevel().getId()][ComponentTargetUtil.get().getMostSpecificTarget(handle.getOriginal()).ordinal()];
    }

    public static Consumer<ComponentMappingHandle> @Nullable [] getChain(int awarenessLevelId, int componentTargetOrdinal) {
        return mappings[awarenessLevelId][componentTargetOrdinal];
    }

    /**
     * Fills this class from the mappings in the corresponding registry.
     */
    public static void build(Registry<ComponentMapping> registry) {

        // Initialize the arrays
        int awarenessLevelSize = AwarenessLevels.getAll().length;
        mappings = new Consumer[awarenessLevelSize][][];
        int targetSize = ComponentTarget.values().length;
        for (int i = 0; i < mappings.length; i++) {
            mappings[i] = new Consumer[targetSize][];
        }

        // Populate
        Map<IntIntPair, List<Consumer<ComponentMappingHandle>>> registered = new HashMap<>();
        registry.stream()
            .forEach(mapping -> {
                for (AwarenessLevel awarenessLevel : mapping.awarenessLevels()) {
                    for (ComponentTarget target : ComponentTargetUtil.get().expandTargets(mapping.targets())) {
                        registered.computeIfAbsent(IntIntPair.of(awarenessLevel.getId(), target.ordinal()), _ -> new ArrayList<>(1)).add(mapping.operation());
                    }
                }
            });
        // TODO invert and re-invert
        registered.forEach((key, mappings) -> {
            OptimizedComponentMappings.mappings[key.firstInt()][key.secondInt()] = mappings.toArray(Consumer[]::new);
        });

    }

}
