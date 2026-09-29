package spout.api.clientview.packetmapping.common.builder;

import spout.api.SpoutBuiltInRegistry;
import spout.api.clientview.model.awarenesslevel.AwarenessLevel;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * A mapping builder to define a mapping that targets one or more particular {@link AwarenessLevel}s.
 */
public interface AwarenessLevelsMappingRegistryEntryBuilder extends AwarenessLevelsMappingRegistryEntry {

    /**
     * Sets the {@link AwarenessLevel} to which this mapping will be applied.
     *
     * <p>
     * This replaces any previous value set with {@link #setAwarenessLevels}.
     * </p>
     */
    default void setAwarenessLevel(AwarenessLevel awarenessLevel) {
        this.setAwarenessLevels(List.of(awarenessLevel));
    }

    /**
     * @see #setAwarenessLevel(AwarenessLevel)
     */
    default void setAwarenessLevels(AwarenessLevel[] awarenessLevels) {
        this.setAwarenessLevels(Arrays.asList(awarenessLevels));
    }

    /**
     * @see #setAwarenessLevel(AwarenessLevel)
     */
    void setAwarenessLevels(Collection<AwarenessLevel> awarenessLevels);

    /**
     * This mapping will be applied to every {@link AwarenessLevel}.
     */
    default void everyAwarenessLevel() {
        this.setAwarenessLevels(SpoutBuiltInRegistry.AWARENESS_LEVEL.stream().toList());
    }

    // /**
    //  * This mapping will be applied to every {@link AwarenessLevel}
    //  * in {@link AwarenessLevel#getThatDoNotAlwaysUnderstandsAllServerSideTranslatables()}.
    //  */
    // default void everyAwarenessLevelThatDoesNotAlwaysUnderstandAllServerSideTranslatables() {
    //     this.setAwarenessLevels(AwarenessLevel.getThatDoNotAlwaysUnderstandsAllServerSideTranslatables());
    // }
    //
    // /**
    //  * This mapping will be applied to every {@link AwarenessLevel}
    //  * in {@link AwarenessLevel#getThatDoNotAlwaysUnderstandsAllServerSideBlocks()}.
    //  */
    // default void everyAwarenessLevelThatDoesNotAlwaysUnderstandAllServerSideBlocks() {
    //     this.setAwarenessLevels(AwarenessLevel.getThatDoNotAlwaysUnderstandsAllServerSideBlocks());
    // }
    //
    // /**
    //  * This mapping will be applied to every {@link AwarenessLevel}
    //  * in {@link AwarenessLevel#getThatDoNotAlwaysUnderstandsAllServerSideItems()}.
    //  */
    // default void everyAwarenessLevelThatDoesNotAlwaysUnderstandAllServerSideItems() {
    //     this.setAwarenessLevels(AwarenessLevel.getThatDoNotAlwaysUnderstandsAllServerSideItems());
    // }

    /**
     * Adds a {@link AwarenessLevel} to which this mapping will be applied.
     */
    void addAwarenessLevel(AwarenessLevel awarenessLevel);

    /**
     * @see #addAwarenessLevel(AwarenessLevel)
     */
    default void addAwarenessLevels(AwarenessLevel[] awarenessLevels) {
        for (AwarenessLevel value : awarenessLevels) {
            this.addAwarenessLevel(value);
        }
    }

    /**
     * @see #addAwarenessLevel(AwarenessLevel)
     */
    default void addAwarenessLevels(Collection<AwarenessLevel> awarenessLevels) {
        for (AwarenessLevel value : awarenessLevels) {
            this.addAwarenessLevel(value);
        }
    }

}
