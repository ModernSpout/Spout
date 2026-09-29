package spout.gamecontent.datadriven.item.datapack;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import spout.gamecontent.datadriven.item.SpoutNonBuiltInItem;
import spout.server.paper.impl.moredatadriven.datapack.SpoutDataPackRegistries;

public final class LoadDataPackRegistryAction implements spout.gamecontent.datadriven.common.registry.delayedfrozen.LoadDataPackRegistryAction<SpoutNonBuiltInItem> {

    @Override
    public int getBeforeDelayedRegistryFreezingActionPriority() {
        return 1;
    }

    @Override
    public ResourceKey<? extends Registry<SpoutNonBuiltInItem>> getRegistryKey() {
        return SpoutDataPackRegistries.ITEM_FROM_DATA_PACK;
    }

    @Override
    public Codec<SpoutNonBuiltInItem> getCodec() {
        return SpoutNonBuiltInItem.CODEC.codec();
    }

}
