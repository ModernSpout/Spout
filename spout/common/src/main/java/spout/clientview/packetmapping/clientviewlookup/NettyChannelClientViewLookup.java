package spout.clientview.packetmapping.clientviewlookup;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.jspecify.annotations.Nullable;
import spout.branding.SpoutNamespace;
import java.lang.ref.WeakReference;

/**
 * Allows storing a {@link ClientViewLookup} in a Netty {@link Channel}.
 */
public final class NettyChannelClientViewLookup {

    private NettyChannelClientViewLookup() {
        throw new UnsupportedOperationException();
    }

    /**
     * An {@link AttributeKey} that is used to {@linkplain Channel#attr store} the {@link ClientViewLookup}
     * in its {@link Channel}.
     */
    private static final AttributeKey<WeakReference<ClientViewLookup>> ATTRIBUTE_KEY = AttributeKey.valueOf(SpoutNamespace.SPOUT + ":client_view_lookup");

    public static void set(Channel channel, ClientViewLookup lookup) {
        channel.attr(ATTRIBUTE_KEY).set(new WeakReference<>(lookup));
    }

    public static @Nullable WeakReference<ClientViewLookup> getWeakReference(Channel channel) {
        return channel.attr(ATTRIBUTE_KEY).get();
    }

}
