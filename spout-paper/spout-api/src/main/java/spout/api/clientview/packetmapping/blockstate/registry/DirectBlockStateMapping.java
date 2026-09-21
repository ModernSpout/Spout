package spout.api.clientview.packetmapping.blockstate.registry;

import org.bukkit.block.data.BlockData;
import spout.api.util.mapping.ToMapping;

public interface DirectBlockStateMapping extends BlockStateMapping, ToMapping<BlockData> {
}
