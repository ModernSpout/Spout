package spout.api.clientview.model.awarenesslevel;

import io.papermc.paper.registry.HolderableBase;
import net.minecraft.core.Holder;

public final class AwarenessLevelImpl extends HolderableBase<spout.clientview.model.awarenesslevel.AwarenessLevel> implements AwarenessLevel {

    public AwarenessLevelImpl(final Holder<spout.clientview.model.awarenesslevel.AwarenessLevel> holder) {
        super(holder);
    }

    @Override
    public boolean alwaysUnderstandsAllServerSideTranslatables() {
        return this.holder.value().alwaysUnderstandsAllServerSideTranslatables();
    }

    @Override
    public boolean alwaysUnderstandsAllServerSideItems() {
        return this.holder.value().alwaysUnderstandsAllServerSideItems();
    }

    @Override
    public boolean alwaysUnderstandsAllServerSideBlocks() {
        return this.holder.value().alwaysUnderstandsAllServerSideBlocks();
    }

}
