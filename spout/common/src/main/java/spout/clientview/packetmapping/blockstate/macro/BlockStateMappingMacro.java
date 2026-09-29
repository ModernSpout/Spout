package spout.clientview.packetmapping.blockstate.macro;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Function10;
import com.mojang.datafixers.util.Function5;
import com.mojang.datafixers.util.Function6;
import com.mojang.datafixers.util.Function7;
import com.mojang.datafixers.util.Function8;
import com.mojang.datafixers.util.Function9;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import spout.util.mojang.codec.CodecUtil;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A macro to generate block state mappings.
 */
public class BlockStateMappingMacro {

    public static <M extends BlockStateMappingMacro> RecordCodecBuilder<M, Boolean> getCreateFromToUsedStatesMappingsCodecBuilder() {
        return CodecUtil.optionalFieldOf(Codec.BOOL, "create_from_to_used_states_mappings", () -> true).forGetter(macro -> macro.createFromToUsedStatesMappings);
    }

    public static <M extends BlockStateMappingMacro> RecordCodecBuilder<M, Boolean> getCreateProxyToVisualDuplicateMappingsCodecBuilder() {
        return CodecUtil.optionalFieldOf(Codec.BOOL, "create_proxy_to_visual_duplicate_mappings", () -> true).forGetter(macro -> macro.createProxyToVisualDuplicateMappings);
    }

    public static <M extends BlockStateMappingMacro> RecordCodecBuilder<M, Boolean> getCreateItemMappingsCodecBuilder() {
        return CodecUtil.optionalFieldOf(Codec.BOOL, "create_item_mappings", () -> true).forGetter(macro -> macro.createItemMappings);
    }

    public static <M extends BlockStateMappingMacro> RecordCodecBuilder<M, Boolean> getCreateVanillaMappingsCodecBuilder() {
        return CodecUtil.optionalFieldOf(Codec.BOOL, "create_vanilla_mappings", () -> true).forGetter(macro -> macro.createVanillaMappings);
    }

    public static <M extends BlockStateMappingMacro> RecordCodecBuilder<M, Boolean> getCreateResourcePackBlockstatesEntriesCodecBuilder() {
        return CodecUtil.optionalFieldOf(Codec.BOOL, "create_resource_pack_blockstates_entries", () -> true).forGetter(macro -> macro.createResourcePackBlockstatesEntries);
    }

