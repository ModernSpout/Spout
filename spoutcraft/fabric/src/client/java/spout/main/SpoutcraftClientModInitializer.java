package spout.main;

import net.fabricmc.api.ClientModInitializer;
import spout.clientview.clientmod.protocol.SpoutProtocol;

public class SpoutcraftClientModInitializer implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
        SpoutProtocol.initialize();
    }

}
