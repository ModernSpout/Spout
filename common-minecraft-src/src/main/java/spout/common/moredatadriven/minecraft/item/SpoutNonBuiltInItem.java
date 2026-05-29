package spout.common.moredatadriven.minecraft.item;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import spout.common.moredatadriven.minecraft.BuiltInSpoutMoreDataDrivenRegistries;
import spout.common.moredatadriven.minecraft.common.nonbuiltin.SpoutNonBuiltInResource;
import spout.common.moredatadriven.minecraft.itemtype.SpoutItemType;
import spout.common.util.mojang.codec.CodecUtil;
import spout.common.util.mojang.codec.MapInputAndOps;

/**
 * A {@link SpoutNonBuiltInResource} for {@link Item}.
 */
public class SpoutNonBuiltInItem extends SpoutNonBuiltInResource<Item, SpoutItemType> {

    /**
     * A codec for {@link SpoutNonBuiltInItem}s.
     */
    public static final MapCodec<SpoutNonBuiltInItem> CODEC = codec(BuiltInSpoutMoreDataDrivenRegistries.ITEM_TYPE);

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
