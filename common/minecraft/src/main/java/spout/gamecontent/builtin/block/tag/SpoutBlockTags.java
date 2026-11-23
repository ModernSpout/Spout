package spout.gamecontent.builtin.block.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import spout.branding.SpoutNamespace;

/**
 * Analogous to {@link BlockTags}.
 */
public final class SpoutBlockTags {

    private SpoutBlockTags() {
        throw new UnsupportedOperationException();
    }

    public static final TagKey<Block> SUPPORTS_AMPHIBIOUS_STALKED_PLANTS = create("supports_amphibious_stalked_plants");

    private static TagKey<Block> create(final String name) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, name));
    }

}
