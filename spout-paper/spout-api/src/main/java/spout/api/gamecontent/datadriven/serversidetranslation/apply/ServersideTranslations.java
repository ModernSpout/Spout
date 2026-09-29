package spout.api.gamecontent.datadriven.serversidetranslation.apply;

import spout.api.gamecontent.datadriven.serversidetranslation.ServersideTranslation;
import org.jspecify.annotations.Nullable;
import spout.api.SpoutAPIServices;

/**
 * A service for registered server-side translations.
 *
 * <p>
 * This can also be used to override existing translations.
 * </p>
 */
public interface ServersideTranslations {

    /**
     * @return The {@link ServersideTranslations} instance.
     */
    static ServersideTranslations get() {
        return SpoutAPIServices.getServersideTranslations();
    }

    /**
     * @return Whether any server-side translations are registered for the given key.
     */
    boolean hasAny(String key);

    /**
     * @return The best registered server-side translation for the given key and locale,
     * or null if none could be found.
     */
    @Nullable ServersideTranslation get(String key, @Nullable String locale);

}
