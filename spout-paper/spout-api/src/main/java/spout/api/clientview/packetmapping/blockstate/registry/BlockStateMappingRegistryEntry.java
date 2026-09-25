package spout.api.clientview.packetmapping.blockstate.registry;

import io.papermc.paper.registry.RegistryBuilder;
import org.bukkit.block.BlockType;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import spout.api.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.api.clientview.packetmapping.blockstate.handle.BlockStateMappingContext;
import spout.api.clientview.packetmapping.blockstate.handle.BlockStateMappingHandle;
import spout.api.clientview.packetmapping.common.builder.AwarenessLevelsMappingRegistryEntry;
import spout.api.clientview.packetmapping.common.builder.AwarenessLevelsMappingRegistryEntryBuilder;
import spout.api.util.mapping.builder.FromRegistryEntry;
import spout.api.util.mapping.builder.FromRegistryEntryBuilder;
import spout.api.util.mapping.builder.FunctionRegistryEntryBuilder;
import spout.api.util.mapping.builder.ToRegistryEntry;
import spout.api.util.mapping.builder.ToRegistryEntryBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * A data-centric version-specific registry entry for the {@link BlockStateMapping} type.
 */
public interface BlockStateMappingRegistryEntry extends AwarenessLevelsMappingRegistryEntry, FromRegistryEntry<BlockData>, ToRegistryEntry<BlockData> {

    /**
     * A mutable builder for a {@link BlockStateMappingRegistryEntry}.
     */
    @ApiStatus.NonExtendable
    interface Builder extends BlockStateMappingRegistryEntry, RegistryBuilder<BlockStateMapping>, AwarenessLevelsMappingRegistryEntryBuilder, FromRegistryEntryBuilder<BlockData>, ToRegistryEntryBuilder<BlockData> {

        /**
         * Sets the target {@link BlockType} to which this mapping will be applied.
         * It will be applied to every block state of that type.
         *
         * <p>
         * This replaces any previous value set with {@link #setFrom} or {@link #setFromEveryStateOf}.
         * </p>
         */
        default void setFromEveryStateOf(BlockType from) {
            this.setFromEveryStateOf(List.of(from));
        }

        /**
         * @see #setFromEveryStateOf(BlockType)
         */
        default void setFromEveryStateOf(BlockType[] from) {
            this.setFromEveryStateOf(Arrays.asList(from));
        }

        /**
         * @see #setFromEveryStateOf(BlockType)
         */
        default void setFromEveryStateOf(Collection<BlockType> from) {
            this.setFrom(from.stream().flatMap(value -> value.createBlockDataStates().stream().map(state -> (BlockData) state)).toList());
        }

        /**
         * Adds a {@link BlockType} to which this mapping will be applied.
         *
         * @see #setFromEveryStateOf(BlockType)
         */
        default void addFromEveryStateOf(BlockType from) {
            for (BlockData value : from.createBlockDataStates()) {
                this.addFrom(value);
            }
        }

        /**
         * @see #addFromEveryStateOf(BlockType)
         */
        default void addFromEveryStateOf(BlockType[] from) {
            for (BlockType value : from) {
                this.addFromEveryStateOf(value);
            }
        }

        /**
         * @see #addFromEveryStateOf(BlockType)
         */
        default void addFromEveryStateOf(Collection<BlockType> from) {
            for (BlockType value : from) {
                this.addFromEveryStateOf(value);
            }
        }

        /**
         * Calls {@link #setTo} with the {@linkplain BlockType#createBlockData() default block state}
         * of the given {@link BlockType}.
         */
        default void setToDefaultStateOf(BlockType to) {
            this.setTo(to.createBlockData());
        }

        /**
         * @param requiresCoordinates Whether this mapping requires the coordinates
         *                            ({@link BlockStateMappingContext#getPhysicalBlockX()} and so on).
         * @see FunctionRegistryEntryBuilder#setToFunction
         */
        void setToFunction(Consumer<BlockStateMappingHandle> function, boolean requiresCoordinates);

    }

}
