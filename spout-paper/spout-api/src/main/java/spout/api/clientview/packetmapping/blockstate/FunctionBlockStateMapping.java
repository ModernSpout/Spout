package spout.api.clientview.packetmapping.blockstate;

import spout.api.clientview.packetmapping.blockstate.handle.BlockStateMappingHandle;
import spout.api.util.mapping.FunctionMapping;

public interface FunctionBlockStateMapping extends BlockStateMapping, FunctionMapping<BlockStateMappingHandle> {

    boolean requiresCoordinates();

}
