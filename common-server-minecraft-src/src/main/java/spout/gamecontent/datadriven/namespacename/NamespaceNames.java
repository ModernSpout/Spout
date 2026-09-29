package spout.gamecontent.datadriven.namespacename;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jspecify.annotations.Nullable;
import java.util.List;

/**
 * Handles human-readable namespace names.
 */
public final class NamespaceNames {

    private NamespaceNames() {
        throw new UnsupportedOperationException();
    }

    private static final String TRANSLATION_KEY_PREFIX = "spout.namespace.";

    /**
     * @return A translation key for the human-readable form of the namespace.
     */
    public static String getTranslationKey(String namespace) {
        return TRANSLATION_KEY_PREFIX + namespace;
    }

    /**
     * Reverses {@link #getTranslationKey}.
     *
     * @return The original namespace, or null if the given string is not a namespace translation key.
     */
    public static @Nullable String parseNamespaceFromTranslationKey(String translationKey) {
        if (translationKey.startsWith(TRANSLATION_KEY_PREFIX) && translationKey.length() > TRANSLATION_KEY_PREFIX.length()) {
            return translationKey.substring(TRANSLATION_KEY_PREFIX.length());
        }
        return null;
    }

    /**
     * @return A newly created human-readable component for a namespace.
     */
    public static MutableComponent getTranslatable(String namespace) {
        return Component.translatable(getTranslationKey(namespace), namespace);
    }

    /**
     * @return A list of alternatives to {@link #getTranslationKey}
     * in a format of other existing mods.
     */
    public static List<String> getAlternativeTranslationKeys(String namespace) {
        return List.of(
            "modmenu.nameTranslation." + namespace,
            "jade.modName." + namespace,
            "itemGroup." + namespace
        );
    }

}
