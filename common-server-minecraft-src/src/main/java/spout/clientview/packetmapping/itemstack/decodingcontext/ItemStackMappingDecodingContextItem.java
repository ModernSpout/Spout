package spout.clientview.packetmapping.itemstack.decodingcontext;

import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.itemstack.ItemStackMapping;

/**
 * Holds an {@link Item}, which can be considered the context of a
 * {@link ItemStackMapping} or a {@link ItemStackMappingMacro} being decoded,
 * and can be used as the target.
 */
public final class ItemStackMappingDecodingContextItem {

    private ItemStackMappingDecodingContextItem() {
        throw new UnsupportedOperationException();
    }

    private static final ThreadLocal<Item> context = new ThreadLocal<>();

    public static @Nullable Item get() {
        return context.get();
    }

    public static void set(Item item) {
        context.set(item);
    }

    public static void remove() {
        context.remove();
    }

}
