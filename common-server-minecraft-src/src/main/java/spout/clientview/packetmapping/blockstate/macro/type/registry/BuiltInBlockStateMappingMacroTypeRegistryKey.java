package spout.clientview.packetmapping.blockstate.macro.type.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import spout.util.minecraft.registry.RegistryKeyUtil;

/**
 * Holder for {@link #BLOCK_STATE_MAPPING_MACRO_TYPE}.
 *
 * <p>
 * Analogous to {@link Registries}.
 * </p>
 */
public final class BuiltInBlockStateMappingMacroTypeRegistryKey {

    private BuiltInBlockStateMappingMacroTypeRegistryKey() {
        throw new UnsupportedOperationException();
    }

    /**
     * Key for {@link BuiltInBlockStateMappingMacroTypeRegistry#BLOCK_STATE_MAPPING_MACRO_TYPE}.
     */
    public static final ResourceKey<Registry<BlockStateMappingMacroType>> BLOCK_STATE_MAPPING_MACRO_TYPE = RegistryKeyUtil.createWithSpoutNamespace("block_state_mapping_macro_type");

}
