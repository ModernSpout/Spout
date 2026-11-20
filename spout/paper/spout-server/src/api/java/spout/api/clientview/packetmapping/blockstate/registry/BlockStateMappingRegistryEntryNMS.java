package spout.api.clientview.packetmapping.blockstate.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.block.BlockType;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import spout.api.clientview.packetmapping.blockstate.handle.BlockStateMappingHandleNMS;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * NMS extension for {@link BlockStateMappingRegistryEntry}.
 *
 * <p>
 * Every instance of {@link BlockStateMappingRegistryEntry}
 * is an instance of {@link BlockStateMappingRegistryEntryNMS}.
 * </p>
 */
public interface BlockStateMappingRegistryEntryNMS extends BlockStateMappingRegistryEntry {

    // /**
    //  * NMS extension for {@link #getToFunction}.
    //  */
    // @Nullable Consumer<BlockStateMappingHandleNMS> getToFunctionNMS();

    /**
     * NMS extension for {@link BlockStateMappingRegistryEntry.Builder}.
     *
     * <p>
     * Every instance of {@link BlockStateMappingRegistryEntry.Builder}
     * is an instance of {@link BlockStateMappingRegistryEntryNMS.Builder}.
     * </p>
     */
    @ApiStatus.NonExtendable
    interface Builder extends BlockStateMappingRegistryEntryNMS, BlockStateMappingRegistryEntry.Builder {

        /**
         * NMS extension for {@link #setFrom(Object)}.
         */
        default void setFromNMS(BlockState from) {
            this.setFromNMS(List.of(from));
        }

        /**
         * NMS extension for {@link #setFrom(Object[])}.
         */
        default void setFromNMS(BlockState[] from) {
            this.setFromNMS(Arrays.asList(from));
        }

        /**
         * NMS extension for {@link #setFrom(Collection)}.
         */
        void setFromNMS(Collection<? extends BlockState> from);

        /**
         * NMS extension for {@link #addFrom(Object)}.
         */
        void addFromNMS(BlockState from);

        /**
         * NMS extension for {@link #addFrom(Object[])}.
         */
        default void addFromNMS(BlockState[] from) {
            for (BlockState value : from) {
                this.addFromNMS(value);
            }
        }

        /**
         * NMS extension for {@link #addFrom(Collection)}.
         */
        default void addFromNMS(Collection<BlockState> from) {
            for (BlockState value : from) {
                this.addFromNMS(value);
            }
        }

        /**
         * NMS extension for {@link #setTo(Object)}.
         */
        void setToNMS(BlockState to);

        /**
         * NMS extension for {@link #setFromEveryStateOf(BlockType)}.
         */
        default void setFromEveryStateOfNMS(Block from) {
            this.setFromEveryStateOfNMS(List.of(from));
        }

        /**
         * NMS extension for {@link #setFromEveryStateOf(BlockType[])}.
         */
        default void setFromEveryStateOfNMS(Block[] from) {
            this.setFromEveryStateOfNMS(Arrays.asList(from));
        }

        /**
         * NMS extension for {@link #setFromEveryStateOf(Collection)}.
         */
        default void setFromEveryStateOfNMS(Collection<Block> from) {
            this.setFrom(from.stream().flatMap(value -> value.getStateDefinition().getPossibleStates().stream().map(state -> (BlockData) state)).toList());
        }

        /**
         * NMS extension for {@link #addFromEveryStateOf(BlockType)}.
         */
        default void addFromEveryStateOfNMS(Block from) {
            for (BlockState value : from.getStateDefinition().getPossibleStates()) {
                this.addFromNMS(value);
            }
        }

        /**
         * NMS extension for {@link #addFromEveryStateOf(BlockType[])}.
         */
        default void addFromEveryStateOfNMS(Block[] from) {
            for (Block value : from) {
                this.addFromEveryStateOfNMS(value);
            }
        }

        /**
         * NMS extension for {@link #addFromEveryStateOf(Collection)}.
         */
        default void addFromEveryStateOfNMS(Collection<Block> from) {
            for (Block value : from) {
                this.addFromEveryStateOfNMS(value);
            }
        }

        /**
         * NMS extension for {@link #setToDefaultStateOf(BlockType)}.
         */
        default void setToDefaultStateOfNMS(Block to) {
            this.setToNMS(to.defaultBlockState());
        }

        /**
         * NMS extension for {@link #setToFunction}.
         */
        void setToFunctionNMS(Consumer<BlockStateMappingHandleNMS> function, boolean requiresCoordinates);

    }

}
