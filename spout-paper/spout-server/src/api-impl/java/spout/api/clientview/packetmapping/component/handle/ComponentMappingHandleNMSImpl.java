package spout.api.clientview.packetmapping.component.handle;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import spout.api.util.mapping.handle.ProxyMutableMappingHandleImpl;
import spout.clientview.packetmapping.component.apply.ComponentMappingsApplicationContext;

public final class ComponentMappingHandleNMSImpl extends ProxyMutableMappingHandleImpl<Component, MutableComponent, ComponentMappingContext, ComponentMappingsApplicationContext, spout.clientview.packetmapping.component.apply.ComponentMappingHandle> implements ComponentMappingHandleNMS {

    public ComponentMappingHandleNMSImpl(spout.clientview.packetmapping.component.apply.ComponentMappingHandle handle) {
        super(handle);
    }

    @Override
    protected ComponentMappingContext mapContextInternalToAPI(final ComponentMappingsApplicationContext context) {
        return new ComponentMappingContextImpl(context);
    }

}
