package spout.gamecontent.datadriven.common.delayedfrozenregistries;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.Nullable;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

/**
 * Provides functions to delay freezing of some registries.
 */
public final class DelayedRegistryFreezing {

    private DelayedRegistryFreezing() {
        throw new UnsupportedOperationException();
    }

    public interface Provider {

        Iterable<ResourceKey<? extends Registry<?>>> getDelayedFrozenRegistries();

    }

    private static boolean canFreeze = false;

    private static @Nullable List<ResourceKey<? extends Registry<?>>> delayedFrozenRegistries;

    private static void initializeDelayedFrozenRegistries() {
        if (delayedFrozenRegistries == null) {
            delayedFrozenRegistries = ServiceLoader.load(Provider.class).stream()
                .flatMap(provider -> StreamSupport.stream(provider.get().getDelayedFrozenRegistries().spliterator(), false))
                .toList();
        }
    }

    public static boolean canFreeze(ResourceKey<? extends Registry<?>> registryKey) {
        if (canFreeze) return true;
        initializeDelayedFrozenRegistries();
        return !delayedFrozenRegistries.contains(registryKey);
    }

    public static boolean canFreeze(Registry<?> registry) {
        return canFreeze(registry.key());
    }

    /**
     * Allow freezing from this moment on, and freeze all registries that would have been frozen before.
     */
    public static void freezeDelayedRegistries() {
        initializeDelayedFrozenRegistries();
        canFreeze = true;
        delayedFrozenRegistries.forEach(key -> {
            ((Registry<?>) BuiltInRegistries.WRITABLE_REGISTRY.getValue((ResourceKey) key)).freeze();
        });
    }

}
