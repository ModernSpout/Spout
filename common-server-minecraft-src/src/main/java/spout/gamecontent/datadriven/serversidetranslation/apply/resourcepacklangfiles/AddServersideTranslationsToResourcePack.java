package spout.gamecontent.datadriven.serversidetranslation.apply.resourcepacklangfiles;

import com.google.gson.JsonParser;
import org.jspecify.annotations.Nullable;
import spout.api.clientview.model.ClientView;
import spout.gamecontent.datadriven.serversidetranslation.ServersideTranslation;
import spout.gamecontent.datadriven.serversidetranslation.apply.OptimizedServersideTranslations;
import spout.server.paper.api.resourcepack.content.Lang;
import spout.util.minecraft.locale.MinecraftLocaleUtil;
import spout.server.paper.impl.resourcepack.construct.ResourcePackConstructionImpl;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class AddServersideTranslationsToResourcePack {

    private AddServersideTranslationsToResourcePack() {
        throw new UnsupportedOperationException();
    }

    public static void addToResourcePack() {
        ResourcePackConstructionImpl.get().addEventInitializer(resourcePackConstructEvent -> {
            Map<String, Lang> languageFiles = exportForResourcePackAsLangs();
            for (ClientView.AwarenessLevel awarenessLevel : ClientView.AwarenessLevel.getAll()) {
                // Skip if the awareness level is not relevant
                if (!ResourcePackConstructionImpl.generateForAwarenessLevel(awarenessLevel)) continue;
                // Add the language files
                for (Map.Entry<String, Lang> entry : languageFiles.entrySet()) {
                    resourcePackConstructEvent.path(awarenessLevel, "assets/minecraft/lang/" + entry.getKey() + ".json").asLang().setMutable(entry.getValue());
                }
            }
        });
    }

    private static Map<String, Lang> exportForResourcePackAsLangs() {
        return exportForResourcePackAsMaps().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> {
            Lang lang = Lang.create();
            for (Map.Entry<String, String> mapsEntry : entry.getValue().entrySet()) {
                lang.putTranslation(mapsEntry.getKey(), mapsEntry.getValue());
            }
            return lang;
        }));
    }

    private static Map<String, Map<String, String>> exportForResourcePackAsMaps() {
        MinecraftLocaleUtil.KnownLocale defaultLocale = MinecraftLocaleUtil.getDefault();
        Set<String> vanillaKeys;
        try {
            vanillaKeys = JsonParser.parseString(new String(AddServersideTranslationsToResourcePack.class.getClassLoader().getResourceAsStream("assets/minecraft/lang/" + defaultLocale.lowerCaseLocale + ".json").readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject().keySet();
        } catch (IOException e) {
            throw new RuntimeException("Exception occurred while reading vanilla default language file");
        }
        @Nullable Map<String, Map<String, String>> exported = new HashMap<>();
        for (Map.Entry<String, OptimizedServersideTranslations.RegisteredTranslationsForKey> registeredTranslationEntry : OptimizedServersideTranslations.registeredTranslations.entrySet()) {

            // Unpack the entry
            String key = registeredTranslationEntry.getKey();
            OptimizedServersideTranslations.RegisteredTranslationsForKey translations = registeredTranslationEntry.getValue();
            boolean keyIsVanilla = vanillaKeys.contains(key);

            // Determine the translation for the default locale
            @Nullable ServersideTranslation defaultLocaleTranslation = keepIfAllowed(translations.localeTranslations.get(defaultLocale.lowerCaseLocale), keyIsVanilla);
            if (defaultLocaleTranslation == null) {
                if (defaultLocale.languageGroup != null) {
                    defaultLocaleTranslation = keepIfAllowed(translations.languageGroupTranslations.get(defaultLocale.languageGroup), keyIsVanilla);
                }
                if (defaultLocaleTranslation == null) {
                    defaultLocaleTranslation = keepIfAllowed(translations.genericTranslation, keyIsVanilla);
                }
            }

            // Fill in the translations for specific locales
            for (Map.Entry<String, ServersideTranslation> localeTranslationEntry : translations.localeTranslations.entrySet()) {

                // Unpack the entry
                String locale = localeTranslationEntry.getKey();
                @Nullable ServersideTranslation translation = keepIfAllowed(localeTranslationEntry.getValue(), keyIsVanilla);
                if (translation == null) continue;

                // Store in the map
                exported.computeIfAbsent(locale, $ -> new HashMap<>()).put(key, translation.translation());

            }

            // Fill in the translations for language groups, where not filled in yet
            if (translations.languageGroupTranslations != null) {
                for (Map.Entry<String, ServersideTranslation> languageGroupTranslationEntry : translations.languageGroupTranslations.entrySet()) {

                    // Unpack the entry
                    String languageGroup = languageGroupTranslationEntry.getKey();
                    @Nullable ServersideTranslation translation = keepIfAllowed(languageGroupTranslationEntry.getValue(), keyIsVanilla);
                    if (translation == null) continue;

                    // Store in the map
                    for (MinecraftLocaleUtil.KnownLocale locale : MinecraftLocaleUtil.getKnownLocalesForLanguageGroup(languageGroup)) {
                        exported.computeIfAbsent(locale.lowerCaseLocale, $ -> new HashMap<>()).putIfAbsent(key, translation.translation());
                    }

                }

                // Fill in the generic translation, where not filled in yet
                @Nullable ServersideTranslation genericTranslation = keepIfAllowed(translations.genericTranslation, keyIsVanilla);
                if (genericTranslation != null) {
                    // Only if the translation is different from the default one
                    if (defaultLocaleTranslation == null || !genericTranslation.translation().equals(defaultLocaleTranslation.translation())) {
                        // Store in the map
                        for (MinecraftLocaleUtil.KnownLocale locale : MinecraftLocaleUtil.getKnownLocales()) {
                            exported.computeIfAbsent(locale.lowerCaseLocale, $ -> new HashMap<>()).putIfAbsent(key, genericTranslation.translation());
                        }
                    }
                }

                // Remove any translations equal to the default
                if (defaultLocaleTranslation != null) {
                    for (Map.Entry<String, Map<String, String>> exportedEntry : exported.entrySet()) {
                        String translation = exportedEntry.getValue().get(key);
                        if (translation != null && translation.equals(defaultLocaleTranslation.translation())) {
                            exportedEntry.getValue().remove(key);
                        }
                    }
                    // Add the default back
                    exported.computeIfAbsent(defaultLocale.lowerCaseLocale, $ -> new HashMap<>()).put(key, defaultLocaleTranslation.translation());
                }

            }

        }
        exported = exported.entrySet().stream().filter(entry -> !entry.getValue().isEmpty()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return exported;
    }

    private static @Nullable ServersideTranslation keepIfAllowed(@Nullable ServersideTranslation translation, boolean keyIsVanilla) {
        return translation != null && (!keyIsVanilla || translation.overrideClientside()) ? translation : null;
    }

}
