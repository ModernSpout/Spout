package spout.api.util.mapping.handle;

import spout.server.paper.api.util.mapping.WithContextMappingFunctionHandle;
import spout.util.mapping.handle.AbstractMutableMappingHandle;
import spout.util.mapping.handle.AbstractWithContextMappingHandle;

/**
 * A {@link CrossMappedMutableMappingHandleImpl}
 * that also implements {@link WithContextMappingFunctionHandle}.
 */
public abstract class CrossMappedWithContextMutableMappingHandleImpl<T, MT extends T, C, IT, IMT extends IT, IC, IH extends AbstractWithContextMappingHandle<IT, IC> & AbstractMutableMappingHandle<IT, IMT>> extends CrossMappedMutableMappingHandleImpl<T, MT, IT, IMT, IH> implements WithContextMappingHandle<T, C> {

    public CrossMappedWithContextMutableMappingHandleImpl(IH handle) {
        super(handle);
    }

    protected abstract C mapContextInternalToAPI(IC context);

    @Override
    public C getContext() {
        return this.mapContextInternalToAPI(this.handle.getContext());
    }

}
