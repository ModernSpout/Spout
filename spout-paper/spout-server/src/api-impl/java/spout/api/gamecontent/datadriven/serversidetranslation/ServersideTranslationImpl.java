package spout.api.gamecontent.datadriven.serversidetranslation;

import io.papermc.paper.registry.HolderableBase;
import net.minecraft.core.Holder;

public class ServersideTranslationImpl extends HolderableBase<spout.gamecontent.datadriven.serversidetranslation.ServersideTranslation> implements ServersideTranslation {

    public ServersideTranslationImpl(Holder<spout.gamecontent.datadriven.serversidetranslation.ServersideTranslation> holder) {
        super(holder);
    }

    @Override
    public String getLangKey() {
        return this.holder.value().key();
    }

    @Override
    public String getTranslation() {
        return this.holder.value().translation();
    }

    @Override
    public String getLocale() {
        return this.holder.value().locale();
    }

    @Override
    public ServersideTranslationFallbackScope getFallbackScope() {
        return CraftServersideTranslationFallbackScope.toBukkit(this.holder.value().fallbackScope());
    }

    @Override
    public boolean getOverrideClientside() {
        return this.holder.value().overrideClientside();
    }

}
