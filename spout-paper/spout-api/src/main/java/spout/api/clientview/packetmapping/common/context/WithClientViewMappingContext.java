package spout.api.clientview.packetmapping.common.context;

import spout.api.clientview.model.ClientView;
import spout.api.util.mapping.handle.WithContextMappingHandle;
import spout.server.paper.api.util.mapping.MappingFunctionContext;

/**
 * A {@linkplain WithContextMappingHandle#getContext() mapping context}
 * for mappings that happen in the context of some {@link ClientView}.
 */
public interface WithClientViewMappingContext extends MappingFunctionContext {

    /**
     * @return The {@link ClientView} of the client that this mapping is being done for.
     */
    ClientView getClientView();

}
