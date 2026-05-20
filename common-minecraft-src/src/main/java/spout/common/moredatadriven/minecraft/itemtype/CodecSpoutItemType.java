package spout.common.moredatadriven.minecraft.itemtype;

import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import spout.common.moredatadriven.minecraft.common.type.ExplicitTypeWithCodec;
import spout.common.moredatadriven.minecraft.item.SpoutNonBuiltInItem;
import spout.common.util.mojang.codec.MapInputAndOps;

/**
 * An implementation of {@link SpoutItemType} defined by its codec.
 */
public final class CodecSpoutItemType extends ExplicitTypeWithCodec<Item, SpoutNonBuiltInItem> implements SpoutItemType {

    public CodecSpoutItemType(Identifier identifier, MapCodec<? extends Item> minecraftCodec) {
        super(identifier, minecraftCodec);
    }

    @Override
    protected SpoutNonBuiltInItem constructForInput(MapInputAndOps<?> input) {
        return new SpoutNonBuiltInItem(this, input);
    }

}
