package spout.gamecontent.builtin.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import spout.gamecontent.builtin.block.BreathingAmphibiousStalkedPlantStalkBlock;

public class BreathingAmphibiousStalkedPlantItem extends BlockItem {

    public BreathingAmphibiousStalkedPlantItem(final Block block, final Item.Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(final BlockPlaceContext context, final BlockState placementState) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (level.getBlockState(pos).is(this.getBlock())) {
            return super.placeBlock(context, placementState);
        }
        boolean result = super.placeBlock(context, placementState);
        if (result) {
            if (placementState.is(this.getBlock())) {
                ((BreathingAmphibiousStalkedPlantStalkBlock) this.getBlock()).placeMatchingBlocksAbove(level, pos, level.getBlockState(pos));
            }
        }
        return result;
    }

}
