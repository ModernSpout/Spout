package spout.client.fabric;

import net.fabricmc.api.ClientModInitializer;
import spout.client.fabric.clientview.SpoutProtocol;

public class SpoutClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
        SpoutProtocol.initialize();
    }

}
