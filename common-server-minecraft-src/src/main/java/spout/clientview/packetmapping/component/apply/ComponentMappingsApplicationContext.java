package spout.clientview.packetmapping.component.apply;

import spout.api.clientview.model.ClientView;
import spout.clientview.packetmapping.WithClientViewMappingsApplicationContext;

/**
 * The context for applying mappings to a component.
 */
public final class ComponentMappingsApplicationContext extends WithClientViewMappingsApplicationContext {

    public ComponentMappingsApplicationContext(ClientView clientView) {
        super(clientView);
    }

}
