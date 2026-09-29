package spout.api.clientview.packetmapping.component.handle;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import spout.api.util.mapping.handle.ProxyWithContextMutableMappingHandleImpl;
import spout.clientview.packetmapping.component.apply.ComponentMappingHandle;
import spout.clientview.packetmapping.component.apply.ComponentMappingsApplicationContext;

public final class ComponentMappingHandleNMSImpl extends ProxyWithContextMutableMappingHandleImpl<Component, MutableComponent, ComponentMappingContext, ComponentMappingsApplicationContext, ComponentMappingHandle> implements ComponentMappingHandleNMS {

    public ComponentMappingHandleNMSImpl(spout.clientview.packetmapping.component.apply.ComponentMappingHandle handle) {
        super(handle);
    }

    @Override
    protected ComponentMappingContext mapContextInternalToAPI(final ComponentMappingsApplicationContext context) {
        return new ComponentMappingContextImpl(context);
    }

}
