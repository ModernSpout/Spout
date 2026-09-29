package spout.api.gamecontent.datadriven.common.enuminjection.rewrite.handle;

import spout.api.gamecontent.datadriven.common.enuminjection.rewrite.registry.EnumNameRewriterRegistryEntry;
import spout.api.util.mapping.handle.WithOriginalMappingHandle;

/**
 * A handle provided to code registered with {@link EnumNameRewriterRegistryEntry.Builder#setToFunction}.
 */
public interface EnumNameRewriterHandle<S> extends WithOriginalMappingHandle<String> {

    /**
     * @return The source value for which the enum name is being picked.
     */
    S getSourceValue();

}
