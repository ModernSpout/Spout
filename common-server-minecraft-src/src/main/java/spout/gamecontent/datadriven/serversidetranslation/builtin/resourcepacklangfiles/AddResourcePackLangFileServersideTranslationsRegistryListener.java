package spout.gamecontent.datadriven.serversidetranslation.builtin.resourcepacklangfiles;

import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import spout.gamecontent.datadriven.serversidetranslation.ServersideTranslation;
import spout.gamecontent.datadriven.serversidetranslation.ServersideTranslationFallbackScope;
import spout.gamecontent.datadriven.serversidetranslation.registry.ServersideTranslationRegistryKey;
import spout.server.paper.api.resourcepack.content.Lang;
import spout.server.paper.impl.configuration.SpoutGlobalConfiguration;
import spout.server.paper.impl.packetmapping.component.translatable.MinecraftLocaleUtil;
import spout.server.paper.impl.resourcepack.plugin.discover.PluginResourcePackDiscoveryImpl;
import spout.util.minecraft.registry.SpoutRegistryHookEvents;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class AddResourcePackLangFileServersideTranslationsRegistryListener implements SpoutRegistryHookEvents.Listener<ServersideTranslation> {

    @Override
    public Iterable<Pair<ResourceKey<Registry<ServersideTranslation>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
        return List.of(Pair.of(ServersideTranslationRegistryKey.SERVERSIDE_TRANSLATION, SpoutRegistryHookEvents.EventType.PRE_POPULATE_STATIC_REGISTRY));
    }

    @Override
    public void onRegistryHookEvent(final SpoutRegistryHookEvents.EventType type, final WritableRegistry<ServersideTranslation> registry) {
        // Add translations from included resource packs
        Comparator<MinecraftLocaleUtil.KnownLocale> localeComparator;
        {
            // Create the complete list of locales in preferred order
            List<MinecraftLocaleUtil.KnownLocale> localesInOrder = new ArrayList<>(MinecraftLocaleUtil.getKnownLocales().length);
            SpoutGlobalConfiguration.get().serverSideTranslations.preferredLocalesInOrder.stream().map(MinecraftLocaleUtil::getKnownLocale).filter(Objects::nonNull).forEach(localesInOrder::add);
            Set<MinecraftLocaleUtil.KnownLocale> localesInOrderSet = new HashSet<>(localesInOrder);
            if (localesInOrderSet.add(MinecraftLocaleUtil.getDefault())) {
                localesInOrder.add(MinecraftLocaleUtil.getDefault());
            }
            for (String languageGroup : MinecraftLocaleUtil.getLanguageGroups()) {
                MinecraftLocaleUtil.KnownLocale defaultLocale = MinecraftLocaleUtil.getDefaultKnownLocaleForLanguageGroup(languageGroup);
                if (defaultLocale != null && localesInOrderSet.add(defaultLocale)) {
                    localesInOrder.add(defaultLocale);
                }
            }
            for (MinecraftLocaleUtil.KnownLocale locale : MinecraftLocaleUtil.getKnownLocales()) {
                if (localesInOrderSet.add(locale)) {
                    localesInOrder.add(locale);
                }
            }
            Object2IntMap<MinecraftLocaleUtil.KnownLocale> localeOrdinal = new Object2IntOpenHashMap<>();
            for (int i = 0; i < localesInOrder.size(); i++) {
                localeOrdinal.put(localesInOrder.get(i), i);
            }
            localeComparator = Comparator.comparingInt(localeOrdinal::getInt);
        }
        Map<String, Map<MinecraftLocaleUtil.KnownLocale, String>> translations;
        {
            // Collect provided translations per key
            translations = new HashMap<>();
            for (Pair<PluginBootstrap, List<Pair<MinecraftLocaleUtil.KnownLocale, Lang>>> resourcePackLangs : PluginResourcePackDiscoveryImpl.get().getResourcePackLangs()) {
                for (Pair<MinecraftLocaleUtil.KnownLocale, Lang> lang : resourcePackLangs.right()) {
                    for (Pair<String, String> translation : lang.right().getTranslations()) {
                        translations.computeIfAbsent(translation.left(), $ -> new HashMap<>(1)).put(lang.left(), translation.right());
                    }
                }
            }
        }
        int[] registryElementI = {0};
        translations.forEach((key, translationPerLocale) -> {
            // Add the translations for the key
            List<Pair<MinecraftLocaleUtil.KnownLocale, String>> translationsSortedByLocale = new ArrayList<>(translationPerLocale.size());
            translationPerLocale.forEach((locale, translation) -> translationsSortedByLocale.add(Pair.of(locale, translation)));
            Collections.sort(translationsSortedByLocale, Comparator.comparing(Pair::left, localeComparator));
            for (int i = translationsSortedByLocale.size() - 1; i > 0; i--) {
                Pair<MinecraftLocaleUtil.KnownLocale, String> listedTranslation = translationsSortedByLocale.get(i);
                Registry.register(
                    registry,
                    Identifier.fromNamespaceAndPath(spout.branding.SpoutNamespace.SPOUT, "rplf_" + registryElementI[0]++),
                    new ServersideTranslation(
                        key, listedTranslation.right(), listedTranslation.left().lowerCaseLocale, ServersideTranslationFallbackScope.LANGUAGE_GROUP, true
                    )
                );
            }
            Pair<MinecraftLocaleUtil.KnownLocale, String> mostPreferredTranslation = translationsSortedByLocale.get(0);
            Registry.register(
                registry,
                Identifier.fromNamespaceAndPath(spout.branding.SpoutNamespace.SPOUT, "rplf_" + registryElementI[0]++),
                new ServersideTranslation(
                    key, mostPreferredTranslation.right(), mostPreferredTranslation.left().lowerCaseLocale, ServersideTranslationFallbackScope.ALL, true
                )
            );
        });
    }
}
