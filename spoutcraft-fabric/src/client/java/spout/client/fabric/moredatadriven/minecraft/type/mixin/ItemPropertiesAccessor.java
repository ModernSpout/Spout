package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import net.minecraft.core.component.DataComponentInitializers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.Properties.class)
public interface ItemPropertiesAccessor {

    @Accessor("componentInitializer")
    DataComponentInitializers.Initializer<Item> getComponentInitializer();

    @Accessor("componentInitializer")
    void setComponentInitializer(DataComponentInitializers.Initializer<Item> componentInitializer);

    @Accessor("requiredFeatures")
    FeatureFlagSet getRequiredFeatures();

    @Accessor("requiredFeatures")
    void setRequiredFeatures(FeatureFlagSet requiredFeatures);

    @Accessor("id")
    ResourceKey<Item> getId();

}
