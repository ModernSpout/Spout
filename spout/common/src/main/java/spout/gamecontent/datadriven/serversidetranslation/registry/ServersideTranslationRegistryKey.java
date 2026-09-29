package spout.gamecontent.datadriven.serversidetranslation.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import spout.gamecontent.datadriven.serversidetranslation.ServersideTranslation;
import spout.util.minecraft.registry.RegistryKeyUtil;

/**
 * Holder for {@link #SERVERSIDE_TRANSLATION}.
 *
 * <p>
 * Analogous to {@link Registries}.
 * </p>
 */
public final class ServersideTranslationRegistryKey {

    private ServersideTranslationRegistryKey() {
        throw new UnsupportedOperationException();
    }

    /**
     * Key for the server-side translation registry.
     */
    public static final ResourceKey<Registry<ServersideTranslation>> SERVERSIDE_TRANSLATION = RegistryKeyUtil.createWithSpoutNamespace("serverside_translation");

}
