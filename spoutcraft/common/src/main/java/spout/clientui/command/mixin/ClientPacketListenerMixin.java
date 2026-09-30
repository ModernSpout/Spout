package spout.clientui.command.mixin;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spout.clientui.command.SpoutcraftCommand;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {

    @Shadow
    private CommandDispatcher<ClientSuggestionProvider> commands;

    @Inject(method = "handleCommands", at = @At("TAIL"))
    private void registerSpoutcraftCommand(ClientboundCommandsPacket packet, CallbackInfo ci) {
        SpoutcraftCommand.register(this.commands);
    }

    @Inject(method = "sendCommand", at = @At("HEAD"), cancellable = true)
    private void runSpoutcraftCommand(String command, CallbackInfo ci) {
        if (SpoutcraftCommand.executeIfSpoutcraftCommand(command)) {
            ci.cancel();
        }
    }

}
