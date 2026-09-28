package spout.api;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Keyed;
import org.bukkit.Registry;
import spout.api.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.api.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import spout.api.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.api.clientview.packetmapping.itemstack.ItemStackMapping;

/**
 * Analogous to {@link Registry}.
 */
public final class SpoutRegistry {

    private SpoutRegistry() {
        throw new UnsupportedOperationException();
    }

    /**
     * Data-driven registry for block state mapping macro types.
     */
    public static final Registry<BlockStateMappingMacroType> BLOCK_STATE_MAPPING_MACRO_TYPE = registryFor(SpoutRegistryKey.BLOCK_STATE_MAPPING_MACRO_TYPE);

    /**
     * Data-driven registry for block state mapping macros.
     */
    public static final Registry<BlockStateMappingMacro> BLOCK_STATE_MAPPING_MACRO = registryFor(SpoutRegistryKey.BLOCK_STATE_MAPPING_MACRO);

    /**
     * Data-driven registry for block state mappings.
     */
    public static final Registry<BlockStateMapping> BLOCK_STATE_MAPPING = registryFor(SpoutRegistryKey.BLOCK_STATE_MAPPING);

    /**
     * Data-driven registry for item stack mappings.
     */
    public static final Registry<ItemStackMapping> ITEM_STACK_MAPPING = registryFor(SpoutRegistryKey.ITEM_STACK_MAPPING);

    private static <A extends Keyed> Registry<A> registryFor(final RegistryKey<A> registryKey) {
        return RegistryAccess.registryAccess().getRegistry(registryKey);
    }

}
