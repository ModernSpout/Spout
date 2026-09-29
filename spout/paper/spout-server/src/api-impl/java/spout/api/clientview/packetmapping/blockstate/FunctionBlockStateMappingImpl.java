package spout.api.clientview.packetmapping.blockstate;

import net.minecraft.core.Holder;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;

/**
 * The implementation for {@link FunctionBlockStateMapping} and {@link FunctionBlockStateMappingNMS}.
 */
public final class FunctionBlockStateMappingImpl extends BlockStateMappingImpl implements FunctionBlockStateMappingNMS {

    FunctionBlockStateMappingImpl(Holder<BlockStateMapping> holder) {
        super(holder);
    }

    // @Override
    // public Consumer<BlockStateMappingHandleNMS> getToFunctionNMS() {
    //     return ((FunctionBlockStateMappingStep) this.handle.operation()).function();
    // }

    @Override
    public boolean requiresCoordinates() {
        return this.holder.value().operation().requiresCoordinates();
    }

    // @Override
    // public Consumer<BlockStateMappingHandle> getToFunction() {
    //     return ((FunctionBlockStateMappingStep) this.handle.operation()).function();
    // }

}
