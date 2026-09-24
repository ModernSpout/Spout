package spout.api.clientview.model;

import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import java.util.Arrays;

/**
 * Conversion utility for {@link ClientView.AwarenessLevel}.
 *
 * TODO Improve this: use something similar to the CraftRegistry.minecraftToBukkit call in CraftBlockType, based on namespaced keys
 */
public final class CraftAwarenessLevel {

    private CraftAwarenessLevel() {
        throw new UnsupportedOperationException();
    }

    public static AwarenessLevel fromBukkit(ClientView.AwarenessLevel level) {
        return Arrays.stream(AwarenessLevels.getAll()).filter(it -> it.getId() == level.getId()).findFirst().orElseThrow();
    }

    public static ClientView.AwarenessLevel toBukkit(AwarenessLevel level) {
        return Arrays.stream(ClientView.AwarenessLevel.getAll()).filter(it -> it.getId() == level.getId()).findFirst().orElseThrow();
    }

}
