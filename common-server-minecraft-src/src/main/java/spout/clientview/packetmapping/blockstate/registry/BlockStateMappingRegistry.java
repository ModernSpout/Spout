package spout.clientview.packetmapping.blockstate.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import spout.util.minecraft.registry.RegistryKeyUtil;

/**
 * Holder for {@link #BLOCK_STATE_MAPPING}.
 *
 * <p>
 * Analogous to {@link Registries}.
 * </p>
 */
public final class BlockStateMappingRegistry {

    private BlockStateMappingRegistry() {
        throw new UnsupportedOperationException();
    }

    /**
     * Key for the block state mapping registry.
     */
    public static final ResourceKey<Registry<BlockStateMapping>> BLOCK_STATE_MAPPING = RegistryKeyUtil.createWithSpoutNamespace("block_state_mapping");

}
