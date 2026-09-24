package spout.api.clientview.packetmapping.blockstate.registry;

import spout.clientview.packetmapping.blockstate.registry.BlockStateMapping;

/**
 * The implementation for {@link FunctionBlockStateMapping} and {@link FunctionBlockStateMappingNMS}.
 */
public final class FunctionBlockStateMappingImpl extends BlockStateMappingImpl implements FunctionBlockStateMappingNMS {

    FunctionBlockStateMappingImpl(BlockStateMapping handle) {
        super(handle);
    }

    // @Override
    // public Consumer<BlockStateMappingHandleNMS> getToFunctionNMS() {
    //     return ((FunctionBlockStateMappingStep) this.handle.operation()).function();
    // }

    @Override
    public boolean requiresCoordinates() {
        return this.handle.operation().requiresCoordinates();
    }

    // @Override
    // public Consumer<BlockStateMappingHandle> getToFunction() {
    //     return ((FunctionBlockStateMappingStep) this.handle.operation()).function();
    // }

}
