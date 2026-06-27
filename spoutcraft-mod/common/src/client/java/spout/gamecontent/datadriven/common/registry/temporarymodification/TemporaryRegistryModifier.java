package spout.gamecontent.datadriven.common.registry.temporarymodification;

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
import spout.gamecontent.datadriven.common.registry.temporarymodification.mixin.HolderSetNamedAccessor;
import spout.gamecontent.datadriven.common.registry.temporarymodification.mixin.MappedRegistryAccessor;
import org.jspecify.annotations.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A wrapper around a registry, that can make temporarily additions.
 */
public abstract class TemporaryRegistryModifier<T, R extends MappedRegistry<T>> {

    public final R registry;

    private @Nullable Map<TagKey<T>, HolderSet.Named<T>> originalFrozenTags = null;
    private @Nullable ArrayList<Pair<ResourceKey<T>, T>> resourcesAdded = null;

    public TemporaryRegistryModifier(R registry) {
        this.registry = registry;
    }

    @SuppressWarnings("unchecked")
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

    public void add(List<Pair<ResourceKey<T>, Supplier<T>>> resources) {
        this.originalFrozenTags = this.copyFrozenTags(this.getRegistryAccessor().getFrozenTags());
        if (!resources.isEmpty()) {
            if (this.resourcesAdded == null) {
                this.resourcesAdded = new ArrayList<>(resources.size());
            } else {
                this.resourcesAdded.ensureCapacity(resources.size());
            }
            for (Pair<ResourceKey<T>, Supplier<T>> resource : resources) {
                T builtResource = resource.right().get();
                this.resourcesAdded.add(Pair.of(resource.left(), builtResource));
                this.add(resource.left(), builtResource);
            }
        }
    }

    public void add(ResourceKey<T> resourceKey, T resource) {
        Registry.register(this.registry, resourceKey, resource);
    }

    public void addAndRefreeze(List<Pair<ResourceKey<T>, Supplier<T>>> resources) {
        this.add(resources);
        this.refreeze();
    }

    public void remove() {
        if (this.resourcesAdded == null) return;
        this.unfreeze();
        this.removeWhileUnfrozen(this.resourcesAdded);
        this.refreeze();
        this.resourcesAdded.clear();
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

    private static final Field allTagsField;
    private static final Method unboundMethod;
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
            List<Holder<T>> contents = this.getHolderSetAccessor(entry.getValue()).getContents();
            if (contents != null) {
                this.getHolderSetAccessor(holderSet).callBind(new ArrayList<>(contents));
            }
            target.put(entry.getKey(), holderSet);
        }
        return target;
    }

    @SuppressWarnings("unchecked")
    private HolderSetNamedAccessor<T> getHolderSetAccessor(HolderSet.Named<T> holderSet) {
        return (HolderSetNamedAccessor<T>) holderSet;
    }

}
