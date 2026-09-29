package spout.api.gamecontent.datadriven.common.keyawareregistry;

import net.minecraft.resources.Identifier;

/**
 * Allows for the setting of the {@link #getKeyNMS} property of a {@link KeyAwareRegistryEntryNMS}.
 */
public interface SettableKeyAwareRegistryEntryNMS extends KeyAwareRegistryEntryNMS {

    /**
     * Sets the {@link #getKeyNMS} of this registry entry.
     */
    void setKeyNMS(Identifier key);

}
