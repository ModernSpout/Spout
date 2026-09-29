package spout.clientview.model;

import net.minecraft.network.Connection;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.AwarenessLevels;

/**
 * A simple implementation of {@link ClientView}
 * for {@link AwarenessLevels#RESOURCE_PACK} clients.
 */
public class JavaWithResourcePackClientViewImpl extends ConnectionClientViewImpl {

    public JavaWithResourcePackClientViewImpl(Connection connection) {
        super(connection);
    }

    @Override
    public AwarenessLevel getAwarenessLevel() {
        return AwarenessLevels.RESOURCE_PACK;
    }

}
