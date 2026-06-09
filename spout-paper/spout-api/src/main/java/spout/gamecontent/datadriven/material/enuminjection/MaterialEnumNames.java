package spout.gamecontent.datadriven.material.enuminjection;

import org.apache.commons.lang3.tuple.Triple;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemType;
import spout.gamecontent.datadriven.common.enuminjection.BukkitEnumNames;
import org.jspecify.annotations.Nullable;
import spout.server.paper.impl.util.service.SpoutServices;

/**
 * The {@link BukkitEnumNames} for {@link Material}.
 */
public interface MaterialEnumNames extends BukkitEnumNames<Triple<NamespacedKey, @Nullable BlockType, @Nullable ItemType>> {

    /**
     * @return The {@link MaterialEnumNames} instance.
     */
    static MaterialEnumNames get() {
        return SpoutServices.getMaterialEnumNames();
    }

}
