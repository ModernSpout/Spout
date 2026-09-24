package spout.api.clientview.packetmapping.blockstate.handle;

import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;

public final class BlockStateMappingHandleImpl implements BlockStateMappingHandle {

    private final spout.clientview.packetmapping.blockstate.apply.BlockStateMappingHandle handle;

    public BlockStateMappingHandleImpl(spout.clientview.packetmapping.blockstate.apply.BlockStateMappingHandle handle) {
        this.handle = handle;
    }

    @Override
    public BlockStateMappingContext getContext() {
        return new BlockStateMappingContextImpl(this.handle.getContext());
    }

    @Override
    public BlockData getOriginal() {
        return this.handle.getOriginal().asBlockData();
    }

    @Override
    public BlockData getImmutable() {
        return this.handle.getImmutable().asBlockData();
    }

    @Override
    public void set(BlockData data) {
        this.handle.set(((CraftBlockData) data).getState());
    }

}
