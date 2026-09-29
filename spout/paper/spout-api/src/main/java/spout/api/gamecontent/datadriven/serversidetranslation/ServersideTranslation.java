package spout.api.gamecontent.datadriven.serversidetranslation;

import org.bukkit.Keyed;

public interface ServersideTranslation extends Keyed {

    /**
     * @return The lower-case key, for example "{@code item.example.ash}".
     */
    String getLangKey();

    /**
     * @return The desired translation, for example "{@code 灰}".
     */
    String getTranslation();

    /**
     * @return A lower-case locale that exists in Minecraft,
     * for example "{@code ja_jp}" for Japanese.
     */
    String getLocale();

    /**
     * @return The extent to which the translation can serve as a fallback.
     */
    ServersideTranslationFallbackScope getFallbackScope();

    /**
     * @return Whether this translation also overrides translations already registered
     * on the client (such as vanilla names for Minecraft blocks and items).
     */
    boolean getOverrideClientside();

}
