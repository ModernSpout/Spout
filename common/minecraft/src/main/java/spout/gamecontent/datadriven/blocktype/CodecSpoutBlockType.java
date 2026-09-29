package spout.gamecontent.datadriven.blocktype;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;
import spout.gamecontent.datadriven.block.FormattedBlockStateCodec;
import spout.gamecontent.datadriven.block.SpoutNonBuiltInBlock;
import spout.gamecontent.datadriven.common.type.ExplicitTypeWithCodec;
import spout.util.mojang.codec.MapInputAndOps;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * An implementation of {@link SpoutBlockType} defined by its codec.
 */
public final class CodecSpoutBlockType extends ExplicitTypeWithCodec<Block, SpoutNonBuiltInBlock> implements SpoutBlockType {

    public CodecSpoutBlockType(Identifier identifier, MapCodec<? extends Block> minecraftCodec) {
        super(identifier, minecraftCodec);
    }

    @Override
    protected SpoutNonBuiltInBlock constructForInput(MapInputAndOps<?> input) {
        return new SpoutNonBuiltInBlock(this, input);
    }

    @Override
    protected Map<Codec<?>, Decoder<Identifier>> getRequiredResourceFieldCodecs() {
        return getTypeRequiredResourceFieldCodecs();
    }

    @Override
    public MapCodec<? extends Block> getBlockClassCodec() {
        return this.minecraftCodec;
    }

    /**
     * Cached return value for {@link #getTypeRequiredResourceFieldCodecs}.
     */
    private static @Nullable Map<Codec<?>, Decoder<Identifier>> typeRequiredResourceFieldCodecs;

    private static Map<Codec<?>, Decoder<Identifier>> getTypeRequiredResourceFieldCodecs() {
        if (typeRequiredResourceFieldCodecs == null) {
            typeRequiredResourceFieldCodecs = new IdentityHashMap<>(2);
            typeRequiredResourceFieldCodecs.put(BuiltInRegistries.BLOCK.byNameCodec(), Identifier.CODEC);
            typeRequiredResourceFieldCodecs.put(FormattedBlockStateCodec.CODEC, new Decoder<>() {

                @Override
                public <T> DataResult<Pair<Identifier, T>> decode(DynamicOps<T> dynamicOps, T input) {
                    return Codec.STRING.decode(dynamicOps, input).map(stringResult -> {
                        String string = stringResult.getFirst();
                        int openBracketIndex = string.indexOf('[');
                        return Pair.of(Identifier.parse(openBracketIndex == -1 ? string : string.substring(0, openBracketIndex)), input);
                    });
                }

            });
        }
        return typeRequiredResourceFieldCodecs;
    }

}
