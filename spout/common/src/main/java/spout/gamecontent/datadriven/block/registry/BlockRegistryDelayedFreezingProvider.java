package spout.gamecontent.datadriven.block.registry;

import it.unimi.dsi.fastutil.objects.ObjectBooleanImmutablePair;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import spout.gamecontent.datadriven.common.registry.delayedfrozen.DelayedRegistryFreezing;
import java.util.List;

public final class BlockRegistryDelayedFreezingProvider implements DelayedRegistryFreezing.Provider {

    @Override
    public Iterable<ObjectBooleanImmutablePair<ResourceKey<? extends Registry<?>>>> getDelayedFrozenRegistries() {
        return List.of(ObjectBooleanImmutablePair.of(Registries.BLOCK, true));
    }

}
