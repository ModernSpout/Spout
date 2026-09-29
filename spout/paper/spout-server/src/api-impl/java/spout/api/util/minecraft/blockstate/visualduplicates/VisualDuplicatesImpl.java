package spout.api.util.minecraft.blockstate.visualduplicates;

import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jspecify.annotations.Nullable;
import java.util.List;

/**
 * The implementation of {@link VisualDuplicates}.
 */
public final class VisualDuplicatesImpl implements VisualDuplicates {

    public static VisualDuplicatesImpl get() {
        return (VisualDuplicatesImpl) VisualDuplicates.get();
    }

    @Override
    public @Nullable VisualDuplicateGroup getVisualDuplicates(BlockData blockState) {
        spout.util.minecraft.blockstate.visualduplicates.VisualDuplicateGroup handle = spout.util.minecraft.blockstate.visualduplicates.VisualDuplicates.getVisualDuplicates(((CraftBlockData) blockState).getState());
        return handle == null ? null : new VisualDuplicateGroupImpl(handle);
    }

    @Override
    public boolean hasVisualDuplicates(BlockData blockState) {
        return spout.util.minecraft.blockstate.visualduplicates.VisualDuplicates.hasVisualDuplicates(((CraftBlockData) blockState).getState());
    }

    /**
     * The implementation of {@link VisualDuplicateGroup}.
     */
    public record VisualDuplicateGroupImpl(spout.util.minecraft.blockstate.visualduplicates.VisualDuplicateGroup handle) implements VisualDuplicateGroup {

        @Override
        public List<? extends BlockData> getStates() {
            return this.handle.getStates().stream().map(BlockState::asBlockData).toList();
        }

    }

}
