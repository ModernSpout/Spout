package spout.api.gamecontent.datadriven.common.enuminjection.rewrite;

import io.papermc.paper.registry.HolderableBase;
import net.minecraft.core.Holder;

public final class EnumNameRewriterImpl<S> extends HolderableBase<spout.gamecontent.datadriven.common.enuminjection.rewrite.EnumNameRewriter<S>> implements EnumNameRewriter<S> {

    public EnumNameRewriterImpl(final Holder<spout.gamecontent.datadriven.common.enuminjection.rewrite.EnumNameRewriter<S>> holder) {
        super(holder);
    }

}
