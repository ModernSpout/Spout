package spout.clientview.clientmod.protocol.mixin;

import java.time.Duration;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.client.multiplayer.LevelLoadTracker;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.TransferState;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ServerboundCustomQueryAnswerPacket;
import net.minecraft.network.protocol.login.custom.CustomQueryPayload;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spout.clientview.clientmod.protocol.SpoutProtocol;

@Mixin(ClientHandshakePacketListenerImpl.class)
public abstract class ClientHandshakePacketListenerImplMixin {

    @Shadow
    @Final
    private Connection connection;

    @Shadow
    @Final
    private Consumer<Component> updateStatus;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void markLoginStart(
        Connection connection,
        Minecraft minecraft,
        ServerData serverData,
        Screen parent,
        boolean newWorld,
        Duration worldLoadDuration,
        Consumer<Component> updateStatus,
        LevelLoadTracker levelLoadTracker,
        TransferState transferState,
        CallbackInfo ci
    ) {
        SpoutProtocol.onLoginStart();
    }

    @Inject(method = "handleCustomQuery", at = @At("HEAD"), cancellable = true)
    private void handleSpoutCustomQuery(ClientboundCustomQueryPacket packet, CallbackInfo ci) {
        CustomQueryPayload payload = packet.payload();
        if (!SpoutProtocol.CLIENT_MOD_DETECTION_PACKET_ID.equals(payload.id())) {
            return;
        }
        this.updateStatus.accept(Component.translatable("connect.negotiating"));
        this.connection.send(
            new ServerboundCustomQueryAnswerPacket(
                packet.transactionId(),
                SpoutProtocol.createClientModDetectionAnswer(payload)
            )
        );
        ci.cancel();
    }

}
