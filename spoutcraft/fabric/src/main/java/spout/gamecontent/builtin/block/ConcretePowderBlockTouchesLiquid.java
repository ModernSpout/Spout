package spout.gamecontent.builtin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.state.BlockState;

public final class ConcretePowderBlockTouchesLiquid {

    private ConcretePowderBlockTouchesLiquid() {
        throw new UnsupportedOperationException();
    }

    public static boolean touchesLiquid(BlockGetter level, BlockPos pos, BlockState state) {
        return ConcretePowderBlock.touchesLiquid(level, pos);
    }

}
