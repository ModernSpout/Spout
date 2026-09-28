package spout.api.clientview.packetmapping.component.handle;

import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import spout.api.util.mapping.handle.CrossMappedWithContextMappingHandleImpl;
import spout.clientview.packetmapping.component.apply.ComponentMappingsApplicationContext;

public final class ComponentMappingHandleImpl extends CrossMappedWithContextMappingHandleImpl<Component, ComponentMappingContext, net.minecraft.network.chat.Component, ComponentMappingsApplicationContext, spout.clientview.packetmapping.component.apply.ComponentMappingHandle> implements ComponentMappingHandle {

    public ComponentMappingHandleImpl(spout.clientview.packetmapping.component.apply.ComponentMappingHandle handle) {
        super(handle);
    }

    @Override
    protected net.minecraft.network.chat.Component mapAPIToInternal(final Component data) {
        return PaperAdventure.asVanilla(data);
    }

    @Override
    protected Component mapInternalToAPI(final net.minecraft.network.chat.Component data) {
        return PaperAdventure.asAdventure(data);
    }

    @Override
    protected ComponentMappingContext mapContextInternalToAPI(final ComponentMappingsApplicationContext context) {
        return new ComponentMappingContextImpl(context);
    }

}
