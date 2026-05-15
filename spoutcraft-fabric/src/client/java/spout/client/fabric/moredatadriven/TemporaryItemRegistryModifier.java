package spout.client.fabric.moredatadriven;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;
import spout.client.fabric.moredatadriven.mixin.DataComponentInitializersAccessor;
import spout.common.moredatadriven.minecraft.type.ApplyLazyItemValues;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The {@link TemporaryRegistryModifier} for {@link BuiltInRegistries#ITEM}.
 */
public final class TemporaryItemRegistryModifier extends TemporaryRegistryModifier<Item, DefaultedMappedRegistry<Item>> {

    TemporaryItemRegistryModifier() {
        super((DefaultedMappedRegistry<Item>) BuiltInRegistries.ITEM);
    }

    @Override
    public void add(List<Pair<ResourceKey<Item>, Item>> resources) {
        super.add(resources);
        if (!resources.isEmpty()) {
            // Apply lazy values
            ApplyLazyItemValues.apply(resources.stream().map(Pair::right));
        }
    }

    @Override
    public void removeWhileUnfrozen(List<Pair<ResourceKey<Item>, Item>> resources) {
        // Remove the data component initializers
        Set<ResourceKey<Item>> resourceKeys = resources.stream().map(Pair::left).collect(Collectors.toSet());
        List<?> initializers = ((DataComponentInitializersAccessor) BuiltInRegistries.DATA_COMPONENT_INITIALIZERS).getInitializers();
        initializers.removeIf(initializer -> resourceKeys.contains(getInitializerResourceKey(initializer)));
        // Continue removing
        super.removeWhileUnfrozen(resources);
    }

    /**
     * The {@code DataComponentInitializers.InitializerEntry.key} field, obtained by reflection,
     * or null if not initialized yet.
     */
    private static @Nullable Field initializerResourceKeyField;

    private static ResourceKey<?> getInitializerResourceKey(Object initializer) {
        if (initializerResourceKeyField == null) {
            initializerResourceKeyField = Arrays.stream(initializer.getClass().getDeclaredFields()).filter(field -> field.getType() == ResourceKey.class).findFirst().get();
            initializerResourceKeyField.setAccessible(true);
        }
        try {
            return (ResourceKey<?>) initializerResourceKeyField.get(initializer);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
