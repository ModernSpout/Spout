package spout.api.clientview.packetmapping.blockstate.handle;

import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import spout.api.util.mapping.handle.CrossMappedWithContextMappingHandleImpl;
import spout.clientview.packetmapping.blockstate.apply.BlockStateMappingsApplicationContext;

public final class BlockStateMappingHandleImpl extends CrossMappedWithContextMappingHandleImpl<BlockData, BlockStateMappingContext, BlockState, BlockStateMappingsApplicationContext, spout.clientview.packetmapping.blockstate.apply.BlockStateMappingHandle> implements BlockStateMappingHandle {

    public BlockStateMappingHandleImpl(spout.clientview.packetmapping.blockstate.apply.BlockStateMappingHandle handle) {
        super(handle);
    }

    @Override
    protected BlockState mapAPIToInternal(final BlockData data) {
        return ((CraftBlockData) data).getState();
    }

    @Override
    protected BlockData mapInternalToAPI(final BlockState data) {
        return data.asBlockData();
    }

    @Override
    protected BlockStateMappingContext mapContextInternalToAPI(final BlockStateMappingsApplicationContext context) {
        return new BlockStateMappingContextImpl(context);
    }

}
