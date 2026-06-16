package spout.clientview.packetmapping.blockstate.macro.type;

import com.mojang.serialization.MapCodec;
import java.util.function.Function;
import net.minecraft.core.Registry;
import org.apache.commons.lang3.function.TriFunction;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.processor.BlockStateMappingMacroProcessor;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMapping;

/**
 * A type of {@link BlockStateMappingMacro}.
 */
public class TypedConstructorBlockStateMappingMacroType extends BlockStateMappingMacroType {

    private final TriFunction<? extends BlockStateMappingMacro, Registry<BlockStateMappingMacro>, Registry<BlockStateMapping>, ? extends BlockStateMappingMacroProcessor<?>> processorConstructor;
    private @Nullable Function<BlockStateMappingMacroType, ? extends MapCodec<? extends BlockStateMappingMacro>> codecConstructor;
    private @Nullable MapCodec<? extends BlockStateMappingMacro> codec;

    public <M extends BlockStateMappingMacro> TypedConstructorBlockStateMappingMacroType(TriFunction<M, Registry<BlockStateMappingMacro>, Registry<BlockStateMapping>, ? extends BlockStateMappingMacroProcessor<?>> processorConstructor, Function<BlockStateMappingMacroType, ? extends MapCodec<? extends BlockStateMappingMacro>> codecConstructor) {
        this.processorConstructor = processorConstructor;
        this.codecConstructor = codecConstructor;
    }

    @Override
    public <M extends BlockStateMappingMacro> BlockStateMappingMacroProcessor<M> createProcessor(M macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        return (BlockStateMappingMacroProcessor<M>) ((TriFunction) this.processorConstructor).apply(macro, sourceRegistry, targetRegistry);
    }

    @Override
    public MapCodec<? extends BlockStateMappingMacro> getCodec() {
        if (this.codec == null) {
            this.codec = this.codecConstructor.apply(this);
            this.codecConstructor = null;
        }
        return this.codec;
    }

}
