package spout.api.clientview.packetmapping.itemstack.handle;

import spout.api.clientview.packetmapping.common.context.WithClientViewMappingContextImpl;
import spout.clientview.packetmapping.itemstack.apply.ItemStackMappingsApplicationContext;

public class ItemStackMappingContextImpl extends WithClientViewMappingContextImpl<ItemStackMappingsApplicationContext> implements ItemStackMappingContext {

    public ItemStackMappingContextImpl(ItemStackMappingsApplicationContext handle) {
        super(handle);
    }

    @Override
    public boolean isItemStackInItemFrame() {
        return this.handle.isItemStackInItemFrame();
    }

    @Override
    public boolean isStonecutterRecipeResult() {
        return this.handle.isStonecutterRecipeResult();
    }

}
