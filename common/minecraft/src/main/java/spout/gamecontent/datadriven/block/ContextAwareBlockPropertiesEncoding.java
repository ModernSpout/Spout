package spout.gamecontent.datadriven.block;

import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

/**
 * Allows {@link BlockPropertiesCodec} to be aware of the encoding context:
 * the {@link Block} being encoded.
 */
public final class ContextAwareBlockPropertiesEncoding {

    private ContextAwareBlockPropertiesEncoding() {
        throw new UnsupportedOperationException();
    }

    /**
     * A thread local holding the current block.
     */
    private static final ThreadLocal<@Nullable Block> block = ThreadLocal.withInitial(() -> null);

    /**
     * @return The current block for which decoding is being done.
     */
    public static @Nullable Block getBlock() {
        return block.get();
    }

    /**
     * Sets the current block for which decoding is being done.
     */
    public static void setBlock(Block block) {
        ContextAwareBlockPropertiesEncoding.block.set(block);
    }

    /**
     * Clears the block that was last set with {@link #setBlock}.
     */
    public static void clearBlock() {
        block.remove();
    }

}
