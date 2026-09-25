package spout.api.clientview.packetmapping.blockstate.registry;

import org.bukkit.Keyed;
import org.bukkit.block.data.BlockData;
import spout.api.clientview.packetmapping.common.registry.AwarenessLevelsMapping;
import spout.api.util.mapping.FromMapping;

/**
 * Always an instance of either {@link DirectBlockStateMapping} or {@link FunctionBlockStateMapping}.
 */
public interface BlockStateMapping extends AwarenessLevelsMapping, FromMapping<BlockData>, Keyed {
}
