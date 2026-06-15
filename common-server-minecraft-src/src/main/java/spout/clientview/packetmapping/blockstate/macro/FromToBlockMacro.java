package spout.clientview.packetmapping.blockstate.macro;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Function10;
import com.mojang.datafixers.util.Function9;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import spout.util.mojang.codec.CodecUtil;

/**
 * A {@link BlockStateMappingMacro} that maps from and to a block.
 */
public class FromToBlockMacro extends FromToItemMacro implements FromToBlockStatesMacro {

    public static <M extends FromToBlockMacro> RecordCodecBuilder<M, Block> getFromBlockCodecBuilder() {
        return BuiltInRegistries.BLOCK.byNameCodec().fieldOf("from").forGetter(macro -> macro.fromBlock);
    }

    public static <M extends FromToBlockMacro> RecordCodecBuilder<M, Block> getFallbackBlockCodecBuilder(Block defaultFallbackBlock) {
        return CodecUtil.optionalFieldOf(BuiltInRegistries.BLOCK.byNameCodec(), "fallback", () -> defaultFallbackBlock).forGetter(macro -> macro.fallbackBlock);
    }

    public static <M extends FromToBlockMacro> MapCodec<? extends M> simpleCodec(
        Block defaultFallbackBlock,
        Function9<Boolean, Boolean, Boolean, Boolean, Boolean, @Nullable Item, @Nullable Item, Block, Block, M> factory
    ) {
        return FromToItemMacro.simpleCodec(
            getFromBlockCodecBuilder(),
            getFallbackBlockCodecBuilder(defaultFallbackBlock),
            factory
        );
    }

    public static <M extends FromToBlockMacro, T1> MapCodec<? extends M> simpleCodec(
        Block defaultFallbackBlock,
        App<RecordCodecBuilder.Mu<M>, T1> t1,
        Function10<Boolean, Boolean, Boolean, Boolean, Boolean, @Nullable Item, @Nullable Item, Block, Block, T1, M> factory
    ) {
        return FromToItemMacro.simpleCodec(
            getFromBlockCodecBuilder(),
            getFallbackBlockCodecBuilder(defaultFallbackBlock),
            t1,
            factory
        );
    }

    public static Function<BlockStateMappingMacroType, ? extends MapCodec<? extends FromToBlockMacro>> codecConstructor(Block defaultFallbackBlock) {
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
                fallbackBlock
            ) -> new FromToBlockMacro(
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
                fallbackBlock
            )
        );
    }

    public final Block fromBlock;
    public final Block fallbackBlock;

    private BlockState @Nullable [] fromStates;
    private BlockState @Nullable [] fallbackStates;

    public FromToBlockMacro(
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
        Block fallbackBlock
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
        this.fromBlock = fromBlock;
        this.fallbackBlock = fallbackBlock;
    }

    @Override
    protected @Nullable Block getBlockToInferFromItem() {
        return this.fromBlock;
    }

    @Override
    protected @Nullable Block getBlockToInferFallbackItem() {
        return this.fallbackBlock;
    }

    @Override
    public BlockState[] getFromStates() {
        if (this.fromStates == null) {
            this.fromStates = this.fromBlock.getStateDefinition().getPossibleStatesArray();
        }
        return this.fromStates;
    }

    @Override
    public BlockState[] getFallbackStates() {
        if (this.fallbackStates == null) {
            BlockState fallbackDefaultState = this.fallbackBlock.defaultBlockState();
            BlockState[] fromStates = this.getFromStates();
            this.fallbackStates = new BlockState[fromStates.length];
            for (int i = 0; i < fallbackStates.length; i++) {
                this.fallbackStates[i] = fallbackDefaultState;
                for (Property<?> property : fallbackDefaultState.getProperties()) {
                    if (fromStates[i].hasProperty(property)) {
                        this.fallbackStates[i] = this.fallbackStates[i].setValue((Property) property, fromStates[i].getValue(property));
                    }
                }
            }
        }
        return this.fallbackStates;
    }

}
