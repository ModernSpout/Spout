package spout.clientview.packetmapping.blockstate.macro;

import com.mojang.datafixers.util.Function9;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import spout.util.minecraft.blockstate.BlockStateStringConversion;
import spout.util.mojang.codec.CodecUtil;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A {@link BlockStateMappingMacro} that maps from and to a block state.
 */
public class FromToBlockStateMacro extends FromToItemMacro implements FromToBlockStatesMacro {

    public static <M extends FromToBlockStateMacro> RecordCodecBuilder<M, BlockState> getFromStateCodecBuilder() {
        return BlockStateStringConversion.CODEC.fieldOf("from").forGetter(macro -> macro.fromState);
    }

    public static <M extends FromToBlockStateMacro> RecordCodecBuilder<M, BlockState> getFallbackStateCodecBuilder(BlockState defaultFallbackState) {
        return CodecUtil.optionalFieldOf(BlockStateStringConversion.CODEC, "fallback", () -> defaultFallbackState).forGetter(macro -> macro.fallbackState);
    }

    public static <M extends FromToBlockStateMacro> MapCodec<? extends M> simpleCodec(
        BlockState defaultFallbackState,
        Function9<Boolean, Boolean, Boolean, Boolean, Boolean, @Nullable Item, @Nullable Item, BlockState, BlockState, M> factory
    ) {
        return FromToItemMacro.simpleCodec(
            getFromStateCodecBuilder(),
            getFallbackStateCodecBuilder(defaultFallbackState),
            factory
        );
    }

    public static Function<BlockStateMappingMacroType, ? extends MapCodec<? extends FromToBlockStateMacro>> codecConstructor(BlockState defaultFallbackState) {
        return type -> simpleCodec(
            defaultFallbackState,
            (
                createFromToUsedStatesMappings,
                createProxyToVisualDuplicateMappings,
                createItemMappings,
                createVanillaMappings,
                createResourcePackBlockstatesEntries,
                fromItem,
                fallbackItem,
                fromState,
                fallbackState
            ) -> new FromToBlockStateMacro(
                type,
                createFromToUsedStatesMappings,
                createProxyToVisualDuplicateMappings,
                createItemMappings,
                createVanillaMappings,
                createResourcePackBlockstatesEntries,
                null,
                fromItem,
                fallbackItem,
                fromState,
                fallbackState
            )
        );
    }

    public final BlockState fromState;
    public final BlockState fallbackState;

    private BlockState @Nullable [] fromStates;
    private BlockState @Nullable [] fallbackStates;

    public FromToBlockStateMacro(
        BlockStateMappingMacroType type,
        boolean createFromToUsedStatesMappings,
        boolean createProxyToVisualDuplicateMappings,
        boolean createItemMappings,
        boolean createVanillaMappings,
        boolean createResourcePackBlockstatesEntries,
        @Nullable List<Consumer<UsedStates>> resultConsumers,
        @Nullable Item fromItem,
        @Nullable Item fallbackItem,
        BlockState fromState,
        BlockState fallbackState
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
            fallbackItem
        );
        this.fromState = fromState;
        this.fallbackState = fallbackState;
    }

    @Override
    protected @Nullable Block getBlockToInferFromItem() {
        return this.fromState.getBlock();
    }

    @Override
    protected @Nullable Block getBlockToInferFallbackItem() {
        return this.fallbackState.getBlock();
    }

    @Override
    public BlockState[] getFromStates() {
        if (this.fromStates == null) {
            this.fromStates = new BlockState[]{this.fromState};
        }
        return this.fromStates;
    }

    @Override
    public BlockState[] getFallbackStates() {
        if (this.fallbackStates == null) {
            this.fallbackStates = new BlockState[]{this.fallbackState};
        }
        return this.fallbackStates;
    }

}
