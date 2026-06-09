package spout.server.paper.impl.moredatadriven.paper.registry.type;

import io.papermc.paper.registry.HolderableBase;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import spout.gamecontent.datadriven.itemtype.SpoutItemType;
import spout.gamecontent.datadriven.itemtype.ItemTypeType;
import spout.server.paper.api.moredatadriven.paper.registry.type.nms.ItemTypeTypeNMS;

/**
 * The implementation for {@link ItemTypeType}.
 */
public final class ItemTypeTypeImpl extends HolderableBase<SpoutItemType> implements ItemTypeTypeNMS {

    public ItemTypeTypeImpl(Holder<SpoutItemType> minecraftHolder) {
        super(minecraftHolder);
    }

    @Override
    public Identifier getIdentifier() {
        return this.holder.value().getIdentifier();
    }

    public static ItemTypeTypeImpl of(Holder<SpoutItemType> minecraftHolder) {
        return new ItemTypeTypeImpl(minecraftHolder);
    }

}
