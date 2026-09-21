package spout.api.clientview.packetmapping.common.builder;

import spout.api.clientview.model.ClientView;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * A mapping builder to define a mapping that targets one or more particular {@link ClientView.AwarenessLevel}s.
 */
public interface AwarenessLevelsMappingRegistryEntryBuilder extends AwarenessLevelsMappingRegistryEntry {

    /**
     * Sets the {@link ClientView.AwarenessLevel} to which this mapping will be applied.
     *
     * <p>
     * This replaces any previous value set with {@link #setAwarenessLevels}.
     * </p>
     */
    default void setAwarenessLevel(ClientView.AwarenessLevel awarenessLevel) {
        this.setAwarenessLevels(List.of(awarenessLevel));
    }

    /**
     * @see #setAwarenessLevel(ClientView.AwarenessLevel)
     */
    default void setAwarenessLevels(ClientView.AwarenessLevel[] awarenessLevels) {
        this.setAwarenessLevels(Arrays.asList(awarenessLevels));
    }

    /**
     * @see #setAwarenessLevel(ClientView.AwarenessLevel)
     */
    void setAwarenessLevels(Collection<ClientView.AwarenessLevel> awarenessLevels);

    /**
     * This mapping will be applied to every {@link ClientView.AwarenessLevel}.
     */
    default void everyAwarenessLevel() {
        this.setAwarenessLevels(ClientView.AwarenessLevel.getAll());
    }

    /**
     * This mapping will be applied to every {@link ClientView.AwarenessLevel}
     * in {@link ClientView.AwarenessLevel#getThatDoNotAlwaysUnderstandsAllServerSideTranslatables()}.
     */
    default void everyAwarenessLevelThatDoesNotAlwaysUnderstandAllServerSideTranslatables() {
        this.setAwarenessLevels(ClientView.AwarenessLevel.getThatDoNotAlwaysUnderstandsAllServerSideTranslatables());
    }

    /**
     * This mapping will be applied to every {@link ClientView.AwarenessLevel}
     * in {@link ClientView.AwarenessLevel#getThatDoNotAlwaysUnderstandsAllServerSideBlocks()}.
     */
    default void everyAwarenessLevelThatDoesNotAlwaysUnderstandAllServerSideBlocks() {
        this.setAwarenessLevels(ClientView.AwarenessLevel.getThatDoNotAlwaysUnderstandsAllServerSideBlocks());
    }

    /**
     * This mapping will be applied to every {@link ClientView.AwarenessLevel}
     * in {@link ClientView.AwarenessLevel#getThatDoNotAlwaysUnderstandsAllServerSideItems()}.
     */
    default void everyAwarenessLevelThatDoesNotAlwaysUnderstandAllServerSideItems() {
        this.setAwarenessLevels(ClientView.AwarenessLevel.getThatDoNotAlwaysUnderstandsAllServerSideItems());
    }

    /**
     * Adds a {@link ClientView.AwarenessLevel} to which this mapping will be applied.
     */
    void addAwarenessLevel(ClientView.AwarenessLevel awarenessLevel);

    /**
     * @see #addAwarenessLevel(ClientView.AwarenessLevel)
     */
    default void addAwarenessLevels(ClientView.AwarenessLevel[] awarenessLevels) {
        for (ClientView.AwarenessLevel value : awarenessLevels) {
            this.addAwarenessLevel(value);
        }
    }

    /**
     * @see #addAwarenessLevel(ClientView.AwarenessLevel)
     */
    default void addAwarenessLevels(Collection<ClientView.AwarenessLevel> awarenessLevels) {
        for (ClientView.AwarenessLevel value : awarenessLevels) {
            this.addAwarenessLevel(value);
        }
    }

}
