package spout.gamecontent.datadriven.material.enuminjection.rewrite.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.apache.commons.lang3.tuple.Triple;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import spout.gamecontent.datadriven.common.enuminjection.rewrite.EnumNameRewriter;
import spout.util.minecraft.registry.RegistryKeyUtil;

/**
 * Holder for {@link #MATERIAL_NAME_REWRITER}.
 *
 * <p>
 * Analogous to {@link Registries}.
 * </p>
 */
public final class MaterialNameRewriterRegistryKey {

    private MaterialNameRewriterRegistryKey() {
        throw new UnsupportedOperationException();
    }

    /**
     * Key for the {@link Material#name()} rewriter registry.
     */
    public static final ResourceKey<Registry<EnumNameRewriter<Triple<NamespacedKey, @Nullable BlockType, @Nullable ItemType>>>> MATERIAL_NAME_REWRITER = RegistryKeyUtil.createWithSpoutNamespace("material_name_rewriter");

}
