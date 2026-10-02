package spout.api.clientview.packetmapping.itemstack.handle;

import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import spout.api.util.mapping.handle.CrossMappedWithContextMutableMappingHandleImpl;
import spout.clientview.packetmapping.itemstack.apply.ItemStackMappingsApplicationContext;

public final class ItemStackMappingHandleImpl extends CrossMappedWithContextMutableMappingHandleImpl<ItemStack, ItemStack, ItemStackMappingContext, net.minecraft.world.item.ItemStack, net.minecraft.world.item.ItemStack, ItemStackMappingsApplicationContext, spout.clientview.packetmapping.itemstack.apply.ItemStackMappingHandle> implements ItemStackMappingHandle {

    public ItemStackMappingHandleImpl(spout.clientview.packetmapping.itemstack.apply.ItemStackMappingHandle handle) {
        super(handle);
    }

    @Override
    protected net.minecraft.world.item.ItemStack mapAPIToInternal(final ItemStack data) {
        return CraftItemStack.asNMSCopy(data);
    }

    @Override
    protected ItemStack mapInternalToAPI(final net.minecraft.world.item.ItemStack data) {
        return CraftItemStack.asBukkitMirror(data);
    }

    @Override
    protected net.minecraft.world.item.ItemStack mapMutableAPIToInternal(final ItemStack data) {
        return this.mapAPIToInternal(data);
    }

    @Override
    protected ItemStack mapMutableInternalToAPI(final net.minecraft.world.item.ItemStack data) {
        return this.mapInternalToAPI(data);
    }

    @Override
    protected ItemStackMappingContext mapContextInternalToAPI(final ItemStackMappingsApplicationContext context) {
        return new ItemStackMappingContextImpl(context);
    }

}
