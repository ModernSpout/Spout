package spout.gamecontent.datadriven.blocktype;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import spout.util.minecraft.registry.RegistryKeyUtil;

/**
 * Holder for {@link #BLOCK_TYPE}.
 *
 * <p>
 * Analogous to {@link Registries}.
 * </p>
 */
public final class BuiltInBlockTypeRegistryKey {

    private BuiltInBlockTypeRegistryKey() {
        throw new UnsupportedOperationException();
    }

    /**
     * Key for {@link BuiltInBlockTypeRegistry#BLOCK_TYPE}.
     */
    public static final ResourceKey<Registry<SpoutBlockType>> BLOCK_TYPE = RegistryKeyUtil.createWithSpoutNamespace("block_type");

}
