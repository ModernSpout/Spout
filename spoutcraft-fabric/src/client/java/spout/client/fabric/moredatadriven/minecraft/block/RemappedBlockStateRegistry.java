package spout.client.fabric.moredatadriven.minecraft.block;

import net.minecraft.core.IdMapper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

/**
 * The implementation for {@link Block#BLOCK_STATE_REGISTRY}.
 */
public final class RemappedBlockStateRegistry extends IdMapper<BlockState> {

    @Override
    public int getId(BlockState thing) {
        return BlockStateRegistryIdMappings.applyClientToServer(super.getId(thing));
    }

    public int getIdUnmapped(BlockState thing) {
        return super.getId(thing);
    }

    public @Nullable BlockState byId(int id) {
        return super.byId(BlockStateRegistryIdMappings.applyServerToClient(id));
    }

    public @Nullable BlockState byIdUnmapped(int id) {
        return super.byId(id);
    }

}
