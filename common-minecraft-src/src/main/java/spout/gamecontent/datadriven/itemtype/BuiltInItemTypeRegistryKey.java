package spout.gamecontent.datadriven.itemtype;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import spout.util.minecraft.registry.RegistryKeyUtil;

/**
 * Holder for {@link #ITEM_TYPE}.
 *
 * <p>
 * Analogous to {@link Registries}.
 * </p>
 */
public final class BuiltInItemTypeRegistryKey {

    private BuiltInItemTypeRegistryKey() {
        throw new UnsupportedOperationException();
    }

    /**
     * Key for {@link BuiltInItemTypeRegistry#ITEM_TYPE}.
     */
    public static final ResourceKey<Registry<SpoutItemType>> ITEM_TYPE = RegistryKeyUtil.createWithSpoutNamespace("item_type");

}
