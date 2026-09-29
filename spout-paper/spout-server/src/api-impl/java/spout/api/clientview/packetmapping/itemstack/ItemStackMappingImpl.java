package spout.api.clientview.packetmapping.itemstack;

import io.papermc.paper.registry.HolderableBase;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import org.bukkit.craftbukkit.inventory.CraftItemType;
import org.bukkit.inventory.ItemType;
import spout.api.clientview.model.awarenesslevel.AwarenessLevel;
import spout.api.clientview.model.awarenesslevel.CraftAwarenessLevel;

/**
 * The implementation for {@link ItemStackMapping} and {@link ItemStackMappingNMS}.
 */
public final class ItemStackMappingImpl extends HolderableBase<spout.clientview.packetmapping.itemstack.ItemStackMapping> implements ItemStackMappingNMS {

    public ItemStackMappingImpl(Holder<spout.clientview.packetmapping.itemstack.ItemStackMapping> holder) {
        super(holder);
    }

    @Override
    public List<? extends AwarenessLevel> getAwarenessLevels() {
        return this.getHolder().value().awarenessLevels().stream().map(CraftAwarenessLevel::toBukkit).toList();
    }

    @Override
    public List<? extends ItemType> getFrom() {
        return this.getHolder().value().targets().stream().map(CraftItemType::minecraftToBukkitNew).toList();
    }

    @Override
    public List<Item> getFromNMS() {
        return Collections.unmodifiableList(this.getHolder().value().targets());
    }

}
