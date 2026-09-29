package spout.gamecontent.datadriven.item;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;
import spout.gamecontent.datadriven.block.BlockPropertiesCodec;

/**
 * Allows {@link BlockPropertiesCodec} to be aware of the decoding context:
 * the {@link ResourceKey} of the item being decoded.
 */
public final class ContextAwareItemPropertiesDecoding {

    private ContextAwareItemPropertiesDecoding() {
        throw new UnsupportedOperationException();
    }

    /**
     * A thread local holding the current key.
     */
    private static final ThreadLocal<@Nullable ResourceKey<Item>> key = ThreadLocal.withInitial(() -> null);

    /**
     * @return The current key for which decoding is being done.
     */
    public static @Nullable ResourceKey<Item> getKey() {
        return key.get();
    }

    /**
     * Sets the current key for which decoding is being done.
     */
    public static void setKey(ResourceKey<Item> key) {
        ContextAwareItemPropertiesDecoding.key.set(key);
    }

    /**
     * Clears the key that was last set with {@link #setKey}.
     */
    public static void clearKey() {
        key.remove();
    }

}
