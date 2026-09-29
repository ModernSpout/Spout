package spout.gamecontent.datadriven.serversidetranslation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import spout.branding.SpoutNamespace;
import spout.util.minecraft.locale.MinecraftLocaleUtil;
import spout.util.mojang.codec.CodecUtil;
import spout.util.mojang.codec.EnumViaIdentifierCodec;

public record ServersideTranslation(
    String key,
    String translation,
    String locale,
    ServersideTranslationFallbackScope fallbackScope,
    boolean overrideClientside
) {

    public static final Codec<ServersideTranslation> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.STRING.fieldOf("key").forGetter(ServersideTranslation::key),
            Codec.STRING.fieldOf("translation").forGetter(ServersideTranslation::translation),
            CodecUtil.optionalFieldOf(Codec.STRING, "locale", () -> MinecraftLocaleUtil.getDefault().lowerCaseLocale).forGetter(ServersideTranslation::locale),
            new EnumViaIdentifierCodec<>(ServersideTranslationFallbackScope.class, SpoutNamespace.SPOUT).optionalFieldOf("fallback_scope", ServersideTranslationFallbackScope.ALL).forGetter(ServersideTranslation::fallbackScope),
            Codec.BOOL.optionalFieldOf("override_clientside", true).forGetter(ServersideTranslation::overrideClientside)
        ).apply(instance, ServersideTranslation::new)
    );

}
