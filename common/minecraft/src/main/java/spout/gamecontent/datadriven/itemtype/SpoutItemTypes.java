package spout.gamecontent.datadriven.itemtype;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.Item;
import spout.branding.SpoutNamespace;
import spout.gamecontent.datadriven.item.ItemCodecs;

/**
 * Built-in values for the {@link BuiltInItemTypeRegistry#ITEM_TYPE} registry.
 */
public class SpoutItemTypes {

    private SpoutItemTypes() {
        throw new UnsupportedOperationException();
    }

    public static final SpoutItemType ITEM = register("item", ItemCodecs.simpleCodec(Item::new), Item.class);
    public static final SpoutItemType BLOCK = register("block", ItemCodecs.blockCodec(BlockItem::new), BlockItem.class);
    public static final SpoutItemType DOUBLE_HIGH_BLOCK = register("double_high_block", ItemCodecs.blockCodec(DoubleHighBlockItem::new), DoubleHighBlockItem.class);
    public static final SpoutItemType EGG = register("egg", ItemCodecs.simpleCodec(EggItem::new), EggItem.class);
    // TODO others

    private static SpoutItemType register(String id, MapCodec<? extends Item> codec, Class<? extends Item> baseClass) {
        return register(Identifier.parse(id), codec, baseClass);
    }

    private static SpoutItemType registerSpout(String path, MapCodec<? extends Item> codec, Class<? extends Item> baseClass) {
        return register(Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, path), codec, baseClass);
    }

    private static SpoutItemType register(Identifier id, MapCodec<? extends Item> codec, Class<? extends Item> baseClass) {
        return register(id, new CodecSpoutItemType(id, codec, baseClass));
    }

    private static SpoutItemType register(Identifier id, SpoutItemType itemType) {
        return Registry.register(BuiltInItemTypeRegistry.ITEM_TYPE, id, itemType);
    }

    public static SpoutItemType bootstrap(Registry<SpoutItemType> registry) {
        return EGG;
    }

}
