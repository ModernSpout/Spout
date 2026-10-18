package spout.gamecontent.datadriven.common.registry.delayedfrozen;

import io.papermc.paper.registry.PaperRegistryListenerManager;
import it.unimi.dsi.fastutil.objects.ObjectBooleanImmutablePair;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.CloseableResourceManager;
import org.jspecify.annotations.Nullable;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Provides functions to delay freezing of some registries.
 */
public final class DelayedRegistryFreezing {

    private DelayedRegistryFreezing() {
        throw new UnsupportedOperationException();
    }

    public interface Provider {

        Iterable<ObjectBooleanImmutablePair<ResourceKey<? extends Registry<?>>>> getDelayedFrozenRegistries();

    }

    private static boolean canFreeze = false;

    private static @Nullable List<ObjectBooleanImmutablePair<ResourceKey<? extends Registry<?>>>> delayedFrozenRegistries;
    private static @Nullable Set<ResourceKey<? extends Registry<?>>> delayedFrozenRegistryKeys;

    private static void initializeDelayedFrozenRegistries() {
        if (delayedFrozenRegistries == null) {
            delayedFrozenRegistries = ServiceLoader.load(Provider.class).stream()
                .flatMap(provider -> StreamSupport.stream(provider.get().getDelayedFrozenRegistries().spliterator(), false))
                .toList();
            delayedFrozenRegistryKeys = delayedFrozenRegistries.stream().map(ObjectBooleanImmutablePair::left).collect(Collectors.toSet());
        }
    }

    public static boolean canFreeze(ResourceKey<? extends Registry<?>> registryKey) {
        if (canFreeze) return true;
        initializeDelayedFrozenRegistries();
        return !delayedFrozenRegistryKeys.contains(registryKey);
    }

    public static boolean canFreeze(Registry<?> registry) {
        return canFreeze(registry.key());
    }

    /**
     * Allow freezing from this moment on, and freeze all registries that would have been frozen before.
     */
    public static CompletableFuture<?> freezeDelayedRegistries(CloseableResourceManager resources, Executor mainThreadExecutor) {
        return BeforeDelayedRegistryFreezingActions.runAll(resources, mainThreadExecutor).thenRunAsync(() -> {
            initializeDelayedFrozenRegistries();
            canFreeze = true;
            delayedFrozenRegistries.forEach(pair -> {
                ResourceKey<? extends Registry<?>> key = pair.key();
                if (pair.rightBoolean()) {
                    PaperRegistryListenerManager.INSTANCE.runFreezeListeners((ResourceKey) key, BuiltInRegistries.STATIC_ACCESS_CONVERSIONS); // Paper
                }
                ((Registry<?>) BuiltInRegistries.WRITABLE_REGISTRY.getValue((ResourceKey) key)).freeze();
            });
        });
    }

}
