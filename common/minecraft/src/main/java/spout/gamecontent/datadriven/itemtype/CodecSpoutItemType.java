package spout.gamecontent.datadriven.itemtype;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;
import spout.gamecontent.datadriven.common.type.ExplicitTypeWithCodec;
import spout.gamecontent.datadriven.item.SpoutNonBuiltInItem;
import spout.util.mojang.codec.MapInputAndOps;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * An implementation of {@link SpoutItemType} defined by its codec.
 */
public final class CodecSpoutItemType extends ExplicitTypeWithCodec<Item, SpoutNonBuiltInItem> implements SpoutItemType {

    public CodecSpoutItemType(Identifier identifier, MapCodec<? extends Item> minecraftCodec) {
        super(identifier, minecraftCodec);
    }

    @Override
    protected SpoutNonBuiltInItem constructForInput(MapInputAndOps<?> input) {
        return new SpoutNonBuiltInItem(this, input);
    }

    @Override
    protected Map<Codec<?>, Decoder<Identifier>> getRequiredResourceFieldCodecs() {
        return getTypeRequiredResourceFieldCodecs();
    }

    /**
     * Cached return value for {@link #getTypeRequiredResourceFieldCodecs}.
     */
    private static @Nullable Map<Codec<?>, Decoder<Identifier>> typeRequiredResourceFieldCodecs;

    private static Map<Codec<?>, Decoder<Identifier>> getTypeRequiredResourceFieldCodecs() {
        if (typeRequiredResourceFieldCodecs == null) {
            typeRequiredResourceFieldCodecs = new IdentityHashMap<>(1);
            typeRequiredResourceFieldCodecs.put(BuiltInRegistries.ITEM.byNameCodec(), Identifier.CODEC);
        }
        return typeRequiredResourceFieldCodecs;
    }

}
