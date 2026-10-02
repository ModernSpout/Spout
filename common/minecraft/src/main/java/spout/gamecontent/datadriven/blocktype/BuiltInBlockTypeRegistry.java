package spout.gamecontent.datadriven.blocktype;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import spout.gamecontent.datadriven.common.registry.bootstrap.SpoutBuiltInDataDrivenRegistryBootstrap;

/**
 * Holder for {@link #BLOCK_TYPE}.
 *
 * <p>
 * Analogous to {@link BuiltInRegistries}.
 * </p>
 */
public final class BuiltInBlockTypeRegistry {

    private BuiltInBlockTypeRegistry() {
        throw new UnsupportedOperationException();
    }

    public static final class BootstrapProvider implements SpoutBuiltInDataDrivenRegistryBootstrap.Provider {

        @Override
        public Registry<?> provideDataDrivenRegistry() {
            return BLOCK_TYPE;
        }

    }

    /**
     * A registry for block types.
     */
    public static final Registry<SpoutBlockType> BLOCK_TYPE = BuiltInRegistries.registerSimple(BuiltInBlockTypeRegistryKey.BLOCK_TYPE, SpoutBlockTypes::bootstrap);

}
