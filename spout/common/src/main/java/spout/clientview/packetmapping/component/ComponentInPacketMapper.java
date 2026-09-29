package spout.clientview.packetmapping.component;

import net.minecraft.network.chat.Component;
import spout.clientview.packetmapping.FallbackContextValueInPacketMapper;
import spout.clientview.packetmapping.component.apply.ComponentMappingsApplicationContext;
import java.util.ServiceLoader;

/**
 * Provides the functions to map components in packets.
 */
public abstract class ComponentInPacketMapper implements FallbackContextValueInPacketMapper<Component, Component, ComponentMappingsApplicationContext> {

    private static volatile ComponentInPacketMapper instance;

    public static ComponentInPacketMapper get() {
        if (instance == null) {
            instance = ServiceLoader.load(ComponentInPacketMapper.class).findFirst().get();
        }
        return instance;
    }

    public abstract boolean hasAnyMapping(int awarenessLevelId, int targetOrdinal);

    public abstract boolean hasAnyMapping(int awarenessLevelId, ComponentTarget target);

}
