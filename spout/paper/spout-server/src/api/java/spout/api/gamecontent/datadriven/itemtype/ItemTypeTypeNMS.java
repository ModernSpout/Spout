package spout.api.gamecontent.datadriven.itemtype;

import net.minecraft.resources.Identifier;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;

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
