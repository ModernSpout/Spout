package spout.server.paper.api.packetmapping.component;

import spout.server.paper.api.packetmapping.AwarenessLevelMappingBuilder;
import spout.util.composable.FromBuilder;
import spout.util.composable.FunctionBuilder;

/**
 * A builder to define a component mapping.
 */
public interface ComponentMappingBuilder extends AwarenessLevelMappingBuilder, FromBuilder<ComponentTarget>, FunctionBuilder<ComponentMappingHandle> {
}
