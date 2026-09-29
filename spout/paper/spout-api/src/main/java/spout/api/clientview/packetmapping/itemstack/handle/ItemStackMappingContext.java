package spout.api.clientview.packetmapping.itemstack.handle;

import spout.api.clientview.packetmapping.common.context.WithClientViewMappingContext;

/**
 * A context to include in {@link ItemStackMappingHandle}s.
 */
public interface ItemStackMappingContext extends WithClientViewMappingContext {

    /**
     * @return Whether the item stack on which this mapping is being applied
     * is an item stack in an item frame.
     */
    boolean isItemStackInItemFrame();

    /**
     * @return Whether the item stack on which this mapping is being applied
     * is the result of a stonecutter recipe.
     */
    boolean isStonecutterRecipeResult();

}
