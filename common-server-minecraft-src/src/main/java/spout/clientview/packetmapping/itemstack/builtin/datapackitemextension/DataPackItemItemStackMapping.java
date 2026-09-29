package spout.clientview.packetmapping.itemstack.builtin.datapackitemextension;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.world.item.Item;
import spout.clientview.packetmapping.blockstate.macro.processor.BlockStateMappingMacroProcessor;
import spout.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.clientview.packetmapping.itemstack.decodingcontext.ItemStackMappingDecodingContextItem;
import spout.util.minecraft.resources.IdentifierUtil;

/**
 * A data-driven {@link ItemStackMapping} that has not been applied yet.
 */
public final class DataPackItemItemStackMapping {

    public static final Codec<DataPackItemItemStackMapping> CODEC = new Codec<>() {

        @Override
        public <T> DataResult<T> encode(DataPackItemItemStackMapping mapping, DynamicOps<T> ops, T t) {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> DataResult<Pair<DataPackItemItemStackMapping, T>> decode(DynamicOps<T> ops, T input) {
            return DataResult.success(Pair.of(new DataPackItemItemStackMapping(ops, input), input));
        }

    };

    public static final Decoder<List<DataPackItemItemStackMapping>> LIST_CODEC = Codec.list(CODEC);

    private final DynamicOps<?> ops;
    private final Object input;

    private DataPackItemItemStackMapping(DynamicOps<?> ops, Object input) {
        this.ops = ops;
        this.input = input;
    }

    public void apply(WritableRegistry<ItemStackMapping> registry, Item item, int i) {
        ItemStackMapping decoded;
        ItemStackMappingDecodingContextItem.set(item);
        try {
            decoded = (ItemStackMapping) ((Pair) ((Codec) ItemStackMapping.CODEC).decode(this.ops, this.input).getOrThrow()).getFirst();
        } finally {
            ItemStackMappingDecodingContextItem.remove();
        }
        Registry.register(registry, IdentifierUtil.addPathSuffix(item.keyInItemRegistry, "_json_" + BlockStateMappingMacroProcessor.generateRandomStringForMappingIdentifiers() + "_" + i), decoded);
    }

}
