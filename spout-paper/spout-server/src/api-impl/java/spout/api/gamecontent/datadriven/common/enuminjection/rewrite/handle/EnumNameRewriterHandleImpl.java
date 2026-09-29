package spout.api.gamecontent.datadriven.common.enuminjection.rewrite.handle;

import spout.api.util.mapping.handle.ProxyMappingHandleImpl;

public final class EnumNameRewriterHandleImpl<S> extends ProxyMappingHandleImpl<String, spout.gamecontent.datadriven.common.enuminjection.rewrite.apply.EnumNameRewriterHandle<S>> implements EnumNameRewriterHandle<S> {

    public EnumNameRewriterHandleImpl(spout.gamecontent.datadriven.common.enuminjection.rewrite.apply.EnumNameRewriterHandle<S> handle) {
        super(handle);
    }

    @Override
    public S getSourceValue() {
        return this.handle.sourceValue;
    }

}
