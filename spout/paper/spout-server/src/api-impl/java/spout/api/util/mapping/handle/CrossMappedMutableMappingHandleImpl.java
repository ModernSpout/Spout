package spout.api.util.mapping.handle;

import spout.util.mapping.handle.AbstractMutableMappingHandle;

/**
 * An implementation of {@link MappingHandle},
 * that also implements {@link WithOriginalMappingHandle} and {@link MutableMappingHandle},
 * that passes any calls to an internal {@link AbstractMutableMappingHandle},
 * cross-mapping its types.
 */
public abstract class CrossMappedMutableMappingHandleImpl<T, MT extends T, IT, IMT extends IT, IH extends AbstractMutableMappingHandle<IT, IMT>> extends CrossMappedMappingHandleImpl<T, IT, IH> implements MutableMappingHandle<T, MT> {

    public CrossMappedMutableMappingHandleImpl(IH handle) {
        super(handle);
    }

    protected abstract IMT mapMutableAPIToInternal(MT data);

    protected abstract MT mapMutableInternalToAPI(IMT data);

    @Override
    public MT getMutable() {
        return this.mapMutableInternalToAPI(this.handle.getMutable());
    }

    @Override
    public void setMutable(MT data) {
        this.handle.setMutable(this.mapMutableAPIToInternal(data));
    }

}
