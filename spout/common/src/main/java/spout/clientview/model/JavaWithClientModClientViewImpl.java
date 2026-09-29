package spout.clientview.model;

import net.minecraft.network.Connection;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.AwarenessLevels;

/**
 * A simple implementation of {@link ClientView}
 * for {@link AwarenessLevels#CLIENT_MOD} clients.
 */
public class JavaWithClientModClientViewImpl extends ConnectionClientViewImpl {

    public JavaWithClientModClientViewImpl(Connection connection) {
        super(connection);
    }

    @Override
    public AwarenessLevel getAwarenessLevel() {
        return AwarenessLevels.CLIENT_MOD;
    }

}
