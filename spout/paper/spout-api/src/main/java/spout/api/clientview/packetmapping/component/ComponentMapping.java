package spout.api.clientview.packetmapping.component;

import org.bukkit.Keyed;
import spout.api.clientview.packetmapping.common.registry.AwarenessLevelsMapping;
import spout.api.clientview.packetmapping.component.handle.ComponentMappingHandle;
import spout.api.util.mapping.FromMapping;
import spout.api.util.mapping.FunctionMapping;

public interface ComponentMapping extends AwarenessLevelsMapping, FromMapping<ComponentTarget>, FunctionMapping<ComponentMappingHandle>, Keyed {
}
