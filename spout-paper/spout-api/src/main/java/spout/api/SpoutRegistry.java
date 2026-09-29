package spout.api;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.apache.commons.lang3.tuple.Triple;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import spout.api.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.api.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import spout.api.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.api.clientview.packetmapping.component.ComponentMapping;
import spout.api.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.api.gamecontent.datadriven.common.enuminjection.rewrite.EnumNameRewriter;
import spout.api.gamecontent.datadriven.serversidetranslation.ServersideTranslation;

/**
 * Analogous to {@link Registry}.
 */
public final class SpoutRegistry {

    private SpoutRegistry() {
        throw new UnsupportedOperationException();
    }

    /**
     * Data-driven registry for {@link Material#name()} rewriters.
     */
    public static final Registry<EnumNameRewriter<Triple<NamespacedKey, @Nullable BlockType, @Nullable ItemType>>> MATERIAL_NAME_REWRITER = registryFor(SpoutRegistryKey.MATERIAL_NAME_REWRITER);

    /**
     * Data-driven registry for server-side translations.
     */
    public static final Registry<ServersideTranslation> SERVERSIDE_TRANSLATION = registryFor(SpoutRegistryKey.SERVERSIDE_TRANSLATION);

    /**
     * Data-driven registry for component mappings.
     */
    public static final Registry<ComponentMapping> COMPONENT_MAPPING = registryFor(SpoutRegistryKey.COMPONENT_MAPPING);

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
