package spout.clientview.model.awarenesslevel;

import spout.clientview.model.ClientView;

/**
 * Represents the major categorization of the client's capability
 * to interpret data sent by the server.
 */
public class AwarenessLevel {

    private final boolean alwaysUnderstandsAllServerSideTranslatables;
    private final boolean alwaysUnderstandsAllServerSideBlocks;
    private final boolean alwaysUnderstandsAllServerSideItems;

    /**
     * A unique numeric ordinal that allows for some optimizations.
     */
    int id;

    AwarenessLevel(
        boolean alwaysUnderstandsAllServerSideTranslatables,
        boolean alwaysUnderstandsAllServerSideBlocks,
        boolean alwaysUnderstandsAllServerSideItems
    ) {
        this.alwaysUnderstandsAllServerSideTranslatables = alwaysUnderstandsAllServerSideTranslatables;
        this.alwaysUnderstandsAllServerSideBlocks = alwaysUnderstandsAllServerSideBlocks;
        this.alwaysUnderstandsAllServerSideItems = alwaysUnderstandsAllServerSideItems;
    }

    /**
     * @return True if every {@link ClientView} with this {@link AwarenessLevel}
     * will have {@link ClientView#understandsAllServerSideTranslatables} returning true.
     */
    public boolean alwaysUnderstandsAllServerSideTranslatables() {
        return this.alwaysUnderstandsAllServerSideTranslatables;
    }

    /**
     * @return True if every {@link ClientView} with this {@link AwarenessLevel}
     * will have {@link ClientView#understandsAllServerSideItems} returning true.
     */
    public boolean alwaysUnderstandsAllServerSideItems() {
        return this.alwaysUnderstandsAllServerSideItems;
    }

    /**
     * @return True if every {@link ClientView} with this {@link AwarenessLevel}
     * will have {@link ClientView#understandsAllServerSideBlocks} returning true.
     */
    public boolean alwaysUnderstandsAllServerSideBlocks() {
        return this.alwaysUnderstandsAllServerSideBlocks;
    }

    public int getId() {
        return this.id;
    }

}
