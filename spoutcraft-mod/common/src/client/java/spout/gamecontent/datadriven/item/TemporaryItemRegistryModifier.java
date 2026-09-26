package spout.gamecontent.datadriven.item;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;
import spout.gamecontent.datadriven.item.mixin.DataComponentInitializersAccessor;
import spout.gamecontent.datadriven.common.registry.temporarymodification.TemporaryRegistryModifier;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * The {@link TemporaryRegistryModifier} for {@link BuiltInRegistries#ITEM}.
 */
public final class TemporaryItemRegistryModifier extends TemporaryRegistryModifier<Item, DefaultedMappedRegistry<Item>> {

    public TemporaryItemRegistryModifier() {
        super((DefaultedMappedRegistry<Item>) BuiltInRegistries.ITEM);
    }

    @Override
    public void add(List<Pair<ResourceKey<Item>, Supplier<Item>>> resources) {
        super.add(resources);
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
            initializerResourceKeyField = Arrays.stream(initializer.getClass().getDeclaredFields())
                .filter(field -> field.getType() == ResourceKey.class)
                .findFirst()
                .orElseThrow();
            initializerResourceKeyField.setAccessible(true);
        }
        try {
            return (ResourceKey<?>) initializerResourceKeyField.get(initializer);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
