package spout.gamecontent.datadriven.item;

import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import spout.gamecontent.datadriven.common.delayedfrozenregistries.DelayedRegistryFreezing;

public class ItemRegistryDelayedFreezingProvider implements DelayedRegistryFreezing.Provider {

    @Override
    public Iterable<ResourceKey<? extends Registry<?>>> getDelayedFrozenRegistries() {
        return List.of(Registries.ITEM);
    }

}
