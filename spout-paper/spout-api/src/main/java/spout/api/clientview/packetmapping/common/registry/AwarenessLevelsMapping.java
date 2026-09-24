package spout.api.clientview.packetmapping.common.registry;

import java.util.List;
import spout.api.clientview.model.ClientView;

/**
 * A mapping that targets one or more particular {@link ClientView.AwarenessLevel}s.
 */
public interface AwarenessLevelsMapping {

    /**
     * @return The {@link ClientView.AwarenessLevel}s to which this mapping will be applied.
     */
    List<? extends ClientView.AwarenessLevel> getAwarenessLevels();

}
