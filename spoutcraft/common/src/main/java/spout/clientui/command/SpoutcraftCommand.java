package spout.clientui.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.network.chat.Component;
import spout.clientview.clientmod.protocol.ClientModStateHandler;
import spout.util.minecraft.chat.SendSystemChatUtil;

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
        ClientModStateHandler.Summary summary = ClientModStateHandler.getSummary();

        SendSystemChatUtil.send(Component.literal("- ").withStyle(ChatFormatting.GRAY).append(Component.literal("Spoutcraft diagnostics").withStyle(ChatFormatting.BOLD, ChatFormatting.WHITE)).append(Component.literal(" -").withStyle(ChatFormatting.GRAY)));
        SendSystemChatUtil.send(Component.literal("State: ").withStyle(ChatFormatting.GRAY).append(Component.literal("" + summary.state()).withStyle(ChatFormatting.WHITE)));
        SendSystemChatUtil.send(Component.literal("Server protocol: ").withStyle(ChatFormatting.GRAY).append(Component.literal(protocolRange(summary)).withStyle(ChatFormatting.WHITE)));
        SendSystemChatUtil.send(Component.literal("Negotiated protocol: ").withStyle(ChatFormatting.GRAY).append(Component.literal(unknownIfNegative(summary.selectedProtocolVersion())).withStyle(ChatFormatting.WHITE)));
        SendSystemChatUtil.send(Component.literal("Loaded blocks: ").withStyle(ChatFormatting.GRAY).append(Component.literal("" + summary.loadedContent().getBlocks()).withStyle(ChatFormatting.WHITE)));
        SendSystemChatUtil.send(Component.literal("Loaded block states: ").withStyle(ChatFormatting.GRAY).append(Component.literal("" + summary.loadedContent().getBlockStates()).withStyle(ChatFormatting.WHITE)));
        SendSystemChatUtil.send(Component.literal("Loaded items: ").withStyle(ChatFormatting.GRAY).append(Component.literal("" + summary.loadedContent().getItems()).withStyle(ChatFormatting.WHITE)));
        SendSystemChatUtil.send(Component.literal("Received registry id lists: ").withStyle(ChatFormatting.GRAY).append(Component.literal("" + summary.loadedContent().getRegistryEntryIdLists()).withStyle(ChatFormatting.WHITE)));
        SendSystemChatUtil.send(Component.literal("Received block state id lists: ").withStyle(ChatFormatting.GRAY).append(Component.literal("" + summary.loadedContent().getBlockStateRegistryEntryIdLists()).withStyle(ChatFormatting.WHITE)));
    }

    private static String protocolRange(ClientModStateHandler.Summary summary) {
        int min = summary.serverMinProtocolVersion();
        int max = summary.serverMaxProtocolVersion();
        if (min < 0 || max < 0) {
            return UNKNOWN;
        }
        return min == max ? "" + min : min + " - " + max;
    }

    private static String unknownIfNegative(int value) {
        return value < 0 ? UNKNOWN : Integer.toString(value);
    }

}
