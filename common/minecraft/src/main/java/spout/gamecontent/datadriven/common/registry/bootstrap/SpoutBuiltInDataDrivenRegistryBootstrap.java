package spout.gamecontent.datadriven.common.registry.bootstrap;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import java.util.ServiceLoader;

/**
 * For bootstrapping Spout built-in data-driven registries.
 */
public final class SpoutBuiltInDataDrivenRegistryBootstrap {

    private SpoutBuiltInDataDrivenRegistryBootstrap() {
        throw new UnsupportedOperationException();
    }

    public interface Provider {

        Registry<?> provideDataDrivenRegistry();

    }

    /**
     * Bootstraps the Spout registries.
     *
     * <p>
     * This must be called during {@link BuiltInRegistries#bootStrap()}.
     * </p>
     */
    public static Registry<?> bootstrap() {
        return (Registry<?>) ServiceLoader.load(Provider.class).stream().map(ServiceLoader.Provider::get).map(Provider::provideDataDrivenRegistry).toArray()[0];
    }

}
