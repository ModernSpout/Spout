package spout.gamecontent.datadriven.block.datapack;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import spout.gamecontent.datadriven.block.SpoutNonBuiltInBlock;
import spout.server.paper.impl.moredatadriven.datapack.SpoutDataPackRegistries;

public final class LoadDataPackRegistryAction implements spout.gamecontent.datadriven.common.registry.delayedfrozen.LoadDataPackRegistryAction<SpoutNonBuiltInBlock> {

    @Override
    public int getBeforeDelayedRegistryFreezingActionPriority() {
        return 0;
    }

    @Override
    public ResourceKey<? extends Registry<SpoutNonBuiltInBlock>> getRegistryKey() {
        return SpoutDataPackRegistries.BLOCK_FROM_DATA_PACK;
    }

    @Override
    public Codec<SpoutNonBuiltInBlock> getCodec() {
        return SpoutNonBuiltInBlock.CODEC;
    }

}
