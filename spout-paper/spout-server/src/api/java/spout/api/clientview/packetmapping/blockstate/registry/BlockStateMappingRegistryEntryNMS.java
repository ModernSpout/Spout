package spout.api.clientview.packetmapping.blockstate.registry;

import org.jetbrains.annotations.ApiStatus;
import spout.api.clientview.packetmapping.blockstate.handle.BlockStateMappingHandleNMS;
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
         * NMS extension for {@link #setToFunction}.
         */
        void setToFunctionNMS(Consumer<BlockStateMappingHandleNMS> function, boolean requiresCoordinates);

    }

}
