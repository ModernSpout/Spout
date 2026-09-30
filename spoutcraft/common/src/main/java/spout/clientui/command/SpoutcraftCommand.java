package spout.clientui.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.network.chat.Component;
import spout.clientview.clientmod.protocol.CurrentLoadedContentDiagnosticSummary;
import spout.clientview.clientmod.protocol.SpoutProtocol;
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
        CurrentLoadedContentDiagnosticSummary summary = CurrentLoadedContentDiagnosticSummary.INSTANCE;

        SendSystemChatUtil.send(Component.literal("- ").withStyle(ChatFormatting.GRAY).append(Component.literal("Spoutcraft diagnostics").withStyle(ChatFormatting.BOLD, ChatFormatting.WHITE)).append(Component.literal(" -").withStyle(ChatFormatting.GRAY)));
        SendSystemChatUtil.send(Component.literal("State: ").withStyle(ChatFormatting.GRAY).append(Component.literal("" + SpoutProtocol.getState()).withStyle(ChatFormatting.WHITE)));
        SendSystemChatUtil.send(Component.literal("Server protocol: ").withStyle(ChatFormatting.GRAY).append(Component.literal(protocolRange()).withStyle(ChatFormatting.WHITE)));
        SendSystemChatUtil.send(Component.literal("Negotiated protocol: ").withStyle(ChatFormatting.GRAY).append(Component.literal(unknownIfNegative(SpoutProtocol.getSelectedProtocolVersion())).withStyle(ChatFormatting.WHITE)));
        SendSystemChatUtil.send(Component.literal("Loaded blocks: ").withStyle(ChatFormatting.GRAY).append(Component.literal("" + summary.getBlocks()).withStyle(ChatFormatting.WHITE)));
        SendSystemChatUtil.send(Component.literal("Loaded block states: ").withStyle(ChatFormatting.GRAY).append(Component.literal("" + summary.getBlockStates()).withStyle(ChatFormatting.WHITE)));
        SendSystemChatUtil.send(Component.literal("Loaded items: ").withStyle(ChatFormatting.GRAY).append(Component.literal("" + summary.getItems()).withStyle(ChatFormatting.WHITE)));
        SendSystemChatUtil.send(Component.literal("Received registry id lists: ").withStyle(ChatFormatting.GRAY).append(Component.literal("" + summary.getRegistryEntryIdLists()).withStyle(ChatFormatting.WHITE)));
        SendSystemChatUtil.send(Component.literal("Received block state id lists: ").withStyle(ChatFormatting.GRAY).append(Component.literal("" + summary.getBlockStateRegistryEntryIdLists()).withStyle(ChatFormatting.WHITE)));
    }

    private static String protocolRange() {
        int min = SpoutProtocol.getServerMinProtocolVersion();
        int max = SpoutProtocol.getServerMaxProtocolVersion();
        if (min < 0 || max < 0) {
            return UNKNOWN;
        }
        return min == max ? "" + min : min + " - " + max;
    }

    private static String unknownIfNegative(int value) {
        return value < 0 ? UNKNOWN : Integer.toString(value);
    }

}
