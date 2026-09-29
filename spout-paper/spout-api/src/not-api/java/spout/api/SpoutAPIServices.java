package spout.api;

import spout.api.gamecontent.datadriven.material.enuminjection.MaterialEnumNames;
import spout.api.clientview.packetmapping.blockstate.resourcepackclaims.ResourcePackBlockStateClaims;
import spout.api.util.minecraft.blockstate.visualduplicates.VisualDuplicates;
import spout.api.gamecontent.datadriven.serversidetranslation.apply.ServersideTranslations;
import spout.api.clientview.packetmapping.itemstack.builtin.changeonlyitem.ChangeOnlyItemUtility;
import spout.server.paper.api.resourcepack.construct.ResourcePackConstruction;
import spout.api.clientview.resourcepack.plugindiscovery.PluginResourcePackDiscovery;
import spout.api.gamecontent.datadriven.material.enuminjection.match.MaterialByKeyLookup;
import java.util.ServiceLoader;

/**
 * A class that provides the instances for all Spout service classes
 * (which will typically have a static {@code get()} method that defers to this class).
 */
public final class SpoutAPIServices {

    private SpoutAPIServices() {
        throw new UnsupportedOperationException();
    }

    private static <T> T getOrInitialize(T[] reference, Class<T> clazz) {
        T value = reference[0];
        if (value != null) {
            return value;
        }
        synchronized (reference) {
            value = reference[0];
            if (value != null) {
                return value;
            }
            value = ServiceLoader.load(clazz, clazz.getClassLoader()).findFirst().get();
            reference[0] = value;
            return value;
        }
    }

    private static final ChangeOnlyItemUtility[] changeOnlyItemUtility = new ChangeOnlyItemUtility[1];
    private static final MaterialByKeyLookup[] materialByKeyLookup = new MaterialByKeyLookup[1];
    private static final MaterialEnumNames[] materialEnumNames = new MaterialEnumNames[1];
    private static final PluginResourcePackDiscovery[] pluginResourcePackDiscovery = new PluginResourcePackDiscovery[1];
    private static final ResourcePackBlockStateClaims[] resourcePackBlockStateClaims = new ResourcePackBlockStateClaims[1];
    private static final ResourcePackConstruction[] resourcePackConstruction = new ResourcePackConstruction[1];
    private static final ServersideTranslations[] serversideTranslations = new ServersideTranslations[1];
    private static final VisualDuplicates[] visualDuplicates = new VisualDuplicates[1];

    public static ChangeOnlyItemUtility getChangeOnlyItemUtility() {
        return getOrInitialize(changeOnlyItemUtility, ChangeOnlyItemUtility.class);
    }

    public static MaterialByKeyLookup getMaterialByKeyLookup() {
        return getOrInitialize(materialByKeyLookup, MaterialByKeyLookup.class);
    }

    public static MaterialEnumNames getMaterialEnumNames() {
        return getOrInitialize(materialEnumNames, MaterialEnumNames.class);
    }

    public static PluginResourcePackDiscovery getPluginResourcePackDiscovery() {
        return getOrInitialize(pluginResourcePackDiscovery, PluginResourcePackDiscovery.class);
    }

    public static ResourcePackBlockStateClaims getResourcePackBlockStateClaims() {
        return getOrInitialize(resourcePackBlockStateClaims, ResourcePackBlockStateClaims.class);
    }

    public static ResourcePackConstruction getResourcePackConstruction() {
        return getOrInitialize(resourcePackConstruction, ResourcePackConstruction.class);
    }

    public static ServersideTranslations getServersideTranslations() {
        return getOrInitialize(serversideTranslations, ServersideTranslations.class);
    }

    public static VisualDuplicates getVisualDuplicates() {
        return getOrInitialize(visualDuplicates, VisualDuplicates.class);
    }

}
