package spout.api.clientview.packetmapping.component.handle;

import net.kyori.adventure.text.Component;
import spout.api.clientview.packetmapping.component.registry.ComponentMappingRegistryEntry;
import spout.api.util.mapping.handle.WithContextMappingHandle;
import spout.api.util.mapping.handle.WithOriginalMappingHandle;

/**
 * A handle provided to code registered with {@link ComponentMappingRegistryEntry.Builder#setToFunction}.
 */
public interface ComponentMappingHandle extends WithContextMappingHandle<Component, ComponentMappingContext>, WithOriginalMappingHandle<Component> {
}
