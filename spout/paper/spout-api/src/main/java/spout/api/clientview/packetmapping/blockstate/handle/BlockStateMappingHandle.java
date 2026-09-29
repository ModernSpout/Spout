package spout.api.clientview.packetmapping.blockstate.handle;

import org.bukkit.block.data.BlockData;
import spout.api.clientview.packetmapping.blockstate.registry.BlockStateMappingRegistryEntry;
import spout.api.util.mapping.handle.WithContextMappingHandle;
import spout.api.util.mapping.handle.WithOriginalMappingHandle;

/**
 * A handle provided to code registered with {@link BlockStateMappingRegistryEntry.Builder#setToFunction}.
 */
public interface BlockStateMappingHandle extends WithContextMappingHandle<BlockData, BlockStateMappingContext>, WithOriginalMappingHandle<BlockData> {
}
