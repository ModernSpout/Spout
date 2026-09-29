package spout.api.util.mapping.handle;

import spout.util.mapping.handle.AbstractWithContextMappingHandle;
import spout.util.mapping.handle.AbstractWithOriginalMappingHandle;

public abstract class ProxyWithContextMappingHandleImpl<T, C, IC, IH extends AbstractWithOriginalMappingHandle<T> & AbstractWithContextMappingHandle<T, IC>> extends ProxyMappingHandleImpl<T, IH> implements WithContextMappingHandle<T, C> {

    public ProxyWithContextMappingHandleImpl(IH handle) {
        super(handle);
    }

    protected abstract C mapContextInternalToAPI(IC context);

    @Override
    public C getContext() {
        return this.mapContextInternalToAPI(this.handle.getContext());
    }

}
