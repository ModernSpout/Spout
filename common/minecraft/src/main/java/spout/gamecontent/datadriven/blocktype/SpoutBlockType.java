package spout.gamecontent.datadriven.blocktype;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;
import spout.gamecontent.datadriven.block.SpoutNonBuiltInBlock;
import spout.gamecontent.datadriven.common.type.TypeWithCodec;

/**
 * A block type, which represents an implementation of a {@link Block}.
 */
public interface SpoutBlockType extends TypeWithCodec<Block, SpoutNonBuiltInBlock> {

    @Nullable MapCodec<? extends Block> getBlockClassCodec();

}
