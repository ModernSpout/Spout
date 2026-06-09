package spout.server.paper.api.moredatadriven.paper.registry.type.nms;

import net.minecraft.resources.Identifier;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import spout.gamecontent.datadriven.blocktype.BlockTypeType;

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
