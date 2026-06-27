package spout.gamecontent.datadriven.item.mixin;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import spout.gamecontent.datadriven.item.ItemPropertiesSerializationDecorator;

/**
 * Mixin applying {@link ItemPropertiesSerializationDecorator}
 */
@Mixin(Item.Properties.class)
public abstract class ItemPropertiesSerializationDecoratorItemPropertiesMixin implements ItemPropertiesSerializationDecorator {

    @Unique
    @Nullable DataComponentMap spout$initializedComponents = DataComponentMap.EMPTY;

    @Override
    public @Nullable DataComponentMap spout$initializedComponents() {
        return this.spout$initializedComponents;
    }

    @Override
    public void spout$initializedComponents(@Nullable DataComponentMap initializedComponents) {
        this.spout$initializedComponents = initializedComponents;
    }

}
