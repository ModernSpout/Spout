package spout.common.moredatadriven.clientmodprotocol;

import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.Pair;
import org.jspecify.annotations.Nullable;
import spout.client.fabric.clientview.ClientModState;
import spout.client.fabric.clientview.SpoutProtocol;
import spout.client.fabric.moredatadriven.TemporaryRegistryModifiers;
import spout.client.fabric.moredatadriven.minecraft.type.WithItemProperties;
import spout.common.moredatadriven.minecraft.item.SpoutDataDrivenItem;
import spout.common.protocol.ClientModCustomContent;
import spout.common.protocol.ClientModCustomContentPacketPayload;
import spout.common.util.minecraft.resources.KeyedValue;

/**
 * Collects all custom content received.
 */
public final class ClientModCustomContentReceiving {

    private ClientModCustomContentReceiving() {
        throw new UnsupportedOperationException();
    }

    /**
     * The current received content, or null if not currently receiving.
     */
    private static volatile @Nullable ClientModCustomContent receivedContent;

    public static void handlePacket(ClientModCustomContentPacketPayload payload) {
        while (SpoutProtocol.getState() != ClientModState.RECEIVED_CUSTOM_CONTENT && !SpoutProtocol.tryChangeState(ClientModState.CLIENT_MOD_DETECTED, ClientModState.RECEIVED_CUSTOM_CONTENT)) {
            Thread.onSpinWait();
        }
        if (receivedContent == null) {
            receivedContent = ClientModCustomContent.createEmpty();
        }
        for (ClientModCustomContentPacketPayload.Element element : payload.elements) {
            switch (element.type) {
                case BLOCK -> receivedContent.getBlocks().add(KeyedValue.of(element.getIdentifier(), element.getContentAsJsonElement()));
                case ITEM -> receivedContent.getItems().add(KeyedValue.of(element.getIdentifier(), SpoutDataDrivenItem.CODEC.decoder().decode(JsonOps.INSTANCE, element.getContentAsJsonElement()).getOrThrow().getFirst()));
                case END -> {
                    // Add the received content
                    TemporaryRegistryModifiers.prepareToAddCustomContent();
                    TemporaryRegistryModifiers.addCustomContent(
                        () -> receivedContent.getParsedBlocks().stream().map(block -> Pair.of(block.properties().id, block)).toList(),
                        () -> receivedContent.getParsedItems().stream().map(item -> Pair.of((((WithItemProperties) item).getItemProperties()).id, item)).toList()
                    );
                    SpoutProtocol.changeState(ClientModState.RECEIVED_CUSTOM_CONTENT, ClientModState.ADDED_CUSTOM_CONTENT);
                    receivedContent = null;
                }
            }
        }
    }

}
