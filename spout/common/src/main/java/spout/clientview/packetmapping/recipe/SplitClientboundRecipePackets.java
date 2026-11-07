package spout.clientview.packetmapping.recipe;

import net.minecraft.network.protocol.game.ClientboundRecipeBookAddPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility that sends either one or multiple recipe packets instead of one,
 * to avoid having a single packet being too large.
 */
public final class SplitClientboundRecipePackets {

    private SplitClientboundRecipePackets() {
        throw new UnsupportedOperationException();
    }

    public static final int MAX_RECIPES_PER_PACKET = 100;

    public static void send(ServerGamePacketListenerImpl connection, List<ClientboundRecipeBookAddPacket.Entry> recipes, boolean replace) {
        for (int i = 0; i < recipes.size(); i += MAX_RECIPES_PER_PACKET) {
            connection.send(new ClientboundRecipeBookAddPacket(new ArrayList<>(recipes.subList(i, Math.min(recipes.size(), i + MAX_RECIPES_PER_PACKET))), i == 0 && replace));
        }
    }

}
