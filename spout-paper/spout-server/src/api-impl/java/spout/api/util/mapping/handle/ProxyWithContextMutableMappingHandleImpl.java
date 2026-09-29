package spout.api.util.mapping.handle;

import spout.util.mapping.handle.AbstractMutableMappingHandle;
import spout.util.mapping.handle.AbstractWithContextMappingHandle;
import spout.util.mapping.handle.AbstractWithOriginalMappingHandle;

public abstract class ProxyWithContextMutableMappingHandleImpl<T, MT extends T, C, IC, IH extends AbstractWithOriginalMappingHandle<T> & AbstractWithContextMappingHandle<T, IC> & AbstractMutableMappingHandle<T, MT>> extends ProxyWithContextMappingHandleImpl<T, C, IC, IH> implements MutableMappingHandle<T, MT> {

    public ProxyWithContextMutableMappingHandleImpl(IH handle) {
        super(handle);
    }

    protected abstract C mapContextInternalToAPI(IC context);

    @Override
    public MT getMutable() {
        return this.handle.getMutable();
    }

    @Override
    public void setMutable(MT data) {
        this.handle.setMutable(data);
    }

}
