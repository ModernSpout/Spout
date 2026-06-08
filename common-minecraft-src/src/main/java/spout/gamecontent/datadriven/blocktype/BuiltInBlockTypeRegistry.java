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
public final class BuiltInBlockTypeRegistry implements SpoutBuiltInDataDrivenRegistryBootstrap.Provider {

    /**
     * A registry for block types.
     *
     * <p>
     * This registry is synchronized with {@link BuiltInRegistries#BLOCK_TYPE}:
     * entries added to either are added to the other.
     * </p>
     */
    public static final SpoutBlockTypeRegistry BLOCK_TYPE = BuiltInRegistries.internalRegister(BuiltInBlockTypeRegistryKey.BLOCK_TYPE, new SpoutBlockTypeRegistry(), SpoutBlockTypes::bootstrap);

    @Override
    public Registry<?> provideDataDrivenRegistry() {
        return BLOCK_TYPE;
    }

}
