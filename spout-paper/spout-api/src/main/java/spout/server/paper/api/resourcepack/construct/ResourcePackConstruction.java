package spout.server.paper.api.resourcepack.construct;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.lifecycle.event.handler.configuration.PrioritizedLifecycleEventHandlerConfiguration;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEventType;
import spout.api.SpoutAPIServices;
import spout.api.util.composable.Composable;

/**
 * A service to construct the Spout server resource pack.
 */
public interface ResourcePackConstruction extends Composable<ResourcePackConstructEvent> {

    /**
     * @return The {@link ResourcePackConstruction} instance.
     */
    static ResourcePackConstruction get() {
        return SpoutAPIServices.getResourcePackConstruction();
    }

    /**
     * @return The {@link LifecycleEventType} for when constructing the resource pack has finished.
     */
    LifecycleEventType<BootstrapContext, ResourcePackConstructFinishEvent, PrioritizedLifecycleEventHandlerConfiguration<BootstrapContext>> finish();

}
