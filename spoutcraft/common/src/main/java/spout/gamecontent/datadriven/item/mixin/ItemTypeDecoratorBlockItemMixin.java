package spout.gamecontent.datadriven.item.mixin;

import net.minecraft.world.item.BlockItem;
import org.spongepowered.asm.mixin.Mixin;
import spout.gamecontent.datadriven.item.ItemTypeDecorator;
import spout.gamecontent.datadriven.itemtype.SpoutItemType;
import spout.gamecontent.datadriven.itemtype.SpoutItemTypes;

/**
 * Mixin applying {@link ItemTypeDecorator}
 */
@Mixin(BlockItem.class)
public abstract class ItemTypeDecoratorBlockItemMixin implements ItemTypeDecorator {

    @Override
    public SpoutItemType spout$getItemType() {
        return SpoutItemTypes.BLOCK;
    }

}
