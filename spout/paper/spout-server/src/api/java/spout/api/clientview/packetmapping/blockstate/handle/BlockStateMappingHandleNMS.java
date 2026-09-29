package spout.api.clientview.packetmapping.blockstate.handle;

import net.minecraft.world.level.block.state.BlockState;
import spout.api.util.mapping.handle.WithContextMappingHandle;
import spout.api.util.mapping.handle.WithOriginalMappingHandle;

/**
 * NMS analogue for {@link BlockStateMappingHandle}.
 *
 * <p>
 * Note: instances of {@link BlockStateMappingHandle}
 * are not instances of {@link BlockStateMappingHandleNMS}.
 * </p>
 */
public interface BlockStateMappingHandleNMS extends WithContextMappingHandle<BlockState, BlockStateMappingContext>, WithOriginalMappingHandle<BlockState> {
}
