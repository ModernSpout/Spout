package spout.server.paper.impl.moredatadriven.datapack;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import spout.gamecontent.datadriven.common.dependent.DependentNonBuiltInResource;
import spout.gamecontent.datadriven.common.dependent.SortDependentDataDrivenResources;
import spout.gamecontent.datadriven.common.nonbuiltin.SpoutNonBuiltInResource;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Provides functionality for adding resources based on definitions.
 */
public final class CopyResourcesFromDataPackRegistryToInternalRegistry {

    private CopyResourcesFromDataPackRegistryToInternalRegistry() {
        throw new UnsupportedOperationException();
    }

    public static <T> void copy(Stream<Pair<ResourceKey<T>, Supplier<T>>> keyedResources, Registry<T> internalRegistry) {
        keyedResources.forEach(keyedDefinition -> {
            ResourceKey<T> key = keyedDefinition.first();
            T resource = keyedDefinition.right().get();
            Registry.register(internalRegistry, key, resource);
        });
    }

    public static <R extends DependentNonBuiltInResource, V> void copyDependent(Registry<R> dataPackRegistry, Registry<V> internalRegistry, BiFunction<ResourceKey<V>, R, V> transform) {
        copy(
            SortDependentDataDrivenResources.sortedRegistry(dataPackRegistry).map(dataPackRegistryElement -> {
                ResourceKey<V> key = ResourceKey.create(internalRegistry.key(), dataPackRegistryElement.left().identifier());
                return Pair.of(key, () -> transform.apply(key, dataPackRegistryElement.right()));
            }),
            internalRegistry
        );
    }

    public static <R extends SpoutNonBuiltInResource<V, ?>, V> void copyInitialized(Registry<R> dataPackRegistry, Registry<V> internalRegistry, Function<BiFunction<ResourceKey<V>, R, V>, BiFunction<ResourceKey<V>, R, V>> transformTransform) {
        copyDependent(
            dataPackRegistry,
            internalRegistry,
            transformTransform.apply((_, resource) -> {
                resource.initializeValueFromInput(true);
                return resource.getValue();
            })
        );
    }

    public static <R extends SpoutNonBuiltInResource<V, ?>, V> void copyInitialized(Registry<R> dataPackRegistry, Registry<V> internalRegistry) {
        copyInitialized(dataPackRegistry, internalRegistry, Function.identity());
    }

}
