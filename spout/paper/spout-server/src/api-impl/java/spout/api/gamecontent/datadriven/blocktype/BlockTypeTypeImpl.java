package spout.api.gamecontent.datadriven.blocktype;

import io.papermc.paper.registry.HolderableBase;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import spout.gamecontent.datadriven.blocktype.SpoutBlockType;

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
