package spout.clientview.packetmapping.blockstate.macro;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import com.mojang.datafixers.util.Function10;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import spout.util.minecraft.blockstate.BlockStateStringConversion;
import spout.util.mojang.codec.CodecUtil;

/**
 * A {@link BlockStateMappingMacro} for slabs.
 */
public class SlabMacro extends FromToBlockMacro {

    public static <M extends SlabMacro> RecordCodecBuilder<M, @Nullable BlockState> getFullBlockFallbackStateCodecBuilder() {
        return CodecUtil.optionalFieldOf(BlockStateStringConversion.CODEC, "full_block_fallback", () -> null).forGetter(macro -> macro.fullBlockFallbackState);
    }

    public static <M extends SlabMacro> MapCodec<? extends M> simpleCodec(
        Block defaultFallbackBlock,
        Function10<Boolean, Boolean, Boolean, Boolean, Boolean, @Nullable Item, @Nullable Item, Block, Block, @Nullable BlockState, M> factory
    ) {
        return FromToBlockMacro.simpleCodec(
            defaultFallbackBlock,
            getFullBlockFallbackStateCodecBuilder(),
            factory
        );
    }

    public static Function<BlockStateMappingMacroType, ? extends MapCodec<? extends SlabMacro>> codecConstructor(Block defaultFallbackBlock) {
        return type -> simpleCodec(
            defaultFallbackBlock,
            (
                createFromToUsedStatesMappings,
                createProxyToVisualDuplicateMappings,
                createItemMappings,
                createVanillaMappings,
                createResourcePackBlockstatesEntries,
                fromItem,
                fallbackItem,
                fromBlock,
                fallbackBlock,
                fullBlockFallbackState
            ) -> new SlabMacro(
                type,
                createFromToUsedStatesMappings,
                createProxyToVisualDuplicateMappings,
                createItemMappings,
                createVanillaMappings,
                createResourcePackBlockstatesEntries,
                null,
                fromItem,
                fallbackItem,
                fromBlock,
                fallbackBlock,
                fullBlockFallbackState
            )
        );
    }

    public final @Nullable BlockState fullBlockFallbackState;

    public SlabMacro(
        BlockStateMappingMacroType type,
        boolean createFromToUsedStatesMappings,
        boolean createProxyToVisualDuplicateMappings,
        boolean createItemMappings,
        boolean createVanillaMappings,
        boolean createResourcePackBlockstatesEntries,
        @Nullable List<Consumer<UsedStates>> resultConsumers,
        @Nullable Item fromItem,
        @Nullable Item fallbackItem,
        Block fromBlock,
        Block fallbackBlock,
        @Nullable BlockState fullBlockFallbackState
    ) {
        super(
            type,
            createFromToUsedStatesMappings,
            createProxyToVisualDuplicateMappings,
            createItemMappings,
            createVanillaMappings,
            createResourcePackBlockstatesEntries,
            resultConsumers,
            fromItem,
            fallbackItem,
            fromBlock,
            fallbackBlock
        );
        this.fullBlockFallbackState = fullBlockFallbackState;
    }

}
