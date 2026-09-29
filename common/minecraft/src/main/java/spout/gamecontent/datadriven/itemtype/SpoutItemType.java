package spout.gamecontent.datadriven.itemtype;

import net.minecraft.world.item.Item;
import spout.gamecontent.datadriven.common.type.TypeWithCodec;
import spout.gamecontent.datadriven.item.SpoutNonBuiltInItem;

/**
 * An item type, which represents an implementation of a {@link Item}.
 */
public interface SpoutItemType extends TypeWithCodec<Item, SpoutNonBuiltInItem> {
}
