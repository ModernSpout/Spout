package spout.api.clientview.packetmapping.component.registry;

import io.papermc.paper.registry.RegistryBuilder;
import org.jetbrains.annotations.ApiStatus;
import spout.api.clientview.packetmapping.common.builder.AwarenessLevelsMappingRegistryEntry;
import spout.api.clientview.packetmapping.common.builder.AwarenessLevelsMappingRegistryEntryBuilder;
import spout.api.clientview.packetmapping.component.ComponentMapping;
import spout.api.clientview.packetmapping.component.ComponentTarget;
import spout.api.clientview.packetmapping.component.handle.ComponentMappingHandle;
import spout.api.util.mapping.builder.FromRegistryEntry;
import spout.api.util.mapping.builder.FromRegistryEntryBuilder;
import spout.api.util.mapping.builder.FunctionRegistryEntry;
import spout.api.util.mapping.builder.FunctionRegistryEntryBuilder;

/**
 * A data-centric version-specific registry entry for the {@link ComponentMapping} type.
 */
public interface ComponentMappingRegistryEntry extends AwarenessLevelsMappingRegistryEntry, FromRegistryEntry<ComponentTarget>, FunctionRegistryEntry<ComponentMappingHandle> {

    /**
     * A mutable builder for a {@link ComponentMappingRegistryEntry}.
     */
    @ApiStatus.NonExtendable
    interface Builder extends ComponentMappingRegistryEntry, RegistryBuilder<ComponentMapping>, AwarenessLevelsMappingRegistryEntryBuilder, FromRegistryEntryBuilder<ComponentTarget>, FunctionRegistryEntryBuilder<ComponentMappingHandle> {
    }

}
