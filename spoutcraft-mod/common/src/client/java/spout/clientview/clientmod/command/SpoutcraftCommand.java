package spout.clientview.clientmod.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.network.chat.Component;
import spout.clientview.clientmod.protocol.ClientModCustomContentReceiving;
import spout.clientview.clientmod.protocol.SpoutProtocol;

/**
 * Client-side diagnostics for Spoutcraft.
 */
public final class SpoutcraftCommand {

    private static final String NAME = "spoutcraft";
    private static final String UNKNOWN = "unknown";

    private SpoutcraftCommand() {
        throw new UnsupportedOperationException();
    }

    public static void register(CommandDispatcher<ClientSuggestionProvider> dispatcher) {
        dispatcher.register(
            LiteralArgumentBuilder.<ClientSuggestionProvider>literal(NAME)
                .executes(context -> {
                    showInfo();
                    return Command.SINGLE_SUCCESS;
                })
        );
    }

    public static boolean executeIfSpoutcraftCommand(String command) {
        if (!command.equals(NAME)) {
            return false;
        }
        showInfo();
        return true;
    }

    private static void showInfo() {
        ClientModCustomContentReceiving.LoadedContentSummary summary =
            ClientModCustomContentReceiving.getLoadedContentSummary();

        send("Spoutcraft");
        send("State: " + SpoutProtocol.getState());
        send("Server protocol: " + protocolRange());
        send("Negotiated protocol: " + unknownIfNegative(SpoutProtocol.getSelectedProtocolVersion()));
        send("Loaded blocks: " + summary.blocks());
        send("Loaded block states: " + summary.blockStates());
        send("Loaded items: " + summary.items());
        send("Registry id lists: " + summary.registryIdLists());
        send("Block-state id lists: " + summary.blockStateIdLists());
    }

    private static String protocolRange() {
        int min = SpoutProtocol.getServerMinProtocolVersion();
        int max = SpoutProtocol.getServerMaxProtocolVersion();
        if (min < 0 || max < 0) {
            return UNKNOWN;
        }
        return min + "-" + max;
    }

    private static String unknownIfNegative(int value) {
        return value < 0 ? UNKNOWN : Integer.toString(value);
    }

    private static void send(String message) {
        Minecraft client = Minecraft.getInstance();
        if (client.player != null) {
            client.player.sendSystemMessage(Component.literal(message));
        }
    }

}
