package spout.api.gamecontent.datadriven.blocktype;

import net.minecraft.resources.Identifier;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;

/**
 * Extension of {@link BlockTypeType} using Minecraft internals.
 */
public interface BlockTypeTypeNMS extends BlockTypeType {

    @Override
    default NamespacedKey getKey() {
        return CraftNamespacedKey.fromMinecraft(this.getIdentifier());
    }

    Identifier getIdentifier();

}
