package spout.clientview.packetmapping.blockstate.decodingcontext;

import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;

/**
 * Holds a {@link Block}, which can be considered the context of a
 * {@link BlockStateMapping} or a {@link BlockStateMappingMacro} being decoded,
 * and can be used as the target.
 */
public final class BlockStateMappingDecodingContextBlock {

    private BlockStateMappingDecodingContextBlock() {
        throw new UnsupportedOperationException();
    }

    private static final ThreadLocal<Block> context = new ThreadLocal<>();

    public static @Nullable Block get() {
        return context.get();
    }

    public static void set(Block block) {
        context.set(block);
    }

    public static void remove() {
        context.remove();
    }

}
