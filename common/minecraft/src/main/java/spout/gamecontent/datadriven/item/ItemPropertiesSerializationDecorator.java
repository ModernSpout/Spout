package spout.gamecontent.datadriven.item;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;

/**
 * A decorator of {@link Item.Properties} with values
 * that are important for serialization.
 */
public interface ItemPropertiesSerializationDecorator {

    @Nullable DataComponentMap spout$initializedComponents();

    void spout$initializedComponents(@Nullable DataComponentMap initializedComponents);

}
