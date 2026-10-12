package spout.clientview.packetmapping.component;

import io.papermc.paper.adventure.AdventureComponent;
import net.minecraft.network.chat.Component;
import spout.clientview.packetmapping.component.apply.ComponentMappingsApplicationContext;

public final class PaperComponentInPacketMapper extends NMSComponentInPacketMapper {

    public static ThreadLocal<Boolean> isAdventureSerializing = ThreadLocal.withInitial(() -> false);

    @Override
    public Component applyWithNonNullContext(final Component value, final ComponentMappingsApplicationContext context) {
        if (isAdventureSerializing.get()) return value;
        // Convert the component to a vanilla component and apply
        return super.applyWithNonNullContext(value instanceof AdventureComponent adventureComponent ? adventureComponent.deepConverted() : value, context);
    }

}
