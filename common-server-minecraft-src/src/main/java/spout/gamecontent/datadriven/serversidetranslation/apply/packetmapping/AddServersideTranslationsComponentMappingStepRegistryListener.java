package spout.gamecontent.datadriven.serversidetranslation.apply.packetmapping;

import it.unimi.dsi.fastutil.Pair;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import spout.branding.SpoutNamespace;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.packetmapping.component.ComponentMapping;
import spout.clientview.packetmapping.component.ComponentTarget;
import spout.clientview.packetmapping.component.registry.ComponentMappingRegistryKey;
import spout.util.minecraft.registry.SpoutRegistryHookEvents;

public final class AddServersideTranslationsComponentMappingStepRegistryListener implements SpoutRegistryHookEvents.Listener<ComponentMapping> {

    @Override
    public Iterable<Pair<ResourceKey<Registry<ComponentMapping>>, SpoutRegistryHookEvents.EventType>> getRegistryHookEventsToListenFor() {
        return List.of(Pair.of(ComponentMappingRegistryKey.COMPONENT_MAPPING, SpoutRegistryHookEvents.EventType.PRE_POPULATE_STATIC_REGISTRY));
    }

    @Override
    public void onRegistryHookEvent(final SpoutRegistryHookEvents.EventType type, final WritableRegistry<ComponentMapping> registry) {
        Registry.register(
            registry,
            Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, "serverside_translations"),
            new ComponentMapping(
                Arrays.asList(AwarenessLevels.getThatDoNotAlwaysUnderstandsAllServerSideTranslatables()),
                List.of(ComponentTarget.TRANSLATABLE),
                new ServersideTranslationsComponentMappingStep()
            )
        );
    }

}
