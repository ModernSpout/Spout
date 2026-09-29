package spout.api.gamecontent.datadriven.common.enuminjection.rewrite.registry;

import io.papermc.paper.registry.PaperRegistryBuilder;
import io.papermc.paper.registry.data.util.Conversions;
import org.jspecify.annotations.Nullable;
import spout.api.gamecontent.datadriven.common.enuminjection.rewrite.EnumNameRewriter;
import spout.api.gamecontent.datadriven.common.enuminjection.rewrite.handle.EnumNameRewriterHandle;
import spout.api.gamecontent.datadriven.common.enuminjection.rewrite.handle.EnumNameRewriterHandleImpl;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * The implementation for {@link EnumNameRewriterRegistryEntry}.
 */
public class EnumNameRewriterRegistryEntryImpl<S> implements EnumNameRewriterRegistryEntry<S>, EnumNameRewriterRegistryEntry.Builder<S> {

    protected @Nullable Consumer<EnumNameRewriterHandle<S>> toFunction;

    public EnumNameRewriterRegistryEntryImpl(
        final Conversions ignoredConversions,
        final spout.gamecontent.datadriven.common.enuminjection.rewrite.EnumNameRewriter<S> internal
    ) {
    }

    @Override
    public void setToFunction(final Consumer<EnumNameRewriterHandle<S>> function) {
        this.toFunction = function;
    }

    /**
     * The implementation for {@link EnumNameRewriterRegistryEntry.Builder}.
     */
    public static final class Builder<S> extends EnumNameRewriterRegistryEntryImpl<S> implements EnumNameRewriterRegistryEntry.Builder<S>,
        PaperRegistryBuilder<spout.gamecontent.datadriven.common.enuminjection.rewrite.EnumNameRewriter<S>, EnumNameRewriter<S>> {

        public Builder(
            final Conversions conversions,
            final spout.gamecontent.datadriven.common.enuminjection.rewrite.EnumNameRewriter<S> internal
        ) {
            super(conversions, internal);
        }

        @Override
        public spout.gamecontent.datadriven.common.enuminjection.rewrite.EnumNameRewriter<S> build() {
            return new spout.gamecontent.datadriven.common.enuminjection.rewrite.EnumNameRewriter<>(
                bukkitFunctionToInternalFunction(Objects.requireNonNull(this.toFunction, "No function given"))
            );
        }

    }

    private static <S> Consumer<spout.gamecontent.datadriven.common.enuminjection.rewrite.apply.EnumNameRewriterHandle<S>> bukkitFunctionToInternalFunction(Consumer<EnumNameRewriterHandle<S>> function) {
        return handle -> function.accept(new EnumNameRewriterHandleImpl<>(handle));
    }

}
