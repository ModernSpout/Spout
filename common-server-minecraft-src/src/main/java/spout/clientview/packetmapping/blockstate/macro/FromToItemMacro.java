package spout.clientview.packetmapping.blockstate.macro;

import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.component.DataComponents;
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

    private final @Nullable Item fromItem;
    private final @Nullable Item fallbackItem;

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
