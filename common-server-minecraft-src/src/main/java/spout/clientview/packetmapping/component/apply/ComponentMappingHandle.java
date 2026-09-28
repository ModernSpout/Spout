package spout.clientview.packetmapping.component.apply;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import spout.util.mapping.handle.AbstractMappingHandle;
import spout.util.mapping.handle.SimpleWithContextMappingHandle;

/**
 * The implementation of {@link AbstractMappingHandle} for component mappings.
 */
public final class ComponentMappingHandle extends SimpleWithContextMappingHandle<Component, MutableComponent, ComponentMappingsApplicationContext> {

    public ComponentMappingHandle(Component data, ComponentMappingsApplicationContext context, boolean isDataMutable) {
        super(data, context, isDataMutable);
    }

    @Override
    protected MutableComponent cloneMutable(final Component data) {
        return data.copy();
    }

}
