package spout.gamecontent.builtin.item.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import spout.branding.SpoutNamespace;

/**
 * Analogous to {@link ItemTags}.
 */
public final class SpoutItemTags {

    private SpoutItemTags() {
        throw new UnsupportedOperationException();
    }

    public static final TagKey<Item> PLACED_ON_WATER_BLOCK = create("placed_on_water_block");

    private static TagKey<Item> create(final String name) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, name));
    }

}
