package spout.api.clientview.packetmapping.common.context;

import spout.api.clientview.model.ClientView;
import spout.clientview.packetmapping.WithClientViewMappingsApplicationContext;

public class WithClientViewMappingContextImpl implements WithClientViewMappingContext {

    private final WithClientViewMappingsApplicationContext handle;

    public WithClientViewMappingContextImpl(WithClientViewMappingsApplicationContext handle) {
        this.handle = handle;
    }

    @Override
    public ClientView getClientView() {
        return this.handle.getClientView();
    }

}
