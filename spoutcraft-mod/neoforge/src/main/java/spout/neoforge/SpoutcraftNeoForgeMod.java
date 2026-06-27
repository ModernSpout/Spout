package spout.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import spout.clientview.clientmod.protocol.ClientModCustomContentPacketPayload;
import spout.clientview.clientmod.protocol.ClientModCustomContentReceiving;

@Mod("spoutcraft_mod")
public final class SpoutcraftNeoForgeMod {

    public SpoutcraftNeoForgeMod(IEventBus modEventBus) {
        modEventBus.addListener(this::registerPayloadHandlers);
    }

    private void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        event.registrar("1").optional().executesOn(HandlerThread.NETWORK).commonToClient(
            ClientModCustomContentPacketPayload.TYPE,
            ClientModCustomContentPacketPayload.STREAM_CODEC,
            (payload, context) -> ClientModCustomContentReceiving.handlePacket(payload)
        );
    }

}
