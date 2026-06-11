package spout.clientview.model.awarenesslevel;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import spout.util.minecraft.registry.RegistryKeyUtil;

/**
 * Holder for {@link #AWARENESS_LEVEL}.
 *
 * <p>
 * Analogous to {@link Registries}.
 * </p>
 */
public final class BuiltInAwarenessLevelRegistryKey {

    private BuiltInAwarenessLevelRegistryKey() {
        throw new UnsupportedOperationException();
    }

    /**
     * Key for {@link BuiltInAwarenessLevelRegistry#AWARENESS_LEVEL}.
     */
    public static final ResourceKey<Registry<AwarenessLevel>> AWARENESS_LEVEL = RegistryKeyUtil.createWithSpoutNamespace("awareness_level");

}
