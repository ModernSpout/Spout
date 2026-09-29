package spout.clientview.packetmapping.blockstate.macro;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Function10;
import com.mojang.datafixers.util.Function7;
import com.mojang.datafixers.util.Function8;
import com.mojang.datafixers.util.Function9;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;

/**
 * A {@link BlockStateMappingMacro} that maps from and to an item.
 */
public abstract class FromToItemMacro extends BlockStateMappingMacro {

    public static <M extends FromToItemMacro> RecordCodecBuilder<M, Optional<Item>> getFromItemCodecBuilder() {
        return BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("from_item").forGetter(macro -> Optional.ofNullable(macro.fromItem));
    }

    public static <M extends FromToItemMacro> RecordCodecBuilder<M, Optional<Item>> getFallbackItemCodecBuilder() {
        return BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("fallback_item").forGetter(macro -> Optional.ofNullable(macro.fallbackItem));
    }

    public static <M extends FromToItemMacro> MapCodec<? extends M> simpleCodec(
        Function7<Boolean, Boolean, Boolean, Boolean, Boolean, Optional<Item>, Optional<Item>, M> factory
    ) {
        return BlockStateMappingMacro.simpleCodec(
            getFromItemCodecBuilder(),
            getFallbackItemCodecBuilder(),
            factory
        );
    }

    public static <M extends FromToItemMacro, T1> MapCodec<? extends M> simpleCodec(
        App<RecordCodecBuilder.Mu<M>, T1> t1,
        Function8<Boolean, Boolean, Boolean, Boolean, Boolean, Optional<Item>, Optional<Item>, T1, M> factory
    ) {
        return BlockStateMappingMacro.simpleCodec(
            getFromItemCodecBuilder(),
            getFallbackItemCodecBuilder(),
            t1,
            factory
        );
    }

    public static <M extends FromToItemMacro, T1, T2> MapCodec<? extends M> simpleCodec(
        App<RecordCodecBuilder.Mu<M>, T1> t1,
        App<RecordCodecBuilder.Mu<M>, T2> t2,
        Function9<Boolean, Boolean, Boolean, Boolean, Boolean, Optional<Item>, Optional<Item>, T1, T2, M> factory
    ) {
        return BlockStateMappingMacro.simpleCodec(
            getFromItemCodecBuilder(),
            getFallbackItemCodecBuilder(),
            t1,
            t2,
            factory
        );
    }

    public static <M extends FromToItemMacro, T1, T2, T3> MapCodec<? extends M> simpleCodec(
        App<RecordCodecBuilder.Mu<M>, T1> t1,
        App<RecordCodecBuilder.Mu<M>, T2> t2,
        App<RecordCodecBuilder.Mu<M>, T3> t3,
        Function10<Boolean, Boolean, Boolean, Boolean, Boolean, Optional<Item>, Optional<Item>, T1, T2, T3, M> factory
    ) {
        return BlockStateMappingMacro.simpleCodec(
            getFromItemCodecBuilder(),
            getFallbackItemCodecBuilder(),
            t1,
            t2,
            t3,
            factory
        );
    }

    protected final @Nullable Item fromItem;
    protected final @Nullable Item fallbackItem;

    public FromToItemMacro(
        BlockStateMappingMacroType type,
        boolean createFromToUsedStatesMappings,
        boolean createProxyToVisualDuplicateMappings,
        boolean createItemMappings,
        boolean createVanillaMappings,
        boolean createResourcePackBlockstatesEntries,
        @Nullable List<Consumer<UsedStates>> resultConsumers,
        @Nullable Item fromItem,
        @Nullable Item fallbackItem
    ) {
        super(
            type,
            createFromToUsedStatesMappings,
            createProxyToVisualDuplicateMappings,
            createItemMappings,
            createVanillaMappings,
            createResourcePackBlockstatesEntries,
            resultConsumers
        );
        this.fromItem = fromItem;
        this.fallbackItem = fallbackItem;
    }

    protected abstract @Nullable Block getBlockToInferFromItem();

    protected @Nullable Item inferFromItem() {
        return inferItem(this.getBlockToInferFromItem());
    }

    public @Nullable Item getFromItem() {
        return this.fromItem != null ? this.fromItem : this.inferFromItem();
    }

    protected abstract @Nullable Block getBlockToInferFallbackItem();

    protected @Nullable Item inferFallbackItem() {
        return inferItem(this.getBlockToInferFallbackItem());
    }

    public @Nullable Item getFallbackItem() {
        return this.fallbackItem != null ? this.fallbackItem : this.inferFallbackItem();
    }

    public @Nullable Identifier getFromItemModel() { // TODO make customizable
        @Nullable Item item = this.getFromItem();
        return item == null ? null : item.getDefaultInstance().getOrDefault(DataComponents.ITEM_MODEL, item.keyInItemRegistry);
    }

    public static @Nullable Item inferItem(@Nullable Block block) {
        if (block == null) return null;
        Item item = block.asItem();
        if (item == Items.AIR) return null;
        return item;
    }

}
