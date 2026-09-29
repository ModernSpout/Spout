package spout.api.clientview.packetmapping.component.handle;

import spout.api.clientview.packetmapping.common.context.WithClientViewMappingContextImpl;
import spout.clientview.packetmapping.component.apply.ComponentMappingsApplicationContext;

public class ComponentMappingContextImpl extends WithClientViewMappingContextImpl<ComponentMappingsApplicationContext> implements ComponentMappingContext {

    public ComponentMappingContextImpl(ComponentMappingsApplicationContext handle) {
        super(handle);
    }

}
