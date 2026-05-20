package spout.common.moredatadriven.minecraft.common.dependent;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.Nullable;
import spout.common.util.minecraft.resources.KeyedValue;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * A utility class for sorting {@link DependentNonBuiltInResource}
 * into an order that is valid.
 */
public final class SortDependentDataDrivenResources {

    private SortDependentDataDrivenResources() {
        throw new UnsupportedOperationException();
    }

    /**
     * A node in a directed acyclic graph.
     */
    private static class DAGNode<K, T> {

        public final ResourceKey<K> key;
        public final T resource;

        public @Nullable List<DAGNode<K, T>> dependents;
        public int requiredLeft;

        public DAGNode(ResourceKey<K> key, T resource) {
            this.key = key;
            this.resource = resource;
        }

    }

    public static <T extends DependentNonBuiltInResource> Stream<Pair<ResourceKey<T>, T>> sortedRegistry(Registry<T> registry) {
        return sorted(registry.entrySet().stream().map(entry -> Pair.of(entry.getKey(), entry.getValue())));
    }

    public static <K, T extends DependentNonBuiltInResource> Stream<Pair<ResourceKey<K>, T>> sortedKeyedResources(Registry<K> registry, Stream<KeyedValue<T>> resources) {
        return sortedKeyedResources(registry.key(), resources);
    }

    public static <K, T extends DependentNonBuiltInResource> Stream<Pair<ResourceKey<K>, T>> sortedKeyedResources(ResourceKey<? extends Registry<K>> registryKey, Stream<KeyedValue<T>> resources) {
        return sorted(resources.map(value -> Pair.of(ResourceKey.create(registryKey, value.identifier()), value.value())));
    }

    public static <K, T extends DependentNonBuiltInResource> Stream<Pair<ResourceKey<K>, T>> sorted(Stream<Pair<ResourceKey<K>, T>> resources) {
        // Create the nodes
        Map<Identifier, DAGNode<K, T>> nodesByIdentifier = new LinkedHashMap<>();
        resources.forEach(pair -> nodesByIdentifier.put(pair.left().identifier(), new DAGNode<>(pair.left(), pair.right())));
        // Link the nodes
        nodesByIdentifier.values().forEach(node -> {
            for (Identifier requiredResource : node.resource.getRequiredResources()) {
                DAGNode<K, T> requiredResourceNode = nodesByIdentifier.get(requiredResource);
                if (requiredResourceNode != null) {
                    if (requiredResourceNode.dependents == null) {
                        requiredResourceNode.dependents = new ArrayList<>(1);
                    }
                    requiredResourceNode.dependents.add(node);
                    node.requiredLeft++;
                }
            }
        });
        // List the nodes in a valid order
        List<DAGNode<K, T>> ready = new ArrayList<>(nodesByIdentifier.size());
        nodesByIdentifier.forEach((_, node) -> {
            if (node.requiredLeft == 0) {
                ready.add(node);
            }
        });
        List<DAGNode<K, T>> result = new ArrayList<>(nodesByIdentifier.size());
        while (!ready.isEmpty()) {
            DAGNode<K, T> next = ready.removeLast();
            if (next.dependents != null) {
                next.dependents.forEach(dependent -> {
                    if (--dependent.requiredLeft == 0) {
                        ready.add(dependent);
                    }
                });
            }
            result.add(next);
        }
        // Return the result
        return result.stream().map(node -> Pair.of(node.key, node.resource));
    }

}
