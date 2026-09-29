package spout.api;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.RegistryKeyImpl;
import org.apache.commons.lang3.tuple.Triple;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import spout.api.clientview.model.awarenesslevel.AwarenessLevel;
import spout.api.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.api.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import spout.api.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.api.clientview.packetmapping.component.ComponentMapping;
import spout.api.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.api.gamecontent.datadriven.common.enuminjection.rewrite.EnumNameRewriter;
import spout.api.gamecontent.datadriven.serversidetranslation.ServersideTranslation;
import spout.branding.SpoutNamespace;

/**
 * Analogous to {@link RegistryKey}.
 */
public final class SpoutRegistryKey {

    private SpoutRegistryKey() {
        throw new UnsupportedOperationException();
    }

    /**
     * Data-driven registry for {@link Material#name()} rewriters.
     */
    public static final RegistryKey<EnumNameRewriter<Triple<NamespacedKey, @Nullable BlockType, @Nullable ItemType>>> MATERIAL_NAME_REWRITER = RegistryKeyImpl.create(SpoutNamespace.SPOUT + ":material_name_rewriter");

    /**
     * Data-driven registry for server-side translations.
     */
    public static final RegistryKey<ServersideTranslation> SERVERSIDE_TRANSLATION = RegistryKeyImpl.create(SpoutNamespace.SPOUT + ":serverside_translation");

    /**
     * Data-driven registry for awareness levels.
     */
    public static final RegistryKey<AwarenessLevel> AWARENESS_LEVEL = RegistryKeyImpl.create(SpoutNamespace.SPOUT + ":awareness_level");

    /**
     * Data-driven registry for component mappings.
     */
    public static final RegistryKey<ComponentMapping> COMPONENT_MAPPING = RegistryKeyImpl.create(SpoutNamespace.SPOUT + ":component_mapping");

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

    /**
     * Data-driven registry for item stack mappings.
     */
    public static final RegistryKey<ItemStackMapping> ITEM_STACK_MAPPING = RegistryKeyImpl.create(SpoutNamespace.SPOUT + ":item_stack_mapping");

}
