package spout.api.clientview.packetmapping.common.context;

import spout.api.clientview.model.ClientViewImpl;
import spout.api.clientview.model.ClientView;
import spout.clientview.packetmapping.WithClientViewMappingsApplicationContext;

public class WithClientViewMappingContextImpl<H extends WithClientViewMappingsApplicationContext> implements WithClientViewMappingContext {

    protected final H handle;

    public WithClientViewMappingContextImpl(H handle) {
        this.handle = handle;
    }

    @Override
    public ClientView getClientView() {
        return new ClientViewImpl(this.handle.getClientView());
    }

}
