package spout.gamecontent.datadriven.item.mixin;

import net.minecraft.world.item.EggItem;
import org.spongepowered.asm.mixin.Mixin;
import spout.gamecontent.datadriven.item.ItemTypeDecorator;
import spout.gamecontent.datadriven.itemtype.SpoutItemType;
import spout.gamecontent.datadriven.itemtype.SpoutItemTypes;

/**
 * Mixin applying {@link ItemTypeDecorator}
 */
@Mixin(EggItem.class)
public abstract class ItemTypeDecoratorEggItemMixin implements ItemTypeDecorator {

    @Override
    public SpoutItemType spout$getItemType() {
        return SpoutItemTypes.EGG;
    }

}
