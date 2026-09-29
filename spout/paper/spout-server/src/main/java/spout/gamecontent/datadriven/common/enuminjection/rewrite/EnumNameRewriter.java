package spout.gamecontent.datadriven.common.enuminjection.rewrite;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import spout.gamecontent.datadriven.common.enuminjection.rewrite.apply.EnumNameRewriterHandle;
import java.util.function.Consumer;

public record EnumNameRewriter<S>(Consumer<EnumNameRewriterHandle<S>> function) {

    public static <S> Codec<EnumNameRewriter<S>> codec() {
        return new Codec<>() {

            @Override
            public <T> DataResult<T> encode(final EnumNameRewriter<S> sEnumNameRewriter, final DynamicOps<T> dynamicOps, final T t) {
                throw new UnsupportedOperationException();
            }

            @Override
            public <T> DataResult<Pair<EnumNameRewriter<S>, T>> decode(final DynamicOps<T> dynamicOps, final T t) {
                throw new UnsupportedOperationException();
            }

        };
    }

}
