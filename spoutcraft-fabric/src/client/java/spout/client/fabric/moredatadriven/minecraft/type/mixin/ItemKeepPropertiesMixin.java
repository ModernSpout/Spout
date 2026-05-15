package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import net.minecraft.world.item.Item;
import spout.client.fabric.moredatadriven.minecraft.type.WithItemProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class ItemKeepPropertiesMixin implements WithItemProperties {

    @Unique
    private Item.Properties properties;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void savePropertiesOnInit(Item.Properties properties, CallbackInfo ci) {
        this.properties = properties;
    }

    @Override
    public Item.Properties getItemProperties() {
        return this.properties;
    }

}
