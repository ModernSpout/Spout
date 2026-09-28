package spout.api.clientview.packetmapping.component;

import io.papermc.paper.registry.HolderableBase;
import java.util.List;
import net.minecraft.core.Holder;
import spout.api.clientview.model.ClientView;
import spout.api.clientview.model.CraftAwarenessLevel;

/**
 * The implementation for {@link ComponentMapping} and {@link ComponentMappingNMS}.
 */
public final class ComponentMappingImpl extends HolderableBase<spout.clientview.packetmapping.component.ComponentMapping> implements ComponentMappingNMS {

    public ComponentMappingImpl(Holder<spout.clientview.packetmapping.component.ComponentMapping> holder) {
        super(holder);
    }

    @Override
    public List<? extends ClientView.AwarenessLevel> getAwarenessLevels() {
        return this.getHolder().value().awarenessLevels().stream().map(CraftAwarenessLevel::toBukkit).toList();
    }

    @Override
    public List<? extends ComponentTarget> getFrom() {
        return this.getHolder().value().targets().stream().map(CraftComponentTarget::toBukkit).toList();
    }

}
