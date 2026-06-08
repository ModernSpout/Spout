package spout.gamecontent.datadriven.itemtype;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import spout.gamecontent.datadriven.common.registry.bootstrap.SpoutBuiltInDataDrivenRegistryBootstrap;

/**
 * Holder for {@link #ITEM_TYPE}.
 *
 * <p>
 * Analogous to {@link BuiltInRegistries}.
 * </p>
 */
public final class BuiltInItemTypeRegistry implements SpoutBuiltInDataDrivenRegistryBootstrap.Provider {

    /**
     * A registry for item types.
     */
    public static final Registry<SpoutItemType> ITEM_TYPE = BuiltInRegistries.registerSimple(BuiltInItemTypeRegistryKey.ITEM_TYPE, SpoutItemTypes::bootstrap);

    @Override
    public Registry<?> provideDataDrivenRegistry() {
        return ITEM_TYPE;
    }

}
