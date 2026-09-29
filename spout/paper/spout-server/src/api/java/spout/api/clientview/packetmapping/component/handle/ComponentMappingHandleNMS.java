package spout.api.clientview.packetmapping.component.handle;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import spout.api.util.mapping.handle.MutableMappingHandle;
import spout.api.util.mapping.handle.WithContextMappingHandle;
import spout.api.util.mapping.handle.WithOriginalMappingHandle;

/**
 * NMS analogue for {@link ComponentMappingHandle}.
 *
 * <p>
 * Note: instances of {@link ComponentMappingHandle}
 * are not instances of {@link ComponentMappingHandleNMS}.
 * </p>
 */
public interface ComponentMappingHandleNMS extends WithContextMappingHandle<Component, ComponentMappingContext>, WithOriginalMappingHandle<Component>, MutableMappingHandle<Component, MutableComponent> {
}
