package spout.gamecontent.datadriven.common.registry.delayedfrozen;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.RegistryValidator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.CloseableResourceManager;

public interface LoadDataPackRegistryAction<T> extends BeforeDelayedRegistryFreezingActions.Action {

    ResourceKey<? extends Registry<T>> getRegistryKey();

    Codec<T> getCodec();

    @Override
    default CompletableFuture<?> runBeforeDelayedRegistryFreezing(CloseableResourceManager resources, Executor mainThreadExecutor) {
        RegistryDataLoader.RegistryData<T> registryData = new RegistryDataLoader.RegistryData<>(this.getRegistryKey(), this.getCodec(), RegistryValidator.none());
        return RegistryDataLoader.load(resources, java.util.Collections.emptyList(), List.of(registryData), mainThreadExecutor);
    }

}
