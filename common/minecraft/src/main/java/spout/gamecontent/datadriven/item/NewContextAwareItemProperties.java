package spout.gamecontent.datadriven.item;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jspecify.annotations.Nullable;

/**
 * A utility class to create {@link Item.Properties}
 * instances that have defaults applied to them based on the
 * {@linkplain ContextAwareItemPropertiesDecoding context}.
 */
public final class NewContextAwareItemProperties {

    private NewContextAwareItemProperties() {
        throw new UnsupportedOperationException();
    }

    /**
     * @return A new {@link BlockBehaviour.Properties} instance,
     * potentially with defaults applied.
     */
    public static Item.Properties create(@Nullable ResourceKey<Item> key) {
        Item.Properties properties = new Item.Properties();
        if (key != null) {
            properties.setId(key);
        }
        return properties;
    }

    /**
     * Calls {@link #create(ResourceKey)} with the values currently in
     * {@link ContextAwareItemPropertiesDecoding}.
     */
    public static Item.Properties create() {
        return create(ContextAwareItemPropertiesDecoding.getKey());
    }

}
