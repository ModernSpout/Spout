package spout.clientview.model;

import net.minecraft.network.Connection;
import spout.api.clientview.model.ClientView;
import spout.clientview.packetmapping.itemstack.apply.reverse.ItemStackMappingReverser;
import org.jspecify.annotations.Nullable;

import java.lang.ref.WeakReference;

/**
 * An abstract implementation of {@link ClientView}
 * for clients that represent a {@link Connection}.
 */
public abstract class ConnectionClientViewImpl extends ClientViewImpl {

    /**
     * The {@link Connection} tied to this client.
     */
    private final WeakReference<Connection> connection;

    protected ConnectionClientViewImpl(Connection connection) {
        this.connection = new WeakReference<>(connection);
    }

    @Override
    public @Nullable Connection getConnection() {
        return this.connection.get();
    }

    @Override
    public @Nullable ItemStackMappingReverser getItemMappingReverser() {
        return this.getConnection().itemStackMappingReverser;
    }

}
