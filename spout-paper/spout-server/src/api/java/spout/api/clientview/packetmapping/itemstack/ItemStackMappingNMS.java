package spout.api.clientview.packetmapping.itemstack;

import java.util.List;
import net.minecraft.world.item.Item;

/**
 * NMS extension for {@link ItemStackMapping}.
 *
 * <p>
 * Every instance of {@link ItemStackMapping}
 * is an instance of {@link ItemStackMappingNMS}.
 * </p>
 */
public interface ItemStackMappingNMS extends ItemStackMapping {

    /**
     * @see #getFrom()
     */
    List<Item> getFromNMS();

    // /**
    //  * @see #getToFunction()
    //  */
    // Consumer<ItemStackMappingHandleNMS> getToFunctionNMS();

}
