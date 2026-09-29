package spout.clientview.packetmapping.blockstate.macro.type.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;
import spout.gamecontent.datadriven.common.registry.bootstrap.SpoutBuiltInDataDrivenRegistryBootstrap;

/**
 * Holder for {@link #BLOCK_STATE_MAPPING_MACRO_TYPE}.
 *
 * <p>
 * Analogous to {@link BuiltInRegistries}.
 * </p>
 */
public final class BuiltInBlockStateMappingMacroTypeRegistry {

    private BuiltInBlockStateMappingMacroTypeRegistry() {
        throw new UnsupportedOperationException();
    }

    public static final class BootstrapProvider implements SpoutBuiltInDataDrivenRegistryBootstrap.Provider {

        @Override
        public Registry<?> provideDataDrivenRegistry() {
            return BLOCK_STATE_MAPPING_MACRO_TYPE;
        }

    }

    /**
     * A registry for block state mapping macro types.
     */
    public static final Registry<BlockStateMappingMacroType> BLOCK_STATE_MAPPING_MACRO_TYPE = BuiltInRegistries.registerSimple(BuiltInBlockStateMappingMacroTypeRegistryKey.BLOCK_STATE_MAPPING_MACRO_TYPE, BlockStateMappingMacroTypes::bootstrap);

}
