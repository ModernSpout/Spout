package spout.clientview.packetmapping.itemstack.builtin.removenonvanilladebugstickstate;

import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.DebugStickState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.itemstack.apply.ItemStackMappingHandle;
import spout.util.mapping.handle.MappingStep;

/**
 * A {@link MappingStep} that removes non-vanilla block states from the debug stick state.
 */
public final class RemoveNonVanillaDebugStickStateItemStackMappingStep implements MappingStep<ItemStackMappingHandle> {

    @Override
    public void apply(final ItemStackMappingHandle handle) {
        if (handle.getContext().getClientView().understandsAllServerSideBlocks()) return;
        @Nullable DebugStickState state = handle.getImmutable().get(DataComponents.DEBUG_STICK_STATE);
        if (state == null) return;
        Map<Holder<Block>, Property<?>> properties = state.properties();
        if (properties.keySet().stream().allMatch(holder -> holder.value().isVanilla())) return;
        Map<Holder<Block>, Property<?>> filteredProperties = properties.entrySet().stream().filter(entry -> entry.getKey().value().isVanilla()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        handle.getMutable().set(DataComponents.DEBUG_STICK_STATE, new DebugStickState(filteredProperties));
    }

}
