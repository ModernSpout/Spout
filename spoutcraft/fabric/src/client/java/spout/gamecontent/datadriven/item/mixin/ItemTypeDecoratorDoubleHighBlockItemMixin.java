package spout.gamecontent.datadriven.item.mixin;

import net.minecraft.world.item.DoubleHighBlockItem;
import org.spongepowered.asm.mixin.Mixin;
import spout.gamecontent.datadriven.item.ItemTypeDecorator;
import spout.gamecontent.datadriven.itemtype.SpoutItemType;
import spout.gamecontent.datadriven.itemtype.SpoutItemTypes;

/**
 * Mixin applying {@link ItemTypeDecorator}
 */
@Mixin(DoubleHighBlockItem.class)
public abstract class ItemTypeDecoratorDoubleHighBlockItemMixin implements ItemTypeDecorator {

    @Override
    public SpoutItemType spout$getItemType() {
        return SpoutItemTypes.DOUBLE_HIGH_BLOCK;
    }

}
