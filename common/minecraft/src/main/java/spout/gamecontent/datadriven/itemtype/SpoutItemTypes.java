package spout.gamecontent.datadriven.itemtype;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.Item;
import spout.gamecontent.datadriven.item.ItemCodecs;

/**
 * Built-in values for the {@link BuiltInItemTypeRegistry#ITEM_TYPE} registry.
 */
public class SpoutItemTypes {

    private SpoutItemTypes() {
        throw new UnsupportedOperationException();
    }

    public static final SpoutItemType ITEM = register("item", ItemCodecs.simpleCodec(Item::new));
    public static final SpoutItemType BLOCK = register("block", ItemCodecs.blockCodec(BlockItem::new));
    public static final SpoutItemType DOUBLE_HIGH_BLOCK = register("double_high_block", ItemCodecs.blockCodec(DoubleHighBlockItem::new));
    public static final SpoutItemType EGG = register("egg", ItemCodecs.simpleCodec(EggItem::new));
    // TODO others

    private static SpoutItemType register(String id, MapCodec<? extends Item> codec) {
        return register(Identifier.parse(id), codec);
    }

    private static SpoutItemType register(Identifier id, MapCodec<? extends Item> codec) {
        return register(id, new CodecSpoutItemType(id, codec));
    }

    private static SpoutItemType register(Identifier id, SpoutItemType itemType) {
        return Registry.register(BuiltInItemTypeRegistry.ITEM_TYPE, id, itemType);
    }

    public static SpoutItemType bootstrap(Registry<SpoutItemType> registry) {
        return EGG;
    }

}
