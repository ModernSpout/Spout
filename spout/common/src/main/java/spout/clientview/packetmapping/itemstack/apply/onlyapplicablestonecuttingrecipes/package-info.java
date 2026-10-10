/**
 * <h1>Client view / Packet mapping / Item stacks / Apply / Send only applicable stonecutter recipes</h1>
 *
 * <p>
 * Makes sure that, to clients that do not understand all items,
 * only stonecutting recipes are sent while they are applicable inside the stonecutting interface.
 * This is needed because the client will show any stonecutting recipes that are applicable
 * to the item of the item stack in the input slot.
 * </p>
 */
@NullMarked
package spout.clientview.packetmapping.itemstack.apply.onlyapplicablestonecuttingrecipes;

import org.jspecify.annotations.NullMarked;
