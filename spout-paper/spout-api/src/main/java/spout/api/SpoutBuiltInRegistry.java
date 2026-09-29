package spout.api;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Keyed;
import org.bukkit.Registry;
import spout.api.clientview.model.awarenesslevel.AwarenessLevel;

/**
 * Analogous to {@link Registry}.
 */
public final class SpoutBuiltInRegistry {

    private SpoutBuiltInRegistry() {
        throw new UnsupportedOperationException();
    }

    /**
     * Data-driven registry for awareness levels.
     */
    public static final Registry<AwarenessLevel> AWARENESS_LEVEL = registryFor(SpoutRegistryKey.AWARENESS_LEVEL);

    private static <A extends Keyed> Registry<A> registryFor(final RegistryKey<A> registryKey) {
        return RegistryAccess.registryAccess().getRegistry(registryKey);
    }

}
