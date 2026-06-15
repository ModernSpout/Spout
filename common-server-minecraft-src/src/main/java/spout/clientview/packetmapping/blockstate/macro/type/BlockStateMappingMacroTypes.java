package spout.clientview.packetmapping.blockstate.macro.type;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;
import spout.branding.SpoutNamespace;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockStateMacro;
import spout.clientview.packetmapping.blockstate.macro.LeavesMacro;
import spout.clientview.packetmapping.blockstate.macro.SlabMacro;

/**
 * Holds the existing {@link BlockStateMappingMacroType} values.
 */
public final class BlockStateMappingMacroTypes {

    private BlockStateMappingMacroTypes() {
        throw new UnsupportedOperationException();
    }

    public static final BlockStateMappingMacroType BARREL = register("barrel", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.BARREL)));
    public static final BlockStateMappingMacroType BRUSHABLE = register("brushable", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.SUSPICIOUS_SAND)));
    public static final BlockStateMappingMacroType BUTTON = register("button", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.STONE_BUTTON)));
    public static final BlockStateMappingMacroType CHAIN = register("chain", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.IRON_CHAIN)));
    public static final BlockStateMappingMacroType CHISELED_BOOKSHELF = register("chiseled_bookshelf", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.CHISELED_BOOKSHELF)));
    public static final BlockStateMappingMacroType DOOR = register("door", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.OAK_DOOR)));
    public static final BlockStateMappingMacroType FENCE = register("fence", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.OAK_FENCE)));
    public static final BlockStateMappingMacroType FENCE_GATE = register("fence_gate", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.OAK_FENCE_GATE)));
    public static final BlockStateMappingMacroType FLOWER_POT = register("flower_pot", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.FLOWER_POT)));
    public static final BlockStateMappingMacroType FULL_BLOCK = register("full_block", new TypedConstructorBlockStateMappingMacroType(FromToBlockStateMacro.codecConstructor(Blocks.STONE.defaultBlockState())));
    public static final BlockStateMappingMacroType FURNACE = register("furnace", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.FURNACE)));
    public static final BlockStateMappingMacroType GLAZED_TERRACOTTA = register("glazed_terracotta", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.WHITE_GLAZED_TERRACOTTA)));
    public static final BlockStateMappingMacroType LADDER = register("ladder", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.LADDER)));
    public static final BlockStateMappingMacroType LEAVES = register("leaves", new TypedConstructorBlockStateMappingMacroType(LeavesMacro.codecConstructor(Blocks.OAK_LEAVES)));
    public static final BlockStateMappingMacroType LOOM = register("loom", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.LOOM)));
    public static final BlockStateMappingMacroType NETHER_PORTAL = register("nether_portal", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.NETHER_PORTAL)));
    public static final BlockStateMappingMacroType PRESSURE_PLATE = register("pressure_plate", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.STONE_PRESSURE_PLATE)));
    public static final BlockStateMappingMacroType PUMPKIN = register("pumpkin", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.PUMPKIN)));
    public static final BlockStateMappingMacroType REDSTONE_ORE = register("redstone_ore", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.REDSTONE_ORE)));
    public static final BlockStateMappingMacroType RESPAWN_ANCHOR = register("respawn_anchor", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.RESPAWN_ANCHOR)));
    public static final BlockStateMappingMacroType ROTATED_PILLAR = register("rotated_pillar", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.QUARTZ_PILLAR)));
    public static final BlockStateMappingMacroType SAPLING = register("sapling", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.OAK_SAPLING)));
    public static final BlockStateMappingMacroType SLAB = register("slab", new TypedConstructorBlockStateMappingMacroType(SlabMacro.codecConstructor(Blocks.STONE_SLAB)));
    public static final BlockStateMappingMacroType STAIRS = register("stairs", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.STONE_STAIRS)));
    public static final BlockStateMappingMacroType TRAPDOOR = register("trapdoor", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.OAK_TRAPDOOR)));
    public static final BlockStateMappingMacroType WALL = register("wall", new TypedConstructorBlockStateMappingMacroType(FromToBlockMacro.codecConstructor(Blocks.COBBLESTONE_WALL)));

    private static BlockStateMappingMacroType register(String name, BlockStateMappingMacroType value) {
        return register(Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, name), value);
    }

    private static BlockStateMappingMacroType register(Identifier location, BlockStateMappingMacroType value) {
        return Registry.register(BuiltInBlockStateMappingMacroTypeRegistry.BLOCK_STATE_MAPPING_MACRO_TYPE, location, value);
    }

    public static BlockStateMappingMacroType bootstrap(Registry<BlockStateMappingMacroType> registry) {
        return WALL;
    }

}
