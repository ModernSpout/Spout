package spout.gamecontent.datadriven.common.enuminjection.rewrite.apply;

import spout.util.mapping.handle.SimpleMappingHandle;

public final class EnumNameRewriterHandle<S> extends SimpleMappingHandle<String, String> {

    public final S sourceValue;

    public EnumNameRewriterHandle(final S sourceValue, final String data, final boolean isDataMutable) {
        super(data, isDataMutable);
        this.sourceValue = sourceValue;
    }

}
