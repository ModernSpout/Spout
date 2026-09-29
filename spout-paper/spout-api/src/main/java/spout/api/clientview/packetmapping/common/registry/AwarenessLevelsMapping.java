package spout.api.clientview.packetmapping.common.registry;

import java.util.List;
import spout.api.clientview.model.awarenesslevel.AwarenessLevel;

/**
 * A mapping that targets one or more particular {@link AwarenessLevel}s.
 */
public interface AwarenessLevelsMapping {

    /**
     * @return The {@link AwarenessLevel}s to which this mapping will be applied.
     */
    List<? extends AwarenessLevel> getAwarenessLevels();

}
