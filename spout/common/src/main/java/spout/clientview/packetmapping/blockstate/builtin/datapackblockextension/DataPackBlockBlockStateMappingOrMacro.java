package spout.clientview.packetmapping.blockstate.builtin.datapackblockextension;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.world.level.block.Block;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.clientview.packetmapping.blockstate.decodingcontext.BlockStateMappingDecodingContextBlock;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.processor.BlockStateMappingMacroProcessor;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import spout.util.minecraft.resources.IdentifierUtil;
import java.util.List;

/**
 * A data-driven {@link BlockStateMapping}
 * or {@link BlockStateMappingMacro} that has not been applied yet.
 */
public final class DataPackBlockBlockStateMappingOrMacro {

    public static final Codec<DataPackBlockBlockStateMappingOrMacro> CODEC = new Codec<>() {

        @Override
        public <T> DataResult<T> encode(DataPackBlockBlockStateMappingOrMacro mapping, DynamicOps<T> ops, T t) {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> DataResult<Pair<DataPackBlockBlockStateMappingOrMacro, T>> decode(DynamicOps<T> ops, T input) {
            return ops.getMap(input).flatMap(mapLike -> DataResult.success(Pair.of(new DataPackBlockBlockStateMappingOrMacro(ops, input, mapLike), input)));
        }

    };

    public static final Decoder<List<DataPackBlockBlockStateMappingOrMacro>> LIST_CODEC = Codec.list(CODEC);

    private final DynamicOps<?> ops;
    private final Object input;
    private final MapLike<?> mapLike;

    private DataPackBlockBlockStateMappingOrMacro(DynamicOps<?> ops, Object input, MapLike<?> mapLike) {
        this.ops = ops;
        this.input = input;
        this.mapLike = mapLike;
    }

    public boolean isMacro() {
        return this.mapLike.get("type") != null;
    }

    public void applyAsMapping(WritableRegistry<BlockStateMapping> registry, Block block, int i) {
        BlockStateMapping decoded;
        BlockStateMappingDecodingContextBlock.set(block);
        try {
            decoded = (BlockStateMapping) ((Pair) ((Codec) BlockStateMapping.CODEC).decode(this.ops, this.input).getOrThrow()).getFirst();
        } finally {
            BlockStateMappingDecodingContextBlock.remove();
        }
        Registry.register(registry, IdentifierUtil.addPathSuffix(block.keyInBlockRegistry, "_json_" + BlockStateMappingMacroProcessor.generateRandomStringForMappingIdentifiers() + "_" + i), decoded);
    }

    public void applyAsMappingMacro(WritableRegistry<BlockStateMappingMacro> registry, Block block, int i) {
        BlockStateMappingMacro decoded;
        BlockStateMappingDecodingContextBlock.set(block);
        try {
            decoded = (BlockStateMappingMacro) ((MapCodec) BlockStateMappingMacroType.MACRO_CODEC).decode(this.ops, this.mapLike).getOrThrow();
        } finally {
            BlockStateMappingDecodingContextBlock.remove();
        }
        Registry.register(registry, IdentifierUtil.addPathSuffix(block.keyInBlockRegistry, "_json_" + BlockStateMappingMacroProcessor.generateRandomStringForMappingIdentifiers() + "_" + i), decoded);
    }

}
