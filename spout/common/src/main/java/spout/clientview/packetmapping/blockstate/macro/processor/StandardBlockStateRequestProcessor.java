package spout.clientview.packetmapping.blockstate.macro.processor;

import net.minecraft.core.Registry;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockStateMacro;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;

/**
 * A {@link BlockStateMappingMacroProcessor} for {@link BlockStateMappingMacroTypes#FULL_BLOCK}.
 */
public abstract class StandardBlockStateRequestProcessor extends FilledArrayResultProcessor<FromToBlockStateMacro, ArrayResultProcessor.RequestBasedResult> {

    public StandardBlockStateRequestProcessor(FromToBlockStateMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<FromToBlockStateMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<FromToBlockStateMacro, RequestBasedResult>.FillPromise kickoff) {
        return kickoff
            .then(new StateFallbackFillPromise(this.macro.fallbackState))
            .then(this.getSafeFallbackFillPromise());
    }

    protected abstract FillPromise getSafeFallbackFillPromise();

}
