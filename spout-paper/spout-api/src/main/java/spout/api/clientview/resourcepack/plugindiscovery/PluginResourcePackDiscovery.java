package spout.api.clientview.resourcepack.plugindiscovery;

import spout.api.SpoutAPIServices;
import spout.util.composable.Composable;

/**
 * A service to discover Spout plugin resource pack content.
 */
public interface PluginResourcePackDiscovery extends Composable<PluginResourcePackDiscoverEvent> {

    /**
     * @return The {@link PluginResourcePackDiscovery} instance.
     */
    static PluginResourcePackDiscovery get() {
        return SpoutAPIServices.getPluginResourcePackDiscovery();
    }

    String DEFAULT_PATH = "resource_pack";

}
