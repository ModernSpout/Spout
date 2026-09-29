package spout.gamecontent.datadriven.serversidetranslation;

/**
 * An extent to which a registered translation can be used as a fallback.
 */
public enum ServersideTranslationFallbackScope {
    /**
     * The translation is only used for the specific locale.
     *
     * <p>
     * For example, when used with the locale {@code en_gb}, the translation with only apply to users who
     * have the locale {@code en_gb}.
     * </p>
     */
    NONE,
    /**
     * The translation is used for the specific locale,
     * but can also be used for other locales of the same language group if no specific
     * translation is available for that locale.
     *
     * <p>
     * For example, when used with the locale {@code en_gb}, the translation will apply to users who
     * have the locale {@code en_gb}, but also to any users who have a different {@code en_} locale,
     * such as {@code en_us} (unless a more specific translation has been registered).
     * </p>
     */
    LANGUAGE_GROUP,
    /**
     * The translation is used for the specific locale,
     * but can also be used for any other locale if no specific or {@link #LANGUAGE_GROUP} fallback
     * translation is available for that locale.
     *
     * <p>
     * For example, when used with the locale {@code en_gb}, the translation will apply to users who
     * have the locale {@code en_gb}, but also to any users who have another locale, such as {@code de_de}
     * (unless a more specific translation has been registered).
     * </p>
     */
    ALL
}
