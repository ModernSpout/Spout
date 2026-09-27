package spout.server.paper.impl.packetmapping.block.datadriven;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.clientview.packetmapping.blockstate.decodingcontext.BlockStateMappingDecodingContextBlock;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.processor.BlockStateMappingMacroProcessor;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import spout.server.paper.impl.packetmapping.block.BlockMappingsComposeEventImpl;
import org.jspecify.annotations.Nullable;
import java.util.List;

/**
 * A data-driven {@link BlockStateMapping}
 * or {@link BlockStateMappingMacro} that has not been applied yet.
 */
public final class UnappliedDataDrivenBlockMapping {

    public static final Codec<UnappliedDataDrivenBlockMapping> CODEC = new Codec<>() {

        @Override
        public <T> DataResult<T> encode(UnappliedDataDrivenBlockMapping mapping, DynamicOps<T> ops, T t) {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> DataResult<Pair<UnappliedDataDrivenBlockMapping, T>> decode(DynamicOps<T> ops, T input) {
            return ops.getMap(input).flatMap(mapLike -> DataResult.success(Pair.of(new UnappliedDataDrivenBlockMapping(ops, input, mapLike), input)));
        }

    };

    public static final Decoder<List<UnappliedDataDrivenBlockMapping>> LIST_CODEC = Codec.list(CODEC);

    private final DynamicOps<?> ops;
    private final Object input;
    private final MapLike<?> mapLike;

    private UnappliedDataDrivenBlockMapping(DynamicOps<?> ops, Object input, MapLike<?> mapLike) {
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
        Registry.register(registry, Identifier.fromNamespaceAndPath(block.keyInBlockRegistry.getNamespace(), block.keyInBlockRegistry.getPath() + "_json_" + BlockStateMappingMacroProcessor.generateRandomStringForMappingIdentifiers() + "_" + i), decoded);
    }

    public void applyAsMappingMacro(WritableRegistry<BlockStateMappingMacro> registry, Block block, int i) {
        BlockStateMappingMacro decoded;
        BlockStateMappingDecodingContextBlock.set(block);
        try {
            decoded = (BlockStateMappingMacro) ((MapCodec) BlockStateMappingMacroType.MACRO_CODEC).decode(this.ops, this.mapLike).getOrThrow();
        } finally {
            BlockStateMappingDecodingContextBlock.remove();
        }
        Registry.register(registry, Identifier.fromNamespaceAndPath(block.keyInBlockRegistry.getNamespace(), block.keyInBlockRegistry.getPath() + "_json_" + BlockStateMappingMacroProcessor.generateRandomStringForMappingIdentifiers() + "_" + i), decoded);
    }

    private static <T> void apply(BlockMappingsComposeEventImpl event, @Nullable Block block, DynamicOps<T> ops, MapLike<T> mapLike) {

        // Parse the type
        T typeInput = mapLike.get("type");
        if (typeInput == null) {
            throw new IllegalArgumentException("Missing mapping type for a mapping" + (block == null ? "" : " for block " + block));
        }
        DataResult<String> typeResult = ops.getStringValue(typeInput);
        if (typeResult.isError()) {
            throw new IllegalArgumentException("Invalid mapping type for a mapping" + (block == null ? "" : " for block " + block) + typeResult.error().map(error -> ": " + error.message()).orElse(""));
        }
        String typeString = typeResult.getOrThrow();
        @Nullable DataDrivenBlockMappingType type = DataDrivenBlockMappingTypeRegistry.get(typeString);
        if (type == null) {
            throw new IllegalArgumentException("Unknown mapping type for a mapping" + (block == null ? "" : " for block " + block) + ": " + typeString);
        }

        // Let the type apply the mapping
        type.apply(event, block, ops, mapLike);

    }

}
