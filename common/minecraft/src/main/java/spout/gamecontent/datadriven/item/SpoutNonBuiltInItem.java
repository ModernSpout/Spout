package spout.gamecontent.datadriven.item;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import spout.gamecontent.datadriven.common.nonbuiltin.SpoutNonBuiltInResource;
import spout.gamecontent.datadriven.itemtype.BuiltInItemTypeRegistry;
import spout.gamecontent.datadriven.itemtype.SpoutItemType;
import spout.util.mojang.codec.CodecUtil;
import spout.util.mojang.codec.MapInputAndOps;

/**
 * A {@link SpoutNonBuiltInResource} for {@link Item}.
 */
public class SpoutNonBuiltInItem extends SpoutNonBuiltInResource<Item, SpoutItemType> {

    /**
     * A codec for {@link SpoutNonBuiltInItem}s.
     */
    public static final MapCodec<SpoutNonBuiltInItem> CODEC = codec(BuiltInItemTypeRegistry.ITEM_TYPE);

    /**
     * A stream codec for {@link SpoutNonBuiltInItem}s.
     */
    public static final StreamCodec<FriendlyByteBuf, SpoutNonBuiltInItem> STREAM_CODEC = CodecUtil.streamViaNBT(CODEC.codec());

    public SpoutNonBuiltInItem(SpoutItemType type, MapInputAndOps<?> input) {
        super(type, input);
    }

    public SpoutNonBuiltInItem(Item value) {
        super(value);
    }

    @Override
    protected SpoutItemType valueToType(Item value) {
        return ((ItemTypeDecorator) value).spout$getItemType();
    }

}
