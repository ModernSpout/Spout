package spout.api.util.mapping.handle;

import spout.util.mapping.handle.AbstractMutableMappingHandle;
import spout.util.mapping.handle.AbstractWithContextMappingHandle;
import spout.util.mapping.handle.AbstractWithOriginalMappingHandle;

public abstract class ProxyMutableMappingHandleImpl<T, MT extends T, C, IC, IH extends AbstractWithOriginalMappingHandle<T> & AbstractWithContextMappingHandle<T, IC> & AbstractMutableMappingHandle<T, MT>> extends ProxyMappingHandleImpl<T, C, IC, IH> implements MutableMappingHandle<T, MT> {

    public ProxyMutableMappingHandleImpl(IH handle) {
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
