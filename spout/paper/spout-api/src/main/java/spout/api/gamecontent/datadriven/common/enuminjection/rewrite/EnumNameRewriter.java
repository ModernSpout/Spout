package spout.api.gamecontent.datadriven.common.enuminjection.rewrite;

import org.bukkit.Keyed;
import spout.api.gamecontent.datadriven.common.enuminjection.rewrite.handle.EnumNameRewriterHandle;
import spout.api.util.mapping.FunctionMapping;

public interface EnumNameRewriter<S> extends FunctionMapping<EnumNameRewriterHandle<S>>, Keyed {
}
