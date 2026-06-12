package spout.clientview.model.awarenesslevel;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import spout.gamecontent.datadriven.common.registry.bootstrap.SpoutBuiltInDataDrivenRegistryBootstrap;

/**
 * Holder for {@link #AWARENESS_LEVEL}.
 *
 * <p>
 * Analogous to {@link BuiltInRegistries}.
 * </p>
 */
public final class BuiltInAwarenessLevelRegistry {

    private BuiltInAwarenessLevelRegistry() {
        throw new UnsupportedOperationException();
    }

    public static final class BootstrapProvider implements SpoutBuiltInDataDrivenRegistryBootstrap.Provider {

        @Override
        public Registry<?> provideDataDrivenRegistry() {
            return AWARENESS_LEVEL;
        }

    }

    /**
     * A registry for block types.
     *
     * <p>
     * This registry is synchronized with {@link BuiltInRegistries#BLOCK_TYPE}:
     * entries added to either are added to the other.
     * </p>
     */
    public static final Registry<AwarenessLevel> AWARENESS_LEVEL = BuiltInRegistries.registerSimple(BuiltInAwarenessLevelRegistryKey.AWARENESS_LEVEL, AwarenessLevels::bootstrap);

}
