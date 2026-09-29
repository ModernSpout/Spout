package spout.gamecontent.datadriven.blocktype;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import spout.branding.SpoutNamespace;
import spout.gamecontent.builtin.block.HalfTransparentSlabBlock;
import spout.gamecontent.builtin.block.HalfTransparentStairBlock;
import spout.gamecontent.builtin.block.QuadBlock;
import spout.gamecontent.builtin.block.TransparentSlabBlock;
import spout.gamecontent.builtin.block.TransparentStairBlock;
import spout.gamecontent.builtin.block.VerticalSlabBlock;

/**
 * Built-in values for the {@link BuiltInBlockTypeRegistry#BLOCK_TYPE} registry.
 */
public final class SpoutBlockTypes {

    private SpoutBlockTypes() {
        throw new UnsupportedOperationException();
    }

    public static final SpoutBlockType HALF_TRANSPARENT_SLAB = register(Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, "half_transparent_slab"), HalfTransparentSlabBlock.CODEC);
    public static final SpoutBlockType HALF_TRANSPARENT_STAIR = register(Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, "half_transparent_stair"), HalfTransparentStairBlock.CODEC);
    public static final SpoutBlockType TRANSPARENT_SLAB = register(Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, "transparent_slab"), TransparentSlabBlock.CODEC);
    public static final SpoutBlockType TRANSPARENT_STAIR = register(Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, "transparent_stair"), TransparentStairBlock.CODEC);
    public static final SpoutBlockType QUAD = register(Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, "quad"), QuadBlock.CODEC);
    public static final SpoutBlockType VERTICAL_SLAB = register(Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, "vertical_slab"), VerticalSlabBlock.CODEC);

    private static <B extends Block> SpoutBlockType register(String id, MapCodec<? extends B> codec) {
        return register(Identifier.parse(id), codec);
    }

    private static <B extends Block> SpoutBlockType register(Identifier id, MapCodec<? extends B> codec) {
        return register(id, new CodecSpoutBlockType(id, codec));
    }

    private static SpoutBlockType register(Identifier id, SpoutBlockType blockType) {
        return Registry.register(BuiltInBlockTypeRegistry.BLOCK_TYPE, id, blockType);
    }

    public static SpoutBlockType bootstrap(Registry<SpoutBlockType> registry) {
        return VERTICAL_SLAB;
    }

}
