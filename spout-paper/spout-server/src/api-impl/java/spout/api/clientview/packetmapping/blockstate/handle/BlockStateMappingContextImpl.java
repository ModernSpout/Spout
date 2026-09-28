package spout.api.clientview.packetmapping.blockstate.handle;

import spout.api.clientview.packetmapping.common.context.WithClientViewMappingContextImpl;
import spout.clientview.packetmapping.blockstate.apply.BlockStateMappingsApplicationContext;

public class BlockStateMappingContextImpl extends WithClientViewMappingContextImpl<BlockStateMappingsApplicationContext> implements BlockStateMappingContext {

    public BlockStateMappingContextImpl(BlockStateMappingsApplicationContext handle) {
        super(handle);
    }

    @Override
    public boolean isStateOfPhysicalBlockInWorld() {
        return this.handle.isStateOfPhysicalBlockInWorld();
    }

    @Override
    public int getPhysicalBlockX() {
        return this.handle.getPhysicalBlockX();
    }

    @Override
    public int getPhysicalBlockY() {
        return this.handle.getPhysicalBlockY();
    }

    @Override
    public int getPhysicalBlockZ() {
        return this.handle.getPhysicalBlockZ();
    }

}
