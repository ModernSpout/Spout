package spout.common.moredatadriven.minecraft.itemtype;

import net.minecraft.world.item.Item;
import spout.common.moredatadriven.minecraft.common.type.TypeWithCodec;
import spout.common.moredatadriven.minecraft.item.SpoutNonBuiltInItem;

/**
 * An item type, which represents an implementation of a {@link Item}.
 */
public interface SpoutItemType extends TypeWithCodec<Item, SpoutNonBuiltInItem> {
}
