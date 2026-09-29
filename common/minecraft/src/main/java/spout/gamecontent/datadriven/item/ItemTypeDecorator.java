package spout.gamecontent.datadriven.item;

import net.minecraft.world.item.Item;
import spout.gamecontent.datadriven.itemtype.SpoutItemType;

/**
 * A decorator of {@link Item} that provides the {@link SpoutItemType}.
 */
public interface ItemTypeDecorator {

    SpoutItemType spout$getItemType();

}
