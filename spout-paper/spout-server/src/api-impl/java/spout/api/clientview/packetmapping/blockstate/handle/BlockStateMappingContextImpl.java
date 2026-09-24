package spout.api.clientview.packetmapping.blockstate.handle;

import spout.api.clientview.packetmapping.common.context.WithClientViewMappingContextImpl;
import spout.clientview.packetmapping.blockstate.apply.BlockStateMappingsApplicationContext;

public class BlockStateMappingContextImpl extends WithClientViewMappingContextImpl implements BlockStateMappingContext {

    private final BlockStateMappingsApplicationContext handle;

    public BlockStateMappingContextImpl(BlockStateMappingsApplicationContext handle) {
        super(handle);
        this.handle = handle;
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
