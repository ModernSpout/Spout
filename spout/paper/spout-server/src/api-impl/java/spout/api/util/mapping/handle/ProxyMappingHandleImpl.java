package spout.api.util.mapping.handle;

import spout.util.mapping.handle.AbstractWithOriginalMappingHandle;

public class ProxyMappingHandleImpl<T, IH extends AbstractWithOriginalMappingHandle<T>> implements WithOriginalMappingHandle<T> {

    public final IH handle;

    public ProxyMappingHandleImpl(IH handle) {
        this.handle = handle;
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
