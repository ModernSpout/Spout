package spout.api.util.minecraft.blockstate.visualduplicates;

import io.papermc.paper.registry.event.RegistryEvents;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.Nullable;
import spout.api.SpoutAPIServices;
import java.util.List;

/**
 * API for {@code spout.util.minecraft.blockstate.visualduplicates.VisualDuplicates}.
 *
 * <p>
 * Note that this class requires vanilla block states to have been registered,
 * so it is generally not allowed to call any method of this class
 * before the {@link RegistryEvents#BLOCK_TYPE} event.
 * </p>
 */
public interface VisualDuplicates {

    /**
     * @return The {@link VisualDuplicates} instance.
     */
    static VisualDuplicates get() {
        return SpoutAPIServices.getVisualDuplicates();
    }

    /**
     * @return The visual duplicates for the given {@link BlockData}.
     * For each visual duplicate in the same group,
     * the return value will be the same {@link VisualDuplicateGroup} instance.
     */
    @Nullable VisualDuplicateGroup getVisualDuplicates(BlockData blockState);

    /**
     * @return Whether the given {@link BlockData} has any visual duplicates.
     */
    boolean hasVisualDuplicates(BlockData blockState);

    /**
     * A group of visual duplicates.
     */
    interface VisualDuplicateGroup {

        /**
         * @return The {@linkplain BlockData block states} in this group.
         */
        List<? extends BlockData> getStates();

    }

}
