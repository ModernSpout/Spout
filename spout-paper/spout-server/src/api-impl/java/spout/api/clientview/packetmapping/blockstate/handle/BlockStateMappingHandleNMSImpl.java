package spout.api.clientview.packetmapping.blockstate.handle;

import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;

public final class BlockStateMappingHandleNMSImpl implements BlockStateMappingHandleNMS {

    private final spout.clientview.packetmapping.blockstate.apply.BlockStateMappingHandle handle;

    public BlockStateMappingHandleNMSImpl(spout.clientview.packetmapping.blockstate.apply.BlockStateMappingHandle handle) {
        this.handle = handle;
    }

    @Override
    public BlockStateMappingContext getContext() {
        return new BlockStateMappingContextImpl(this.handle.getContext());
    }

    @Override
    public BlockState getOriginal() {
        return this.handle.getOriginal();
    }

    @Override
    public BlockState getImmutable() {
        return this.handle.getImmutable();
    }

    @Override
    public void set(BlockState data) {
        this.handle.set(data);
    }

}
