package spout.api;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.RegistryKeyImpl;
import spout.api.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.api.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import spout.api.clientview.packetmapping.blockstate.registry.BlockStateMapping;
import spout.branding.SpoutNamespace;

/**
 * Analogous to {@link RegistryKey}.
 */
public final class SpoutRegistryKey {

    private SpoutRegistryKey() {
        throw new UnsupportedOperationException();
    }

    /**
     * Data-driven registry for block state mapping macro types.
     */
    public static final RegistryKey<BlockStateMappingMacroType> BLOCK_STATE_MAPPING_MACRO_TYPE = RegistryKeyImpl.create(SpoutNamespace.SPOUT + ":block_state_mapping_macro_type");

    /**
     * Data-driven registry for block state mapping macros.
     */
    public static final RegistryKey<BlockStateMappingMacro> BLOCK_STATE_MAPPING_MACRO = RegistryKeyImpl.create(SpoutNamespace.SPOUT + ":block_state_mapping_macro");

    /**
     * Data-driven registry for block state mappings.
     */
    public static final RegistryKey<BlockStateMapping> BLOCK_STATE_MAPPING = RegistryKeyImpl.create(SpoutNamespace.SPOUT + ":block_state_mapping");

}
