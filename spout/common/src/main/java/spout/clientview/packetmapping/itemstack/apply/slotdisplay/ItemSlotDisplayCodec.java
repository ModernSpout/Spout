package spout.clientview.packetmapping.itemstack.apply.slotdisplay;

import net.minecraft.core.HolderSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import spout.clientview.packetmapping.clientviewlookup.ThreadLocalClientViewLookup;
import spout.clientview.packetmapping.itemstack.apply.ItemInPacketMappers;

public final class ItemSlotDisplayCodec {

    private ItemSlotDisplayCodec() {
        throw new UnsupportedOperationException();
    }

    public static <O extends SlotDisplay> O mapItemDisplayToItemStackDisplay(SlotDisplay display) {
        SlotDisplay.Type<?> type = display.type();
        if (type == SlotDisplay.ItemSlotDisplay.TYPE) {
            int awarenessLevelId = ThreadLocalClientViewLookup.getThreadLocalClientViewOrFallback().getAwarenessLevel().getId();
            Item item = ((SlotDisplay.ItemSlotDisplay) display).item().value();
            if (ItemInPacketMappers.hasAnyMapping(awarenessLevelId, item)) {
                return (O) new SlotDisplay.ItemStackSlotDisplay(ItemStackTemplate.fromStack(new ItemStack(item)));
            }
        } else if (type == SlotDisplay.TagSlotDisplay.TYPE) {
            int awarenessLevelId = ThreadLocalClientViewLookup.getThreadLocalClientViewOrFallback().getAwarenessLevel().getId();
            HolderSet<Item> set = ((SlotDisplay.TagSlotDisplay) display).tag();
            if (set.stream().anyMatch(holder -> ItemInPacketMappers.hasAnyMapping(awarenessLevelId, holder.value()))) {
                return (O) new SlotDisplay.Composite(set.stream().map(holder -> (SlotDisplay) new SlotDisplay.ItemStackSlotDisplay(ItemStackTemplate.fromStack(new ItemStack(holder.value())))).toList());
            }
        }
        return (O) display;
    }

}
