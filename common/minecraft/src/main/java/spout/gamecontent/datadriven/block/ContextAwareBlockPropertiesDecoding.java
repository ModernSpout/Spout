package spout.gamecontent.datadriven.block;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;
import spout.gamecontent.datadriven.blocktype.SpoutBlockType;

/**
 * Allows {@link BlockPropertiesCodec} to be aware of the decoding context:
 * the {@link SpoutBlockType} and {@link ResourceKey} of the block being decoded.
 */
public final class ContextAwareBlockPropertiesDecoding {

    private ContextAwareBlockPropertiesDecoding() {
        throw new UnsupportedOperationException();
    }

    /**
     * A thread local holding the current type.
     */
    private static final ThreadLocal<@Nullable SpoutBlockType> type = ThreadLocal.withInitial(() -> null);

    /**
     * A thread local holding the current key.
     */
    private static final ThreadLocal<@Nullable ResourceKey<Block>> key = ThreadLocal.withInitial(() -> null);

    /**
     * @return The current type for which decoding is being done.
     */
    public static @Nullable SpoutBlockType getType() {
        return type.get();
    }

    /**
     * Sets the current type for which decoding is being done.
     */
    public static void setType(SpoutBlockType type) {
        ContextAwareBlockPropertiesDecoding.type.set(type);
    }

    /**
     * Clears the type that was last set with {@link #setType}.
     */
    public static void clearType() {
        type.remove();
    }

    /**
     * @return The current key for which decoding is being done.
     */
    public static @Nullable ResourceKey<Block> getKey() {
        return key.get();
    }

    /**
     * Sets the current key for which decoding is being done.
     */
    public static void setKey(ResourceKey<Block> key) {
        ContextAwareBlockPropertiesDecoding.key.set(key);
    }

    /**
     * Clears the key that was last set with {@link #setKey}.
     */
    public static void clearKey() {
        key.remove();
    }

}
