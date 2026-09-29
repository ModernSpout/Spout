package spout.api.clientview.packetmapping.itemstack.handle;

import net.minecraft.world.item.ItemStack;
import spout.api.util.mapping.handle.ProxyWithContextMutableMappingHandleImpl;
import spout.clientview.packetmapping.itemstack.apply.ItemStackMappingHandle;
import spout.clientview.packetmapping.itemstack.apply.ItemStackMappingsApplicationContext;

public final class ItemStackMappingHandleNMSImpl extends ProxyWithContextMutableMappingHandleImpl<ItemStack, ItemStack, ItemStackMappingContext, ItemStackMappingsApplicationContext, ItemStackMappingHandle> implements ItemStackMappingHandleNMS {

    public ItemStackMappingHandleNMSImpl(spout.clientview.packetmapping.itemstack.apply.ItemStackMappingHandle handle) {
        super(handle);
    }

    @Override
    protected ItemStackMappingContext mapContextInternalToAPI(final ItemStackMappingsApplicationContext context) {
        return new ItemStackMappingContextImpl(context);
    }

}
