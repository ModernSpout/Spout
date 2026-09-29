package spout.clientview.packetmapping.itemstack.apply.hoverevent;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import spout.clientview.packetmapping.clientviewlookup.ThreadLocalClientViewLookup;
import spout.clientview.packetmapping.itemstack.apply.ItemInPacketMappers;

import java.util.function.Function;

/**
 * Holder for mapped {@link ItemStack} codecs.
 */
public final class ClientViewMappedItemStackCodecs {

    private ClientViewMappedItemStackCodecs() {
        throw new UnsupportedOperationException();
    }

    /**
     * A modified version of {@link ItemStack#MAP_CODEC}, which maps the item according to the
     * {@link ThreadLocalClientViewLookup#getThreadLocalClientViewOrFallback}.
     */
    public static final MapCodec<ItemStack> MAP_CODEC = ItemStack.MAP_CODEC.xmap(
        Function.identity(), // Used by io.papermc.paper.adventure.WrapperAwareSerializer#deserialize
        ItemInPacketMappers.ITEM_STACK::apply
    );

    /**
     * A modified version of {@link ItemStackTemplate#MAP_CODEC}, which maps the item according to the
     * {@link ThreadLocalClientViewLookup#getThreadLocalClientViewOrFallback}.
     */
    public static final MapCodec<ItemStackTemplate> TEMPLATE_MAP_CODEC = ItemStackTemplate.MAP_CODEC.xmap(
        Function.identity(), // Used by io.papermc.paper.adventure.WrapperAwareSerializer#deserialize
        ItemInPacketMappers.ITEM_STACK_TEMPLATE::apply
    );

}
