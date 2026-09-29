package spout.server.paper.api.resourcepack.construct;

import io.papermc.paper.plugin.lifecycle.event.LifecycleEvent;
import spout.api.clientview.model.awarenesslevel.AwarenessLevel;

/**
 * Called when the Spout server resource pack has been constructed.
 */
public interface ResourcePackConstructFinishEvent extends LifecycleEvent {

    /**
     * @param awarenessLevel A {@link AwarenessLevel}.
     * @return The constructed resource pack for the given awareness level.
     * @throws IllegalArgumentException If the given {@link AwarenessLevel}
     *                                  does not support a resource pack.
     */
    ConstructedResourcePack get(AwarenessLevel awarenessLevel);

}
