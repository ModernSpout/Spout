package spout.gamecontent.datadriven.material.enuminjection;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Registry;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.tuple.Triple;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import spout.gamecontent.datadriven.common.enuminjection.BukkitEnumSynchronizer;
import spout.gamecontent.datadriven.common.enuminjection.KeyedSourceBukkitEnumSynchronizer;
import spout.gamecontent.datadriven.common.enuminjection.rewrite.EnumNameRewriter;
import spout.gamecontent.datadriven.material.enuminjection.rewrite.registry.MaterialNameRewriterRegistryKey;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@link BukkitEnumSynchronizer} for {@link Material}.
 */
public final class MaterialEnumSynchronizer extends KeyedSourceBukkitEnumSynchronizer<Material, Triple<NamespacedKey, @Nullable BlockType, @Nullable ItemType>, MaterialEnumInjector> {

    private static @Nullable MaterialEnumSynchronizer INSTANCE;

    public static MaterialEnumSynchronizer get() {
        if (INSTANCE == null) {
            INSTANCE = new MaterialEnumSynchronizer();
        }
        return INSTANCE;
    }

    private MaterialEnumSynchronizer() {
    }

    @Override
    protected Registry<EnumNameRewriter<Triple<NamespacedKey, @Nullable BlockType, @Nullable ItemType>>> lookupRewriterRegistry() {
        return MinecraftServer.getServer().registryAccess().lookupOrThrow(MaterialNameRewriterRegistryKey.MATERIAL_NAME_REWRITER);
    }

    @Override
    protected MaterialEnumInjector createInjector() throws Exception {
        return new MaterialEnumInjector();
    }

    @Override
    protected List<Triple<NamespacedKey, @Nullable BlockType, @Nullable ItemType>> getSourceValues() {

        Map<NamespacedKey, Pair<@Nullable BlockType, @Nullable ItemType>> map = new LinkedHashMap<>();

        // Add all non-vanilla blocks
        org.bukkit.Registry.BLOCK.stream().forEach(blockType -> {
            NamespacedKey key = blockType.getKey();
            if (key.namespace().equals(NamespacedKey.MINECRAFT_NAMESPACE)) {
                return;
            }
            map.put(key, Pair.of(blockType, null));
        });

        // Add all non-vanilla items
        org.bukkit.Registry.ITEM.stream().forEach(itemType -> {
            NamespacedKey key = itemType.getKey();
            if (key.namespace().equals(NamespacedKey.MINECRAFT_NAMESPACE)) {
                return;
            }
            map.compute(key, ($, existingValue) -> {
                if (existingValue == null) {
                    return Pair.of(null, itemType);
                }
                return Pair.of(existingValue.first(), itemType);
            });
        });

        // Turn into a list
        List<Triple<NamespacedKey, @Nullable BlockType, @Nullable ItemType>> list = new ArrayList<>();
        for (var entry : map.entrySet()) {
            list.add(Triple.of(entry.getKey(), entry.getValue().left(), entry.getValue().right()));
        }
        return list;

    }

    @Override
    protected void stage(String enumName, Triple<NamespacedKey, @Nullable BlockType, @Nullable ItemType> sourceValue) throws Exception {
        this.getInjector().stage(enumName, sourceValue.getLeft(), sourceValue.getMiddle(), sourceValue.getRight());
    }

    @Override
    protected NamespacedKey getKey(Triple<NamespacedKey, @Nullable BlockType, @Nullable ItemType> sourceValue) {
        return sourceValue.getLeft();
    }

}
