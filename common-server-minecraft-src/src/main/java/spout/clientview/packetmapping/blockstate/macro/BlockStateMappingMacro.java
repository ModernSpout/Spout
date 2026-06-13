package spout.clientview.packetmapping.blockstate.macro;

import com.mojang.serialization.MapCodec;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import spout.clientview.packetmapping.blockstate.macro.type.BuiltInBlockStateMappingMacroTypeRegistry;
import spout.util.minecraft.resources.IdentifierUtil;
import java.util.List;
import java.util.function.Consumer;

/**
 * A macro to generate block state mappings.
 */
public class BlockStateMappingMacro {

    public static final MapCodec<BlockStateMappingMacro> CODEC = IdentifierUtil.byNameWithSpoutNamespaceAsDefaultCodec(BuiltInBlockStateMappingMacroTypeRegistry.BLOCK_STATE_MAPPING_MACRO_TYPE).dispatchMap(macro -> macro.type, BlockStateMappingMacroType::codec);

    public final BlockStateMappingMacroType type;
    public final boolean createFromToUsedStatesMappings;
    public final boolean createProxyToVisualDuplicateMappings;
    public final boolean createItemMappings;
    public final boolean createVanillaMappings;
    public final boolean createResourcePackBlockstatesEntries;
    public final @Nullable List<Consumer<UsedStates>> resultConsumers;

    public BlockStateMappingMacro(
        BlockStateMappingMacroType type,
        boolean createFromToUsedStatesMappings,
        boolean createProxyToVisualDuplicateMappings,
        boolean createItemMappings,
        boolean createVanillaMappings,
        boolean createResourcePackBlockstatesEntries,
        @Nullable List<Consumer<UsedStates>> resultConsumers
    ) {
        this.type = type;
        this.createFromToUsedStatesMappings = createFromToUsedStatesMappings;
        this.createProxyToVisualDuplicateMappings = createProxyToVisualDuplicateMappings;
        this.createItemMappings = createItemMappings;
        this.createVanillaMappings = createVanillaMappings;
        this.createResourcePackBlockstatesEntries = createResourcePackBlockstatesEntries;
        this.resultConsumers = resultConsumers;
    }

    public void validateArguments() {
    }

}
