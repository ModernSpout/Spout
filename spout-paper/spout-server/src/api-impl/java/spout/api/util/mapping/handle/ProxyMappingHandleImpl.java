package spout.api.util.mapping.handle;

import spout.util.mapping.handle.AbstractWithContextMappingHandle;
import spout.util.mapping.handle.AbstractWithOriginalMappingHandle;

public abstract class ProxyMappingHandleImpl<T, C, IC, IH extends AbstractWithOriginalMappingHandle<T> & AbstractWithContextMappingHandle<T, IC>> implements WithContextMappingHandle<T, C>, WithOriginalMappingHandle<T> {

    public final IH handle;

    public ProxyMappingHandleImpl(IH handle) {
        this.handle = handle;
    }

    protected abstract C mapContextInternalToAPI(IC context);

    @Override
    public C getContext() {
        return this.mapContextInternalToAPI(this.handle.getContext());
    }

    @Override
    public T getOriginal() {
        return this.handle.getOriginal();
    }

    @Override
    public T getImmutable() {
        return this.handle.getImmutable();
    }

    @Override
    public void set(T data) {
        this.handle.set(data);
    }

}
