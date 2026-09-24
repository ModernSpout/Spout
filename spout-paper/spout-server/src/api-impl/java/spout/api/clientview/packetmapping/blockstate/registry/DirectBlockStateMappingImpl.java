package spout.api.clientview.packetmapping.blockstate.registry;

import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.block.data.BlockData;
import spout.clientview.packetmapping.blockstate.apply.DirectBlockStateMappingStep;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMapping;

/**
 * The implementation for {@link DirectBlockStateMapping} and {@link DirectBlockStateMappingNMS}.
 */
public final class DirectBlockStateMappingImpl extends BlockStateMappingImpl implements DirectBlockStateMappingNMS {

    DirectBlockStateMappingImpl(final BlockStateMapping handle) {
        super(handle);
    }

    @Override
    public BlockData getTo() {
        return this.getToNMS().asBlockData();
    }

    @Override
    public BlockState getToNMS() {
        return ((DirectBlockStateMappingStep) this.handle.operation()).to();
    }

}
