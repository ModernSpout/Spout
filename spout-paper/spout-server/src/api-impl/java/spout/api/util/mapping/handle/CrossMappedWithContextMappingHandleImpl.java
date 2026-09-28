package spout.api.util.mapping.handle;

import spout.util.mapping.handle.AbstractWithContextMappingHandle;

/**
 * A {@link CrossMappedMappingHandleImpl}
 * that also implements {@link WithContextMappingHandle}.
 */
public abstract class CrossMappedWithContextMappingHandleImpl<T, C, IT, IC, IH extends AbstractWithContextMappingHandle<IT, IC>> extends CrossMappedMappingHandleImpl<T, IT, IH> implements WithContextMappingHandle<T, C> {

    public CrossMappedWithContextMappingHandleImpl(IH handle) {
        super(handle);
    }

    protected abstract C mapContextInternalToAPI(IC context);

    @Override
    public C getContext() {
        return this.mapContextInternalToAPI(this.handle.getContext());
    }

}
