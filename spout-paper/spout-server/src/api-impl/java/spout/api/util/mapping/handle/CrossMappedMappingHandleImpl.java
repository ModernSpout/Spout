package spout.api.util.mapping.handle;

import spout.util.mapping.handle.AbstractMappingHandle;

/**
 * An implementation of {@link MappingHandle},
 * that also implements {@link WithOriginalMappingHandle},
 * that passes any calls to an internal {@link AbstractMappingHandle},
 * cross-mapping its types.
 */
public abstract class CrossMappedMappingHandleImpl<T, IT, IH extends AbstractMappingHandle<IT>> implements WithOriginalMappingHandle<T> {

    public final IH handle;

    public CrossMappedMappingHandleImpl(IH handle) {
        this.handle = handle;
    }

    protected abstract IT mapAPIToInternal(T data);

    protected abstract T mapInternalToAPI(IT data);

    @Override
    public T getOriginal() {
        if (this.handle instanceof WithOriginalMappingHandle<?> withOriginalInternal) {
            return this.mapInternalToAPI((IT) withOriginalInternal.getOriginal());
        }
        throw new UnsupportedOperationException("Internal handle does not support original");
    }

    @Override
    public T getImmutable() {
        return this.mapInternalToAPI(this.handle.getImmutable());
    }

    @Override
    public void set(T data) {
        this.handle.set(this.mapAPIToInternal(data));
    }

}
