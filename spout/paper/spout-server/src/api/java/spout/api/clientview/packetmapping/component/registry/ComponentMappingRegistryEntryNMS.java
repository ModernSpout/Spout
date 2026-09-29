package spout.api.clientview.packetmapping.component.registry;

import java.util.function.Consumer;
import org.jetbrains.annotations.ApiStatus;
import spout.api.clientview.packetmapping.component.handle.ComponentMappingHandleNMS;

/**
 * NMS extension for {@link ComponentMappingRegistryEntry}.
 *
 * <p>
 * Every instance of {@link ComponentMappingRegistryEntry}
 * is an instance of {@link ComponentMappingRegistryEntryNMS}.
 * </p>
 */
public interface ComponentMappingRegistryEntryNMS extends ComponentMappingRegistryEntry {

    // /**
    //  * NMS extension for {@link #getToFunction}.
    //  */
    // @Nullable Consumer<ComponentMappingHandleNMS> getToFunctionNMS();

    /**
     * NMS extension for {@link ComponentMappingRegistryEntry.Builder}.
     *
     * <p>
     * Every instance of {@link ComponentMappingRegistryEntry.Builder}
     * is an instance of {@link ComponentMappingRegistryEntryNMS.Builder}.
     * </p>
     */
    @ApiStatus.NonExtendable
    interface Builder extends ComponentMappingRegistryEntryNMS, ComponentMappingRegistryEntry.Builder {

        /**
         * NMS extension for {@link #setToFunction}.
         */
        void setToFunctionNMS(Consumer<ComponentMappingHandleNMS> function);

    }

}
