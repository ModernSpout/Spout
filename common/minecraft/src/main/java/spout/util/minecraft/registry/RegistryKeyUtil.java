package spout.util.minecraft.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import spout.branding.SpoutNamespace;

public final class RegistryKeyUtil {

    private RegistryKeyUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Analogous to {@link Registries#createRegistryKey},
     * but can have a different namespace than {@link Identifier#DEFAULT_NAMESPACE}.
     */
    public static <T> ResourceKey<Registry<T>> create(String identifier) {
        return ResourceKey.createRegistryKey(Identifier.parse(identifier));
    }

    /**
     * Analogous to {@link Registries#createRegistryKey},
     * but with the {@link SpoutNamespace#SPOUT} namespace.
     */
    public static <T> ResourceKey<Registry<T>> createWithSpoutNamespace(String path) {
        return ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, path));
    }

}
