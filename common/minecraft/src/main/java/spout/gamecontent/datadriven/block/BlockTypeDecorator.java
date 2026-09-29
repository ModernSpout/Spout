package spout.gamecontent.datadriven.block;

import net.minecraft.world.level.block.Block;
import spout.gamecontent.datadriven.blocktype.BuiltInBlockTypeRegistry;
import spout.gamecontent.datadriven.blocktype.SpoutBlockType;
import java.util.Objects;

/**
 * A decorator of {@link Block} that provides the {@link SpoutBlockType}.
 */
public interface BlockTypeDecorator {

    default SpoutBlockType spout$getBlockType() {
        return Objects.requireNonNull(BuiltInBlockTypeRegistry.BLOCK_TYPE.byBlockCodec(((BlockCodecAccessor) this).spout$codec()));
    }

}
