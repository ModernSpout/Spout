package spout.api.gamecontent.datadriven.serversidetranslation.registry;

import io.papermc.paper.registry.RegistryBuilder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import spout.api.gamecontent.datadriven.serversidetranslation.ServersideTranslation;
import spout.api.gamecontent.datadriven.serversidetranslation.ServersideTranslationFallbackScope;

/**
 * A data-centric version-specific registry entry for the {@link ServersideTranslation} type.
 *
 * <p>
 * Translations that are registered later
 * replace translations that are registered with the same parameters before.
 * </p>
 *
 * <p>
 * Translations can also replace Minecraft vanilla translations,
 * such as for the key "{@code block.minecraft.bookshelf}".
 * </p>
 */
public interface ServersideTranslationRegistryEntry {

    /**
     * @return The last value set with {@link Builder#setKey}.
     */
    @Nullable String getKey();

    /**
     * @return The last value set with {@link Builder#setTranslation}.
     */
    @Nullable String getTranslation();

    /**
     * @return The last value set with {@link Builder#setLocale},
     * or the default value.
     */
    String getLocale();

    /**
     * @return The last value set with {@link Builder#setFallbackScope},
     * or the default value.
     */
    ServersideTranslationFallbackScope getFallbackScope();

    /**
     * @return The last value set with {@link Builder#setOverrideClientside},
     * or the default value.
     */
    boolean getOverrideClientside();

    /**
     * A mutable builder for a {@link ServersideTranslationRegistryEntry}.
     */
    @ApiStatus.NonExtendable
    interface Builder extends ServersideTranslationRegistryEntry, RegistryBuilder<ServersideTranslation> {

        /**
         * @param key The intended value for {@link ServersideTranslation#getLangKey()}.
         */
        void setKey(String key);

        /**
         * @param translation The intended value for {@link ServersideTranslation#getTranslation()}.
         */
        void setTranslation(String translation);

        /**
         * @param locale The intended value for {@link ServersideTranslation#getLocale()}.
         */
        void setLocale(String locale);

        /**
         * @param fallbackScope The intended value for {@link ServersideTranslation#getFallbackScope()}.
         */
        void setFallbackScope(ServersideTranslationFallbackScope fallbackScope);

        /**
         * @param overrideClientside The intended value for {@link ServersideTranslation#getOverrideClientside()}.
         */
        void setOverrideClientside(boolean overrideClientside);

    }

}
