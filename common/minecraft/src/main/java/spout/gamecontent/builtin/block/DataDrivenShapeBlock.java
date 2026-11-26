package spout.gamecontent.builtin.block;

import net.minecraft.world.phys.AABB;
import spout.gamecontent.datadriven.block.subtypes.BlockStateFunction;

public interface DataDrivenShapeBlock {

    BlockStateFunction<AABB> getShape();

}
