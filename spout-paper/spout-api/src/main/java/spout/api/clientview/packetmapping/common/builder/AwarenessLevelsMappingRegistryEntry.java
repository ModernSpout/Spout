package spout.api.clientview.packetmapping.common.builder;

import java.util.List;
import org.jspecify.annotations.Nullable;
import spout.api.clientview.model.ClientView;

/**
 * Provides getters for {@link AwarenessLevelsMappingRegistryEntryBuilder}.
 */
public interface AwarenessLevelsMappingRegistryEntry {

    /**
     * @return The {@link ClientView.AwarenessLevel}s to which this mapping will be applied,
     * or null if not set.
     */
    @Nullable List<? extends ClientView.AwarenessLevel> getAwarenessLevels();

}
