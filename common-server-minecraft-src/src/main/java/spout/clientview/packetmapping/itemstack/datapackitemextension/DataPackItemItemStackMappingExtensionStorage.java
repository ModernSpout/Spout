package spout.clientview.packetmapping.itemstack.datapackitemextension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.world.item.Item;

public final class DataPackItemItemStackMappingExtensionStorage {

    private DataPackItemItemStackMappingExtensionStorage() {
        throw new UnsupportedOperationException();
    }

    static Map<Item, List<DataPackItemItemStackMapping>> mappings = new HashMap<>();

    public static void add(Item item, List<DataPackItemItemStackMapping> mappings) {
        DataPackItemItemStackMappingExtensionStorage.mappings.computeIfAbsent(item, _ -> new ArrayList<>(1)).addAll(mappings);
    }

}
