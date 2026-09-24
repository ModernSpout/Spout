package spout.api.clientview.packetmapping.blockstate.registry;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.block.data.BlockData;
import spout.api.clientview.model.ClientView;
import spout.api.clientview.model.CraftAwarenessLevel;
import java.util.Collections;
import java.util.List;

/**
 * The implementation for {@link BlockStateMapping} and {@link BlockStateMappingNMS}.
 */
public sealed abstract class BlockStateMappingImpl implements BlockStateMappingNMS permits DirectBlockStateMappingImpl, FunctionBlockStateMappingImpl {

    protected final spout.clientview.packetmapping.blockstate.registry.BlockStateMapping handle;

    protected BlockStateMappingImpl(spout.clientview.packetmapping.blockstate.registry.BlockStateMapping handle) {
        this.handle = handle;
    }

    @Override
    public List<? extends ClientView.AwarenessLevel> getAwarenessLevels() {
        return this.handle.awarenessLevels().stream().map(CraftAwarenessLevel::toBukkit).toList();
    }

    @Override
    public List<? extends BlockData> getFrom() {
        return this.handle.targets().stream().map(BlockBehaviour.BlockStateBase::asBlockData).toList();
    }

    @Override
    public List<BlockState> getFromNMS() {
        return Collections.unmodifiableList(this.handle.targets());
    }

    public BlockStateMappingImpl create(spout.clientview.packetmapping.blockstate.registry.BlockStateMapping handle) {
        return handle.operation().isDirect() ? new DirectBlockStateMappingImpl(handle) : new FunctionBlockStateMappingImpl(handle);
    }

}
