package spout.clientview.packetmapping.component.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import spout.clientview.packetmapping.component.ComponentMapping;
import spout.util.minecraft.registry.RegistryKeyUtil;

/**
 * Holder for {@link #COMPONENT_MAPPING}.
 *
 * <p>
 * Analogous to {@link Registries}.
 * </p>
 */
public final class ComponentMappingRegistryKey {

    private ComponentMappingRegistryKey() {
        throw new UnsupportedOperationException();
    }

    /**
     * Key for the component mapping registry.
     */
    public static final ResourceKey<Registry<ComponentMapping>> COMPONENT_MAPPING = RegistryKeyUtil.createWithSpoutNamespace("component_mapping");

}
