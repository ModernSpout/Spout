package spout.clientview.packetmapping.itemstack.apply;

import spout.api.clientview.model.ClientView;
import spout.clientview.packetmapping.WithClientViewMappingsApplicationContext;

/**
 * The context for applying mappings to an item stack.
 */
public final class ItemStackMappingsApplicationContext extends WithClientViewMappingsApplicationContext {

    private final boolean isItemStackInItemFrame;
    private final boolean isStonecutterRecipeResult;

    public ItemStackMappingsApplicationContext(ClientView clientView, boolean isItemStackInItemFrame, boolean isStonecutterRecipeResult) {
        super(clientView);
        this.isItemStackInItemFrame = isItemStackInItemFrame;
        this.isStonecutterRecipeResult = isStonecutterRecipeResult;
    }

    public ItemStackMappingsApplicationContext(ClientView clientView) {
        this(clientView, false, false);
    }

    /**
     * @return Whether the item stack on which this mapping is being applied
     * is an item stack in an item frame.
     */
    public boolean isItemStackInItemFrame() {
        return this.isItemStackInItemFrame;
    }

    /**
     * @return Whether the item stack on which this mapping is being applied
     * is the result of a stonecutter recipe.
     */
    public boolean isStonecutterRecipeResult() {
        return this.isStonecutterRecipeResult;
    }

}
