package spout.api.gamecontent.datadriven.serversidetranslation;

/**
 * Conversion utility for {@link ServersideTranslationFallbackScope}.
 */
public final class CraftServersideTranslationFallbackScope {

    private CraftServersideTranslationFallbackScope() {
        throw new UnsupportedOperationException();
    }

    public static spout.gamecontent.datadriven.serversidetranslation.ServersideTranslationFallbackScope fromBukkit(ServersideTranslationFallbackScope target) {
        return spout.gamecontent.datadriven.serversidetranslation.ServersideTranslationFallbackScope.valueOf(target.name());
    }

    public static ServersideTranslationFallbackScope toBukkit(spout.gamecontent.datadriven.serversidetranslation.ServersideTranslationFallbackScope target) {
        return ServersideTranslationFallbackScope.valueOf(target.name());
    }

}
