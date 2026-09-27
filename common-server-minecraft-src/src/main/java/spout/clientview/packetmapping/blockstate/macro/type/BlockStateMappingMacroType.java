package spout.clientview.packetmapping.blockstate.macro.type;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import net.minecraft.core.Registry;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.processor.BlockStateMappingMacroProcessor;
import spout.clientview.packetmapping.blockstate.macro.type.registry.BuiltInBlockStateMappingMacroTypeRegistry;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.util.minecraft.resources.IdentifierUtil;
import java.util.stream.Stream;

/**
 * A type of {@link BlockStateMappingMacro}.
 */
public abstract class BlockStateMappingMacroType {

    public static final MapCodec<BlockStateMappingMacro> MACRO_CODEC = IdentifierUtil.byNameWithSpoutNamespaceAsDefaultCodec(BuiltInBlockStateMappingMacroTypeRegistry.BLOCK_STATE_MAPPING_MACRO_TYPE).dispatchMap(macro -> macro.type, t -> new MapCodec<BlockStateMappingMacro>() {
        @Override
        public <T> Stream<T> keys(final DynamicOps<T> dynamicOps) {
            return t.getCodec().keys(dynamicOps);
        }

        @Override
        public <T> DataResult<BlockStateMappingMacro> decode(final DynamicOps<T> dynamicOps, final MapLike<T> mapLike) {
            return t.getCodec().decode(dynamicOps, mapLike).map(y -> y);
        }

        @Override
        public <T> RecordBuilder<T> encode(final BlockStateMappingMacro blockStateMappingMacro, final DynamicOps<T> dynamicOps, final RecordBuilder<T> recordBuilder) {
            return null;
        }
    });

    public abstract <M extends BlockStateMappingMacro> BlockStateMappingMacroProcessor<M> createProcessor(M macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry);

    public abstract MapCodec<? extends BlockStateMappingMacro> getCodec();

    @Override
    public String toString() {
        return "BlockStateMappingMacroType{" + BuiltInBlockStateMappingMacroTypeRegistry.BLOCK_STATE_MAPPING_MACRO_TYPE.wrapAsHolder(this).getRegisteredName() + "}";
    }

}
