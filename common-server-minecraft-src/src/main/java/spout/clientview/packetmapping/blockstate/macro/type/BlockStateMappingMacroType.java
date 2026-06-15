package spout.clientview.packetmapping.blockstate.macro.type;

import com.mojang.serialization.MapCodec;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.util.minecraft.resources.IdentifierUtil;

/**
 * A type of {@link BlockStateMappingMacro}.
 */
public abstract class BlockStateMappingMacroType {

    public static final MapCodec<BlockStateMappingMacro> MACRO_CODEC = IdentifierUtil.byNameWithSpoutNamespaceAsDefaultCodec(BuiltInBlockStateMappingMacroTypeRegistry.BLOCK_STATE_MAPPING_MACRO_TYPE).dispatchMap(macro -> macro.type, BlockStateMappingMacroType::getCodec);

    public abstract MapCodec<? extends BlockStateMappingMacro> getCodec();

    @Override
    public String toString() {
        return "BlockStateMappingMacroType{" + BuiltInBlockStateMappingMacroTypeRegistry.BLOCK_STATE_MAPPING_MACRO_TYPE.wrapAsHolder(this).getRegisteredName() + "}";
    }

}
