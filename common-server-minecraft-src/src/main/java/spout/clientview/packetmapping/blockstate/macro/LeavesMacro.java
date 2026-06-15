package spout.clientview.packetmapping.blockstate.macro;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import com.mojang.datafixers.util.Function10;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import spout.util.mojang.codec.CodecUtil;

/**
 * A {@link BlockStateMappingMacro} for leaves.
 */
public class LeavesMacro extends FromToBlockMacro {

    public static <M extends LeavesMacro> RecordCodecBuilder<M, @Nullable Boolean> getTintedCodecBuilder() {
        return CodecUtil.optionalFieldOf(Codec.BOOL, "tinted", () -> null).forGetter(macro -> macro.tinted);
    }

    public static <M extends LeavesMacro> MapCodec<? extends M> simpleCodec(
        Block defaultFallbackBlock,
        Function10<Boolean, Boolean, Boolean, Boolean, Boolean, @Nullable Item, @Nullable Item, Block, Block, @Nullable Boolean, M> factory
    ) {
        return FromToBlockMacro.simpleCodec(
            defaultFallbackBlock,
            getTintedCodecBuilder(),
            factory
        );
    }

    public static Function<BlockStateMappingMacroType, ? extends MapCodec<? extends LeavesMacro>> codecConstructor(Block defaultFallbackBlock) {
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
                tinted
            ) -> new LeavesMacro(
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
                tinted
            )
        );
    }

    protected final @Nullable Boolean tinted;

    public LeavesMacro(
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
        @Nullable Boolean tinted
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
        this.tinted = tinted;
    }

    public boolean getTinted() {
        return this.tinted != null ? tinted : this.fromBlock instanceof TintedParticleLeavesBlock;
    }

}
