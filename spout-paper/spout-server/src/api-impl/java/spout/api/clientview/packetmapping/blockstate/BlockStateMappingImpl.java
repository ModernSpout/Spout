package spout.api.clientview.packetmapping.blockstate;

import io.papermc.paper.registry.HolderableBase;
import net.minecraft.core.Holder;
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
public sealed abstract class BlockStateMappingImpl extends HolderableBase<spout.clientview.packetmapping.blockstate.BlockStateMapping> implements BlockStateMappingNMS permits DirectBlockStateMappingImpl, FunctionBlockStateMappingImpl {

    protected BlockStateMappingImpl(Holder<spout.clientview.packetmapping.blockstate.BlockStateMapping> holder) {
        super(holder);
    }

    @Override
    public List<? extends ClientView.AwarenessLevel> getAwarenessLevels() {
        return this.getHolder().value().awarenessLevels().stream().map(CraftAwarenessLevel::toBukkit).toList();
    }

    @Override
    public List<? extends BlockData> getFrom() {
        return this.getHolder().value().targets().stream().map(BlockBehaviour.BlockStateBase::asBlockData).toList();
    }

    @Override
    public List<BlockState> getFromNMS() {
        return Collections.unmodifiableList(this.getHolder().value().targets());
    }

    public static BlockStateMappingImpl create(Holder<spout.clientview.packetmapping.blockstate.BlockStateMapping> holder) {
        return holder.value().operation().isDirect() ? new DirectBlockStateMappingImpl(holder) : new FunctionBlockStateMappingImpl(holder);
    }

}
