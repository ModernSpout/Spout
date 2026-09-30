package spout.util.minecraft.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * Utility to send system chat messages to the client.
 */
public final class SendSystemChatUtil {

    private SendSystemChatUtil() {
        throw new UnsupportedOperationException();
    }

    public static void send(String message) {
        send(Component.literal(message));
    }

    public static void send(Component message) {
        Minecraft client = Minecraft.getInstance();
        if (client.player != null) {
            client.player.sendSystemMessage(message);
        }
    }

}
