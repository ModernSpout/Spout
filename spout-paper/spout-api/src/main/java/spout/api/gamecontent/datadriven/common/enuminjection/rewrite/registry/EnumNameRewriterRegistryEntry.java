package spout.api.gamecontent.datadriven.common.enuminjection.rewrite.registry;

import io.papermc.paper.registry.RegistryBuilder;
import spout.api.gamecontent.datadriven.common.enuminjection.rewrite.EnumNameRewriter;
import spout.api.gamecontent.datadriven.common.enuminjection.rewrite.handle.EnumNameRewriterHandle;
import spout.api.util.mapping.builder.FunctionRegistryEntry;
import spout.api.util.mapping.builder.FunctionRegistryEntryBuilder;

/**
 * A data-centric version-specific registry entry for the {@link EnumNameRewriter} type.
 *
 * <p>
 * Don't use this unless you know what you are doing.
 * The default name for enums is {@code SPOUT_<namespace>_<key>}, for example
 * {@code willow_trees:willow_log} will become {@code SPOUT_WILLOW_TREES_WILLOW_LOG}.
 * With this naming style, it is very unlikely that any issues will come up now or in the future.
 * Other naming styles may lead to collisions or other plugins parsing strings incorrectly.
 * </p>
 */
public interface EnumNameRewriterRegistryEntry<S> extends FunctionRegistryEntry<EnumNameRewriterHandle<S>> {

    /**
     * A mutable builder for the {@link EnumNameRewriterRegistryEntry},
     * that plugins may change in applicable registry events.
     */
    interface Builder<S> extends EnumNameRewriterRegistryEntry<S>, RegistryBuilder<EnumNameRewriter<S>>, FunctionRegistryEntryBuilder<EnumNameRewriterHandle<S>> {
    }

}
