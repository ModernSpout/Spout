package spout.clientview.packetmapping.blockstate.blockdefinitionextension;

import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BlockDefinitionBlockStateMappingExtensionStorage {

    private BlockDefinitionBlockStateMappingExtensionStorage() {
        throw new UnsupportedOperationException();
    }

    static Map<Block, List<UnappliedDataDrivenBlockMapping>> mappings = new HashMap<>();

    public static void add(Block block, List<UnappliedDataDrivenBlockMapping> mappings) {
        BlockDefinitionBlockStateMappingExtensionStorage.mappings.computeIfAbsent(block, _ -> new ArrayList<>(1)).addAll(mappings);
    }

}
