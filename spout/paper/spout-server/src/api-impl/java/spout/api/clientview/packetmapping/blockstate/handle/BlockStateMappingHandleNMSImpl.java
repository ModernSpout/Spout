package spout.api.clientview.packetmapping.blockstate.handle;

import net.minecraft.world.level.block.state.BlockState;
import spout.api.util.mapping.handle.ProxyWithContextMappingHandleImpl;
import spout.clientview.packetmapping.blockstate.apply.BlockStateMappingHandle;
import spout.clientview.packetmapping.blockstate.apply.BlockStateMappingsApplicationContext;

public final class BlockStateMappingHandleNMSImpl extends ProxyWithContextMappingHandleImpl<BlockState, BlockStateMappingContext, BlockStateMappingsApplicationContext, BlockStateMappingHandle> implements BlockStateMappingHandleNMS {

    public BlockStateMappingHandleNMSImpl(spout.clientview.packetmapping.blockstate.apply.BlockStateMappingHandle handle) {
        super(handle);
    }

    @Override
    protected BlockStateMappingContext mapContextInternalToAPI(final BlockStateMappingsApplicationContext context) {
        return new BlockStateMappingContextImpl(context);
    }

}