    public static <M extends BlockStateMappingMacro> MapCodec<? extends M> simpleCodec(
        Function5<Boolean, Boolean, Boolean, Boolean, Boolean, M> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            getCreateFromToUsedStatesMappingsCodecBuilder(),
            getCreateProxyToVisualDuplicateMappingsCodecBuilder(),
            getCreateItemMappingsCodecBuilder(),
            getCreateVanillaMappingsCodecBuilder(),
            getCreateResourcePackBlockstatesEntriesCodecBuilder()
        ).apply(instance, factory));
    }

    public static <M extends BlockStateMappingMacro, T1> MapCodec<? extends M> simpleCodec(
        App<RecordCodecBuilder.Mu<M>, T1> t1,
        Function6<Boolean, Boolean, Boolean, Boolean, Boolean, T1, M> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            getCreateFromToUsedStatesMappingsCodecBuilder(),
            getCreateProxyToVisualDuplicateMappingsCodecBuilder(),
            getCreateItemMappingsCodecBuilder(),
            getCreateVanillaMappingsCodecBuilder(),
            getCreateResourcePackBlockstatesEntriesCodecBuilder(),
            t1
        ).apply(instance, factory));
    }

    public static <M extends BlockStateMappingMacro, T1, T2> MapCodec<? extends M> simpleCodec(
        App<RecordCodecBuilder.Mu<M>, T1> t1,
        App<RecordCodecBuilder.Mu<M>, T2> t2,
        Function7<Boolean, Boolean, Boolean, Boolean, Boolean, T1, T2, M> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            getCreateFromToUsedStatesMappingsCodecBuilder(),
            getCreateProxyToVisualDuplicateMappingsCodecBuilder(),
            getCreateItemMappingsCodecBuilder(),
            getCreateVanillaMappingsCodecBuilder(),
            getCreateResourcePackBlockstatesEntriesCodecBuilder(),
            t1,
            t2
        ).apply(instance, factory));
    }

    public static <M extends BlockStateMappingMacro, T1, T2, T3> MapCodec<? extends M> simpleCodec(
        App<RecordCodecBuilder.Mu<M>, T1> t1,
        App<RecordCodecBuilder.Mu<M>, T2> t2,
        App<RecordCodecBuilder.Mu<M>, T3> t3,
        Function8<Boolean, Boolean, Boolean, Boolean, Boolean, T1, T2, T3, M> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            getCreateFromToUsedStatesMappingsCodecBuilder(),
            getCreateProxyToVisualDuplicateMappingsCodecBuilder(),
            getCreateItemMappingsCodecBuilder(),
            getCreateVanillaMappingsCodecBuilder(),
            getCreateResourcePackBlockstatesEntriesCodecBuilder(),
            t1,
            t2,
            t3
        ).apply(instance, factory));
    }

    public static <M extends BlockStateMappingMacro, T1, T2, T3, T4> MapCodec<? extends M> simpleCodec(
        App<RecordCodecBuilder.Mu<M>, T1> t1,
        App<RecordCodecBuilder.Mu<M>, T2> t2,
        App<RecordCodecBuilder.Mu<M>, T3> t3,
        App<RecordCodecBuilder.Mu<M>, T4> t4,
        Function9<Boolean, Boolean, Boolean, Boolean, Boolean, T1, T2, T3, T4, M> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            getCreateFromToUsedStatesMappingsCodecBuilder(),
            getCreateProxyToVisualDuplicateMappingsCodecBuilder(),
            getCreateItemMappingsCodecBuilder(),
            getCreateVanillaMappingsCodecBuilder(),
            getCreateResourcePackBlockstatesEntriesCodecBuilder(),
            t1,
            t2,
            t3,
            t4
        ).apply(instance, factory));
    }

    public static <M extends BlockStateMappingMacro, T1, T2, T3, T4, T5> MapCodec<? extends M> simpleCodec(
        App<RecordCodecBuilder.Mu<M>, T1> t1,
        App<RecordCodecBuilder.Mu<M>, T2> t2,
        App<RecordCodecBuilder.Mu<M>, T3> t3,
        App<RecordCodecBuilder.Mu<M>, T4> t4,
        App<RecordCodecBuilder.Mu<M>, T5> t5,
        Function10<Boolean, Boolean, Boolean, Boolean, Boolean, T1, T2, T3, T4, T5, M> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            getCreateFromToUsedStatesMappingsCodecBuilder(),
            getCreateProxyToVisualDuplicateMappingsCodecBuilder(),
            getCreateItemMappingsCodecBuilder(),
            getCreateVanillaMappingsCodecBuilder(),
            getCreateResourcePackBlockstatesEntriesCodecBuilder(),
            t1,
            t2,
            t3,
            t4,
            t5
        ).apply(instance, factory));
    }

    public static Function<BlockStateMappingMacroType, ? extends MapCodec<? extends BlockStateMappingMacro>> codecConstructor() {
        return type -> simpleCodec(
            (
                createFromToUsedStatesMappings,
                createProxyToVisualDuplicateMappings,
                createItemMappings,
                createVanillaMappings,
                createResourcePackBlockstatesEntries
            ) -> new BlockStateMappingMacro(
                type,
                createFromToUsedStatesMappings,
                createProxyToVisualDuplicateMappings,
                createItemMappings,
                createVanillaMappings,
                createResourcePackBlockstatesEntries,
                null
            )
        );
    }

    public final BlockStateMappingMacroType type;
    public final boolean createFromToUsedStatesMappings;
    public final boolean createProxyToVisualDuplicateMappings;
    public final boolean createItemMappings;
    public final boolean createVanillaMappings;
    public final boolean createResourcePackBlockstatesEntries;
    public final @Nullable List<Consumer<UsedStates>> resultConsumers;

    public BlockStateMappingMacro(
        BlockStateMappingMacroType type,
        boolean createFromToUsedStatesMappings,
        boolean createProxyToVisualDuplicateMappings,
        boolean createItemMappings,
        boolean createVanillaMappings,
        boolean createResourcePackBlockstatesEntries,
        @Nullable List<Consumer<UsedStates>> resultConsumers
    ) {
        this.type = type;
        this.createFromToUsedStatesMappings = createFromToUsedStatesMappings;
        this.createProxyToVisualDuplicateMappings = createProxyToVisualDuplicateMappings;
        this.createItemMappings = createItemMappings;
        this.createVanillaMappings = createVanillaMappings;
        this.createResourcePackBlockstatesEntries = createResourcePackBlockstatesEntries;
        this.resultConsumers = resultConsumers;
    }

    public @Nullable Function<BlockState, @Nullable Item> getFromItemFunction() {
        return null;
    }

    public @Nullable Function<BlockState, @Nullable Item> getToItemFunction() {
        return null;
    }

    public void validateArguments() {
    }

}
