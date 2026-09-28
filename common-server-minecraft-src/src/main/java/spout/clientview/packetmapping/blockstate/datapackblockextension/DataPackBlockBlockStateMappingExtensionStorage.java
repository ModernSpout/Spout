package spout.clientview.packetmapping.blockstate.datapackblockextension;

import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DataPackBlockBlockStateMappingExtensionStorage {

    private DataPackBlockBlockStateMappingExtensionStorage() {
        throw new UnsupportedOperationException();
    }

    static Map<Block, List<DataPackBlockBlockStateMappingOrMacro>> mappings = new HashMap<>();

    public static void add(Block block, List<DataPackBlockBlockStateMappingOrMacro> mappings) {
        DataPackBlockBlockStateMappingExtensionStorage.mappings.computeIfAbsent(block, _ -> new ArrayList<>(1)).addAll(mappings);
    }

}
