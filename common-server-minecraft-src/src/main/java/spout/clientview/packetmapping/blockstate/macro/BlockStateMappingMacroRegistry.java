package spout.clientview.packetmapping.blockstate.macro;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import spout.util.minecraft.registry.RegistryKeyUtil;

/**
 * Holder for {@link #BLOCK_STATE_MAPPING_MACRO}.
 *
 * <p>
 * Analogous to {@link Registries}.
 * </p>
 */
public final class BlockStateMappingMacroRegistry {

    private BlockStateMappingMacroRegistry() {
        throw new UnsupportedOperationException();
    }

    /**
     * Key for the block state mapping macro registry.
     */
    public static final ResourceKey<Registry<BlockStateMappingMacro>> BLOCK_STATE_MAPPING_MACRO = RegistryKeyUtil.createWithSpoutNamespace("block_state_mapping_macro");

}
