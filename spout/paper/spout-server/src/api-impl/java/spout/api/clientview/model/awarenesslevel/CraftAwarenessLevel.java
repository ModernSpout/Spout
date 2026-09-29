package spout.api.clientview.model.awarenesslevel;

import org.bukkit.craftbukkit.CraftRegistry;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.BuiltInAwarenessLevelRegistryKey;

/**
 * Conversion utility for {@link spout.api.clientview.model.awarenesslevel.AwarenessLevel}.
 */
public final class CraftAwarenessLevel {

    private CraftAwarenessLevel() {
        throw new UnsupportedOperationException();
    }

    public static AwarenessLevel fromBukkit(spout.api.clientview.model.awarenesslevel.AwarenessLevel level) {
        return CraftRegistry.bukkitToMinecraft(level);
    }

    public static spout.api.clientview.model.awarenesslevel.AwarenessLevel toBukkit(AwarenessLevel level) {
        return CraftRegistry.minecraftToBukkit(level, BuiltInAwarenessLevelRegistryKey.AWARENESS_LEVEL);
    }

}
