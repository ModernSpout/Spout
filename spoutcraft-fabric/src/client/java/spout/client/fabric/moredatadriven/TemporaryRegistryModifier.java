package spout.client.fabric.moredatadriven;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import spout.client.fabric.moredatadriven.mixin.HolderSetNamedAccessor;
import spout.client.fabric.moredatadriven.mixin.MappedRegistryAccessor;
import org.jspecify.annotations.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * A wrapper around a registry, that can make temporarily additions.
 */
public abstract class TemporaryRegistryModifier<T, R extends MappedRegistry<T>> {

    public final R registry;

    private @Nullable Map<TagKey<T>, HolderSet.Named<T>> originalFrozenTags = null;
    private @Nullable List<Pair<ResourceKey<T>, T>> resourcesAdded = null;

    TemporaryRegistryModifier(R registry) {
        this.registry = registry;
    }

    public MappedRegistryAccessor<T> getRegistryAccessor() {
        return (MappedRegistryAccessor<T>) this.registry;
    }

    public void unfreeze() {
        MappedRegistryAccessor<T> accessor = this.getRegistryAccessor();
        accessor.setFrozen(false);
        if (accessor.getUnregisteredIntrusiveHolders() == null) {
            accessor.setUnregisteredIntrusiveHolders(new IdentityHashMap<>());
        }
        try {
            allTagsField.set(this.registry, unboundMethod.invoke(null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void refreeze() {
        this.registry.freeze();
    }

    public void add(List<Pair<ResourceKey<T>, T>> resources) {
        this.originalFrozenTags = this.copyFrozenTags(this.getRegistryAccessor().getFrozenTags());
        if (!resources.isEmpty()) {
            this.resourcesAdded = resources;
            for (Pair<ResourceKey<T>, T> resource : resources) {
                this.add(resource.left(), resource.right());
            }
        }
    }

    public void add(ResourceKey<T> resourceKey, T resource) {
        Registry.register(this.registry, resourceKey, resource);
    }

    public void addAndRefreeze(List<Pair<ResourceKey<T>, T>> resources) {
        this.add(resources);
        this.refreeze();
    }

    public void remove() {
        if (this.resourcesAdded == null) return;
        this.unfreeze();
        this.removeWhileUnfrozen(this.resourcesAdded);
        this.refreeze();
        this.resourcesAdded = null;
    }

    public void removeWhileUnfrozen(List<Pair<ResourceKey<T>, T>> resources) {
        for (int i = resources.size() - 1; i >= 0; i--) {
            Pair<ResourceKey<T>, T> resource = resources.get(i);
            this.remove(resource.left(), resource.right());
        }
        if (this.originalFrozenTags != null) {
            Map<TagKey<T>, HolderSet.Named<T>> frozenTags = this.getRegistryAccessor().getFrozenTags();
            frozenTags.clear();
            frozenTags.putAll(this.copyFrozenTags(this.originalFrozenTags));
            this.originalFrozenTags = null;
        }
    }

    public void remove(ResourceKey<T> resourceKey, T resource) {

        // Get the inner data structures of the registry
        MappedRegistryAccessor<T> accessor = this.getRegistryAccessor();
        Map<ResourceKey<T>, Holder.Reference<T>> byKey = accessor.getByKey();
        Map<Identifier, Holder.Reference<T>> byLocation = accessor.getByLocation();
        Map<T, Holder.Reference<T>> byValue = accessor.getByValue();
        Map<ResourceKey<T>, RegistrationInfo> registrationInfos = accessor.getRegistrationInfos();
        ObjectList<Holder.Reference<T>> byId = accessor.getById();
        var toId = accessor.getToId();

        // Remove the resource from the inner data structures
        byKey.remove(resourceKey);
        byLocation.remove(resourceKey.identifier());
        byValue.remove(resource);
        int id = toId.removeInt(resource);
        byId.remove(id);
        registrationInfos.remove(resourceKey);

    }

    private static Field allTagsField;
    private static Method unboundMethod;
    static {
        try {
            allTagsField = Arrays.stream(MappedRegistry.class.getDeclaredFields())
                .filter(f -> Modifier.isPrivate(f.getModifiers()))
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .filter(f -> Arrays.asList(MappedRegistry.class.getDeclaredClasses()).contains(f.getType()))
                .findFirst()
                .orElseThrow();
            allTagsField.setAccessible(true);
            unboundMethod = Arrays.stream(allTagsField.getType().getDeclaredMethods())
                .filter(m -> !Modifier.isPrivate(m.getModifiers()) && !Modifier.isProtected(m.getModifiers()))
                .filter(m -> Modifier.isStatic(m.getModifiers()))
                .filter(m -> m.getParameterCount() == 0)
                .findFirst()
                .orElseThrow();
            unboundMethod.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<TagKey<T>, HolderSet.Named<T>> copyFrozenTags(Map<TagKey<T>, HolderSet.Named<T>> source) {
        Map<TagKey<T>, HolderSet.Named<T>> target = new IdentityHashMap<>();
        for (Map.Entry<TagKey<T>, HolderSet.Named<T>> entry : source.entrySet()) {
            HolderSet.Named<T> holderSet = this.getRegistryAccessor().callCreateTag(entry.getValue().key());
            @Nullable List<Holder<T>> contents = ((HolderSetNamedAccessor<T>) entry.getValue()).getContents();
            if (contents != null) {
                ((HolderSetNamedAccessor<T>) holderSet).callBind(new ArrayList<>(contents));
            }
            target.put(entry.getKey(), holderSet);
        }
        return target;
    }

}
