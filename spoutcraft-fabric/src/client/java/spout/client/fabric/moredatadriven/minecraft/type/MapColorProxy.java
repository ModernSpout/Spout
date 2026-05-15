package spout.client.fabric.moredatadriven.minecraft.type;

import net.minecraft.world.level.material.MapColor;
import spout.common.util.mojang.codec.ProxyCodec;

/**
 * A proxy enum for {@link MapColor}.
 */
public enum MapColorProxy implements ProxyCodec.Proxy<MapColor> {

    NONE(MapColor.NONE),
    GRASS(MapColor.GRASS),
    SAND(MapColor.SAND),
    WOOL(MapColor.WOOL),
    FIRE(MapColor.FIRE),
    ICE(MapColor.ICE),
    METAL(MapColor.METAL),
    PLANT(MapColor.PLANT),
    SNOW(MapColor.SNOW),
    CLAY(MapColor.CLAY),
    DIRT(MapColor.DIRT),
    STONE(MapColor.STONE),
    WATER(MapColor.WATER),
    WOOD(MapColor.WOOD),
    QUARTZ(MapColor.QUARTZ),
    COLOR_ORANGE(MapColor.COLOR_ORANGE),
    COLOR_MAGENTA(MapColor.COLOR_MAGENTA),
    COLOR_LIGHT_BLUE(MapColor.COLOR_LIGHT_BLUE),
    COLOR_YELLOW(MapColor.COLOR_YELLOW),
    COLOR_LIGHT_GREEN(MapColor.COLOR_LIGHT_GREEN),
    COLOR_PINK(MapColor.COLOR_PINK),
    COLOR_GRAY(MapColor.COLOR_GRAY),
    COLOR_LIGHT_GRAY(MapColor.COLOR_LIGHT_GRAY),
    COLOR_CYAN(MapColor.COLOR_CYAN),
    COLOR_PURPLE(MapColor.COLOR_PURPLE),
    COLOR_BLUE(MapColor.COLOR_BLUE),
    COLOR_BROWN(MapColor.COLOR_BROWN),
    COLOR_GREEN(MapColor.COLOR_GREEN),
    COLOR_RED(MapColor.COLOR_RED),
    COLOR_BLACK(MapColor.COLOR_BLACK),
    GOLD(MapColor.GOLD),
    DIAMOND(MapColor.DIAMOND),
    LAPIS(MapColor.LAPIS),
    EMERALD(MapColor.EMERALD),
    PODZOL(MapColor.PODZOL),
    NETHER(MapColor.NETHER),
    TERRACOTTA_WHITE(MapColor.TERRACOTTA_WHITE),
    TERRACOTTA_ORANGE(MapColor.TERRACOTTA_ORANGE),
    TERRACOTTA_MAGENTA(MapColor.TERRACOTTA_MAGENTA),
    TERRACOTTA_LIGHT_BLUE(MapColor.TERRACOTTA_LIGHT_BLUE),
    TERRACOTTA_YELLOW(MapColor.TERRACOTTA_YELLOW),
    TERRACOTTA_LIGHT_GREEN(MapColor.TERRACOTTA_LIGHT_GREEN),
    TERRACOTTA_PINK(MapColor.TERRACOTTA_PINK),
    TERRACOTTA_GRAY(MapColor.TERRACOTTA_GRAY),
    TERRACOTTA_LIGHT_GRAY(MapColor.TERRACOTTA_LIGHT_GRAY),
    TERRACOTTA_CYAN(MapColor.TERRACOTTA_CYAN),
    TERRACOTTA_PURPLE(MapColor.TERRACOTTA_PURPLE),
    TERRACOTTA_BLUE(MapColor.TERRACOTTA_BLUE),
    TERRACOTTA_BROWN(MapColor.TERRACOTTA_BROWN),
    TERRACOTTA_GREEN(MapColor.TERRACOTTA_GREEN),
    TERRACOTTA_RED(MapColor.TERRACOTTA_RED),
    TERRACOTTA_BLACK(MapColor.TERRACOTTA_BLACK),
    CRIMSON_NYLIUM(MapColor.CRIMSON_NYLIUM),
    CRIMSON_STEM(MapColor.CRIMSON_STEM),
    CRIMSON_HYPHAE(MapColor.CRIMSON_HYPHAE),
    WARPED_NYLIUM(MapColor.WARPED_NYLIUM),
    WARPED_STEM(MapColor.WARPED_STEM),
    WARPED_HYPHAE(MapColor.WARPED_HYPHAE),
    WARPED_WART_BLOCK(MapColor.WARPED_WART_BLOCK),
    DEEPSLATE(MapColor.DEEPSLATE),
    RAW_IRON(MapColor.RAW_IRON),
    GLOW_LICHEN(MapColor.GLOW_LICHEN);

    private final MapColor original;

    MapColorProxy(MapColor original) {
        this.original = original;
    }

    @Override
    public MapColor getOriginal() {
        return this.original;
    }

}
