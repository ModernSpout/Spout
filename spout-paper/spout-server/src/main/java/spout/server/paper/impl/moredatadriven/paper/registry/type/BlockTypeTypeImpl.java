package spout.server.paper.impl.moredatadriven.paper.registry.type;

import io.papermc.paper.registry.HolderableBase;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import spout.gamecontent.datadriven.blocktype.SpoutBlockType;
import spout.gamecontent.datadriven.blocktype.BlockTypeType;
import spout.server.paper.api.moredatadriven.paper.registry.type.nms.BlockTypeTypeNMS;

/**
 * The implementation for {@link BlockTypeType}.
 */
public final class BlockTypeTypeImpl extends HolderableBase<SpoutBlockType> implements BlockTypeTypeNMS {

    public BlockTypeTypeImpl(Holder<SpoutBlockType> minecraftHolder) {
        super(minecraftHolder);
    }

    @Override
    public Identifier getIdentifier() {
        return this.holder.value().getIdentifier();
    }

    public static BlockTypeTypeImpl of(Holder<SpoutBlockType> minecraftHolder) {
        return new BlockTypeTypeImpl(minecraftHolder);
    }

}
