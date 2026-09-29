package spout.util.minecraft.registry;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Holds listeners that are invoked on certain registry hooks.
 */
public final class SpoutRegistryHookEvents {

    private SpoutRegistryHookEvents() {
        throw new UnsupportedOperationException();
    }

    public interface Listener<T> {

        Iterable<Pair<ResourceKey<Registry<T>>, EventType>> getRegistryHookEventsToListenFor();

        void onRegistryHookEvent(EventType type, WritableRegistry<T> registry);

    }

    /**
     * The listeners, or null if not initialized yet.
     */
    private static @Nullable Map<Pair<ResourceKey<Registry<?>>, EventType>, List<Listener<?>>> listeners;

    public static void fireEvent(EventType type, WritableRegistry<?> registry) {
        if (listeners == null) {
            // Initialize listeners
            listeners = new HashMap<>();
            ServiceLoader.load(Listener.class).forEach(listener -> {
                listener.getRegistryHookEventsToListenFor().forEach(pair -> {
                    listeners.computeIfAbsent((Pair) pair, _ -> new ArrayList<>(1)).add(listener);
                });
            });
        }
        @Nullable List<Listener<?>> relevantListeners = listeners.get(Pair.of(registry.key(), type));
        if (relevantListeners != null) {
            relevantListeners.forEach(listener -> listener.onRegistryHookEvent(type, (WritableRegistry) registry));
        }
    }

    /**
     * A type of registry hook.
     */
    public enum EventType {

        /**
         * Invoked just before a registry is being frozen.
         */
        PRE_FREEZE,

        /**
         * Invoked just after a registry has been frozen.
         */
        POST_FREEZE,

        /**
         * Invoked just before static (also known as experimental)
         * registries are populated from resources (from data packs) and Paper plugins.
         */
        PRE_POPULATE_STATIC_REGISTRY,

        /**
         * Invoked just after static (also known as experimental)
         * registries are populated from resources (from data packs) and Paper plugins.
         */
        POST_POPULATE_STATIC_REGISTRY;

    }

}
