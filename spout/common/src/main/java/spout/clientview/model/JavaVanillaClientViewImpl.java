package spout.clientview.model;

import net.minecraft.network.Connection;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.AwarenessLevels;

/**
 * A simple implementation of {@link ClientView}
 * for {@link AwarenessLevels#VANILLA} clients.
 */
public class JavaVanillaClientViewImpl extends ConnectionClientViewImpl {

    public JavaVanillaClientViewImpl(Connection connection) {
        super(connection);
    }

    @Override
    public AwarenessLevel getAwarenessLevel() {
        return AwarenessLevels.VANILLA;
    }

}
