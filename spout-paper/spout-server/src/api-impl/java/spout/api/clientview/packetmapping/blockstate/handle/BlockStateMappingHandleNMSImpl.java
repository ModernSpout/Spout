package spout.api.clientview.packetmapping.blockstate.handle;

import net.minecraft.world.level.block.state.BlockState;
import spout.api.util.mapping.handle.ProxyMappingHandleImpl;
import spout.clientview.packetmapping.blockstate.apply.BlockStateMappingsApplicationContext;

public final class BlockStateMappingHandleNMSImpl extends ProxyMappingHandleImpl<BlockState, BlockStateMappingContext, BlockStateMappingsApplicationContext, spout.clientview.packetmapping.blockstate.apply.BlockStateMappingHandle> implements BlockStateMappingHandleNMS {

    public BlockStateMappingHandleNMSImpl(spout.clientview.packetmapping.blockstate.apply.BlockStateMappingHandle handle) {
        super(handle);
    }

    @Override
    protected BlockStateMappingContext mapContextInternalToAPI(final BlockStateMappingsApplicationContext context) {
        return new BlockStateMappingContextImpl(context);
    }

}
