package spout.api.gamecontent.datadriven.serversidetranslation.apply;

import net.minecraft.server.MinecraftServer;
import spout.api.gamecontent.datadriven.serversidetranslation.ServersideTranslation;
import spout.api.gamecontent.datadriven.serversidetranslation.ServersideTranslationImpl;
import spout.gamecontent.datadriven.serversidetranslation.apply.OptimizedServersideTranslations;
import spout.gamecontent.datadriven.serversidetranslation.registry.ServersideTranslationRegistryKey;
import org.jspecify.annotations.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * The implementation of {@link ServersideTranslations}.
 */
public final class ServersideTranslationsImpl implements ServersideTranslations {

    public static ServersideTranslationsImpl get() {
        return (ServersideTranslationsImpl) ServersideTranslations.get();
    }

    /**
     * A map of the registered translations per key.
     */
    final Map<String, OptimizedServersideTranslations.RegisteredTranslationsForKey> registeredTranslations = new HashMap<>();

    @Override
    public boolean hasAny(String key) {
        return OptimizedServersideTranslations.hasAny(key);
    }

    @Override
    public @Nullable ServersideTranslation get(String key, @Nullable String locale) {
        spout.gamecontent.datadriven.serversidetranslation.ServersideTranslation internal = OptimizedServersideTranslations.get(key, locale);
        return internal == null ? null : new ServersideTranslationImpl(MinecraftServer.getServer().registryAccess().lookupOrThrow(ServersideTranslationRegistryKey.SERVERSIDE_TRANSLATION).wrapAsHolder(internal));
    }

}
