package spout.server.paper.api.moredatadriven.paper.registry.type.nms;

import net.minecraft.resources.Identifier;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import spout.gamecontent.datadriven.itemtype.ItemTypeType;

/**
 * Extension of {@link ItemTypeType} using Minecraft internals.
 */
public interface ItemTypeTypeNMS extends ItemTypeType {

    @Override
    default NamespacedKey getKey() {
        return CraftNamespacedKey.fromMinecraft(this.getIdentifier());
    }

    Identifier getIdentifier();

}
