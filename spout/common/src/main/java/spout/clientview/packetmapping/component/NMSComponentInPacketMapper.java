package spout.clientview.packetmapping.component;

import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.clientviewlookup.ThreadLocalClientViewLookup;
import spout.clientview.packetmapping.component.apply.ComponentMappingHandle;
import spout.clientview.packetmapping.component.apply.ComponentMappingsApplicationContext;
import spout.clientview.packetmapping.component.apply.OptimizedComponentMappings;
import spout.util.mapping.handle.MappingStep;
import java.util.function.Consumer;

public class NMSComponentInPacketMapper extends ComponentInPacketMapper {

    @Override
    public ComponentMappingsApplicationContext getFallbackContext() {
        return new ComponentMappingsApplicationContext(ThreadLocalClientViewLookup.getThreadLocalClientViewOrFallback());
    }

    @Override
    public Component applyWithNonNullContext(Component value, ComponentMappingsApplicationContext context) {
        int awarenessLevelId = context.getClientView().getAwarenessLevel().getId();
        int valueTargetOrdinal = ComponentTargetUtil.get().getMostSpecificTarget(value).ordinal();
        // If there is a mapping chain, apply it
        Consumer<ComponentMappingHandle> @Nullable [] chain = OptimizedComponentMappings.getChain(awarenessLevelId, valueTargetOrdinal);
        if (chain != null) {
            return MappingStep.applyChain(new ComponentMappingHandle(value, context, false), chain);
        }
        // No mappings need to be applied
        return value;
    }

    @Override
    public boolean hasAnyMapping(int awarenessLevelId, int targetOrdinal) {
        return OptimizedComponentMappings.getChain(awarenessLevelId, targetOrdinal) != null;
    }

    @Override
    public boolean hasAnyMapping(int awarenessLevelId, ComponentTarget target) {
        return this.hasAnyMapping(awarenessLevelId, target.ordinal());
    }

}
