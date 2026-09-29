package spout.api.gamecontent.datadriven.serversidetranslation.registry;

import io.papermc.paper.registry.PaperRegistryBuilder;
import io.papermc.paper.registry.data.util.Conversions;
import java.util.Objects;
import org.jspecify.annotations.Nullable;
import spout.api.gamecontent.datadriven.serversidetranslation.CraftServersideTranslationFallbackScope;
import spout.api.gamecontent.datadriven.serversidetranslation.ServersideTranslationFallbackScope;
import spout.gamecontent.datadriven.serversidetranslation.ServersideTranslation;
import spout.server.paper.impl.packetmapping.component.translatable.MinecraftLocaleUtil;

/**
 * The implementation for {@link ServersideTranslationRegistryEntry}.
 */
public class ServersideTranslationRegistryEntryImpl implements ServersideTranslationRegistryEntry, ServersideTranslationRegistryEntry.Builder {

    protected @Nullable String key;
    protected @Nullable String translation;
    protected String locale;
    protected spout.gamecontent.datadriven.serversidetranslation.ServersideTranslationFallbackScope fallbackScope;
    protected boolean overrideClientside;

    public ServersideTranslationRegistryEntryImpl(
        final Conversions ignoredConversions,
        final ServersideTranslation internal
    ) {
        if (internal == null) {
            this.locale = MinecraftLocaleUtil.getDefault().lowerCaseLocale;
            this.fallbackScope = spout.gamecontent.datadriven.serversidetranslation.ServersideTranslationFallbackScope.ALL;
            this.overrideClientside = true;
            return;
        }

        this.key = internal.key();
        this.translation = internal.translation();
        this.locale = internal.locale();
        this.fallbackScope = internal.fallbackScope();
        this.overrideClientside = internal.overrideClientside();
    }

    @Override
    public @Nullable String getKey() {
        return this.key;
    }

    @Override
    public void setKey(final String key) {
        this.key = key;
    }

    @Override
    public @Nullable String getTranslation() {
        return this.translation;
    }

    @Override
    public void setTranslation(final String translation) {
        this.translation = translation;
    }

    @Override
    public String getLocale() {
        return this.locale;
    }

    @Override
    public void setLocale(final String locale) {
        this.locale = locale;
    }

    @Override
    public ServersideTranslationFallbackScope getFallbackScope() {
        return CraftServersideTranslationFallbackScope.toBukkit(this.fallbackScope);
    }

    @Override
    public void setFallbackScope(final spout.api.gamecontent.datadriven.serversidetranslation.ServersideTranslationFallbackScope fallbackScope) {
        this.fallbackScope = CraftServersideTranslationFallbackScope.fromBukkit(fallbackScope);
    }

    @Override
    public boolean getOverrideClientside() {
        return this.overrideClientside;
    }

    @Override
    public void setOverrideClientside(final boolean overrideClientside) {
        this.overrideClientside = overrideClientside;
    }

    /**
     * The implementation for {@link ServersideTranslationRegistryEntry.Builder}.
     */
    public static final class Builder extends ServersideTranslationRegistryEntryImpl implements ServersideTranslationRegistryEntry.Builder,
        PaperRegistryBuilder<ServersideTranslation, spout.api.gamecontent.datadriven.serversidetranslation.ServersideTranslation> {

        public Builder(
            final Conversions conversions,
            final ServersideTranslation internal
        ) {
            super(conversions, internal);
        }

        @Override
        public ServersideTranslation build() {
            return new ServersideTranslation(
                Objects.requireNonNull(this.key, "No key given"),
                Objects.requireNonNull(this.translation, "No translation given"),
                this.locale,
                this.fallbackScope,
                this.overrideClientside
            );
        }

    }

}
