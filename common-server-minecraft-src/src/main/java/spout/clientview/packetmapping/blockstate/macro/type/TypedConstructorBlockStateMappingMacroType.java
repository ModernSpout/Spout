package spout.clientview.packetmapping.blockstate.macro.type;

import com.mojang.serialization.MapCodec;
import java.util.function.Function;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.util.minecraft.resources.IdentifierUtil;

/**
 * A type of {@link BlockStateMappingMacro}.
 */
public class TypedConstructorBlockStateMappingMacroType extends BlockStateMappingMacroType {

    private @Nullable Function<BlockStateMappingMacroType, ? extends MapCodec<? extends BlockStateMappingMacro>> codecConstructor;
    private @Nullable MapCodec<? extends BlockStateMappingMacro> codec;

    public TypedConstructorBlockStateMappingMacroType(Function<BlockStateMappingMacroType, ? extends MapCodec<? extends BlockStateMappingMacro>> codecConstructor) {
        this.codecConstructor = codecConstructor;
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
