package spout.gamecontent.datadriven.serversidetranslation.apply;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.Nullable;
import spout.gamecontent.datadriven.serversidetranslation.ServersideTranslation;
import spout.gamecontent.datadriven.serversidetranslation.ServersideTranslationFallbackScope;
import spout.gamecontent.datadriven.serversidetranslation.apply.resourcepacklangfiles.AddServersideTranslationsToResourcePack;
import spout.gamecontent.datadriven.serversidetranslation.registry.ServersideTranslationRegistryKey;
import spout.server.paper.impl.moredatadriven.namespace.NamespaceNames;
import spout.util.minecraft.locale.MinecraftLocaleUtil;
import spout.util.minecraft.registry.SpoutRegistryHookEvents;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public final class OptimizedServersideTranslations {

    private OptimizedServersideTranslations() {
        throw new UnsupportedOperationException();
    }

    public static final class RegistryFreezeListener implements SpoutRegistryHookEvents.Listener<ServersideTranslation> {

        @Override
        public Iterable<Pair<ResourceKey<Registry<ServersideTranslation>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
            return List.of(Pair.of(ServersideTranslationRegistryKey.SERVERSIDE_TRANSLATION, SpoutRegistryHookEvents.EventType.POST_FREEZE));
        }

        @Override
        public void onRegistryHookEvent(final SpoutRegistryHookEvents.EventType type, final WritableRegistry<ServersideTranslation> registry) {
            build(registry);
        }

    }

    /**
     * A map of the registered translations per key.
     */
    public final static Map<String, RegisteredTranslationsForKey> registeredTranslations = new HashMap<>();

    public static final class RegisteredTranslationsForKey {

        /**
         * A translation that can act as the fallback for any locale,
         * or null if none is registered.
         */
        public @Nullable ServersideTranslation genericTranslation;

        /**
         * Translations that can act as the fallback for specific language groups,
         * indexed by their lower-case language group,
         * or null if none are registered.
         */
        public @Nullable Map<String, ServersideTranslation> languageGroupTranslations;

        /**
         * Translations for specific locales,
         * indexed by their lower-case locale.
         */
        public Map<String, ServersideTranslation> localeTranslations = new HashMap<>(2);

    }

    /**
     * @return Whether any translations are registered for the given key.
     */
    public static boolean hasAny(String key) {
        String lowerCaseKey = key.toLowerCase(Locale.ROOT);
        RegisteredTranslationsForKey registeredTranslationsForKey = registeredTranslations.get(lowerCaseKey);
        if (registeredTranslationsForKey != null) {
            if (registeredTranslationsForKey.genericTranslation != null || (registeredTranslationsForKey.languageGroupTranslations != null && !registeredTranslationsForKey.languageGroupTranslations.isEmpty()) || !registeredTranslationsForKey.localeTranslations.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return The best registered server-side translation for the given key and locale,
     * or null if none could be found.
     */
    public static @Nullable ServersideTranslation get(String key, @Nullable String locale) {
        String lowerCaseKey = key.toLowerCase(Locale.ROOT);
        @Nullable String lowerCaseLocale = locale == null ? null : locale.toLowerCase(Locale.ROOT);
        @Nullable RegisteredTranslationsForKey translationsForKey = registeredTranslations.get(lowerCaseKey);
        if (translationsForKey == null) {
            return null;
        }
        @Nullable ServersideTranslation translation = null;
        if (lowerCaseLocale != null) {
            translation = translationsForKey.localeTranslations == null ? null : translationsForKey.localeTranslations.get(lowerCaseLocale);
            if (translation != null && translation.overrideClientside()) {
                return translation;
            }
            @Nullable String group = MinecraftLocaleUtil.getLanguageGroup(lowerCaseLocale);
            if (group != null) {
                @Nullable ServersideTranslation alternative = translationsForKey.languageGroupTranslations == null ? null : translationsForKey.languageGroupTranslations.get(group);
                if (alternative != null) {
                    if (alternative.overrideClientside()) {
                        return alternative;
                    }
                    if (translation == null) {
                        translation = alternative;
                    }
                }
            }
        }
        @Nullable ServersideTranslation alternative = translationsForKey.genericTranslation;
        if (alternative != null) {
            if (alternative.overrideClientside()) {
                return alternative;
            }
            if (translation == null) {
                translation = alternative;
            }
        }
        return translation;
    }

    /**
     * Fills this class from the mappings in the corresponding registry.
     */
    public static void build(Registry<ServersideTranslation> registry) {
        // Add the translations from the register
        registry.stream().forEach(OptimizedServersideTranslations::addTranslationFromRegistry);
        // Add additional translations
        addNamespaceTranslations();
        // Build the resource pack language files from the result
        AddServersideTranslationsToResourcePack.addToResourcePack();
    }

    private static void addTranslationFromRegistry(ServersideTranslation translationToAdd) {
        String key = translationToAdd.key();
        String locale = translationToAdd.locale();
        ServersideTranslationFallbackScope fallbackScope = translationToAdd.fallbackScope();
        boolean overrideClientside = translationToAdd.overrideClientside();

        // Do some basic checks for the arguments
        String lowerCaseKey = key.toLowerCase(Locale.ROOT);
        if (!key.equals(lowerCaseKey)) {
            throw new IllegalArgumentException("The given key (" + key + ") is not lower-case");
        }
        String trimmedKey = key.trim();
        if (!key.equals(trimmedKey)) {
            throw new IllegalArgumentException("The given key (" + key + ") includes whitespace");
        }
        String lowerCaseLocale = locale.toLowerCase(Locale.ROOT);
        if (!locale.equals(lowerCaseLocale)) {
            throw new IllegalArgumentException("The given locale (" + locale + ") is not lower-case");
        }
        String trimmedLocale = locale.trim();
        if (!locale.equals(trimmedLocale)) {
            throw new IllegalArgumentException("The given locale (" + locale + ") includes whitespace");
        }

        // Add the translations
        RegisteredTranslationsForKey translationsForKey = registeredTranslations.computeIfAbsent(key, $ -> new RegisteredTranslationsForKey());
        if (fallbackScope.equals(ServersideTranslationFallbackScope.ALL)) {
            // Add as generic fallback
            if (translationsForKey.genericTranslation == null || overrideClientside || !translationsForKey.genericTranslation.overrideClientside()) {
                translationsForKey.genericTranslation = translationToAdd;
            }
        }
        if (!fallbackScope.equals(ServersideTranslationFallbackScope.NONE)) {
            // Add as group fallback
            int underscoreIndex = locale.indexOf('_');
            if (underscoreIndex > 0) {
                String group = locale.substring(0, underscoreIndex);
                if (translationsForKey.languageGroupTranslations == null) {
                    translationsForKey.languageGroupTranslations = new HashMap<>(2);
                    translationsForKey.languageGroupTranslations.put(group, translationToAdd);
                } else {
                    translationsForKey.languageGroupTranslations.compute(group, ($, existingTranslation) -> {
                        if (existingTranslation == null || overrideClientside || !existingTranslation.overrideClientside()) {
                            return translationToAdd;
                        }
                        return existingTranslation;
                    });
                }
            }
        }
        translationsForKey.localeTranslations.compute(locale, ($, existingTranslation) -> {
            if (existingTranslation == null || overrideClientside || !existingTranslation.overrideClientside()) {
                return translationToAdd;
            }
            return existingTranslation;
        });

    }

    private static void addNamespaceTranslations() {
        List<Pair<String, RegisteredTranslationsForKey>> namespaces = registeredTranslations.entrySet().stream()
            .map(entry -> {
                String namespace = NamespaceNames.parseNamespaceFromTranslationKey(entry.getKey());
                return namespace == null ? null : Pair.of(namespace, entry.getValue());
            }).filter(Objects::nonNull).toList();
        for (Pair<String, RegisteredTranslationsForKey> namespace : namespaces) {
            RegisteredTranslationsForKey translations = namespace.second();
            for (String alternativeTranslationKey : NamespaceNames.getAlternativeTranslationKeys(namespace.first())) {
                registeredTranslations.compute(alternativeTranslationKey, (_, existing) -> {
                    if (existing == null) {
                        return translations;
                    }
                    // Not entirely accurate, but good enough for now
                    if (existing.genericTranslation == null) {
                        existing.genericTranslation = translations.genericTranslation;
                    }
                    if (existing.languageGroupTranslations == null) {
                        existing.languageGroupTranslations = translations.languageGroupTranslations;
                    } else if (translations.languageGroupTranslations != null) {
                        for (Map.Entry<String, ServersideTranslation> translation : translations.languageGroupTranslations.entrySet()) {
                            existing.languageGroupTranslations.putIfAbsent(translation.getKey(), translation.getValue());
                        }
                    }
                    for (Map.Entry<String, ServersideTranslation> translation : translations.localeTranslations.entrySet()) {
                        existing.localeTranslations.putIfAbsent(translation.getKey(), translation.getValue());
                    }
                    return existing;
                });
            }
        }
    }

}
