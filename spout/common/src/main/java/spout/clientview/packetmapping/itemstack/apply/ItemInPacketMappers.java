package spout.clientview.packetmapping.itemstack.apply;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.FallbackContextValueInPacketMapper;
import spout.clientview.packetmapping.clientviewlookup.ThreadLocalClientViewLookup;
import spout.util.mapping.handle.MappingStep;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Provides the functions to map items in packets.
 */
public final class ItemInPacketMappers {

    private ItemInPacketMappers() {
        throw new UnsupportedOperationException();
    }

    private static abstract class AbstractItemInPacketMapper<T> implements FallbackContextValueInPacketMapper<T, T, ItemStackMappingsApplicationContext> {

        @Override
        public ItemStackMappingsApplicationContext getFallbackContext() {
            return new ItemStackMappingsApplicationContext(ThreadLocalClientViewLookup.getThreadLocalClientViewOrFallback());
        }

    }

    public static final FallbackContextValueInPacketMapper<ItemStack, ItemStack, ItemStackMappingsApplicationContext> ITEM_STACK = new AbstractItemInPacketMapper<>() {

        @Override
        public ItemStack applyWithNonNullContext(ItemStack value, ItemStackMappingsApplicationContext context) {
            // Skip the mapping for empty item stacks
            if (value.isEmpty() || value.getItem() == null) {
                return value;
            }

            int awarenessLevelId = context.getClientView().getAwarenessLevel().getId();
            int valueIndexInRegistry = value.getItem().indexInItemRegistry;
            // If there is a mapping chain, apply it
            Consumer<ItemStackMappingHandle> @Nullable [] chain = OptimizedItemStackMappings.getChain(awarenessLevelId, valueIndexInRegistry);
            if (chain != null) {
                return MappingStep.applyChain(new ItemStackMappingHandle(value, context, false), chain);
            }
            // No mappings need to be applied
            return value;
        }

    };

    public static final FallbackContextValueInPacketMapper<Item, Item, ItemStackMappingsApplicationContext> ITEM = new AbstractItemInPacketMapper<>() {

        @Override
        public Item applyWithNonNullContext(Item value, ItemStackMappingsApplicationContext context) {
            return ITEM_STACK.applyWithNonNullContext(new ItemStack(value), context).getItem();
        }

    };

    public static final FallbackContextValueInPacketMapper<ItemStackTemplate, ItemStackTemplate, ItemStackMappingsApplicationContext> ITEM_STACK_TEMPLATE = new AbstractItemInPacketMapper<>() {

        @Override
        public ItemStackTemplate applyWithNonNullContext(ItemStackTemplate value, ItemStackMappingsApplicationContext context) {
            ItemStack original = value.create();
            ItemStack mapped = ITEM_STACK.applyWithNonNullContext(original, context);
            if (mapped == original) {
                return value;
            }
            return ItemStackTemplate.fromNonEmptyStack(mapped);
        }

    };

    public static final FallbackContextValueInPacketMapper<Holder<Item>, Holder<Item>, ItemStackMappingsApplicationContext> ITEM_HOLDER = new AbstractItemInPacketMapper<>() {

        @Override
        public Holder<Item> applyWithNonNullContext(Holder<Item> value, ItemStackMappingsApplicationContext context) {
            Item original = value.value();
            Item mapped = ITEM.apply(original, context);
            if (mapped == original) {
                return value;
            }
            return Holder.direct(mapped);
        }

    };

    public static final FallbackContextValueInPacketMapper<HolderSet<Item>, HolderSet<Item>, ItemStackMappingsApplicationContext> ITEM_HOLDER_SET = new AbstractItemInPacketMapper<>() {

        @Override
        public HolderSet<Item> applyWithNonNullContext(HolderSet<Item> value, ItemStackMappingsApplicationContext context) {
            Int2ObjectMap<Item> fromToMap = new Int2ObjectArrayMap<>();
            List<Holder<Item>> result = new ArrayList<>(value.size());
            boolean changed = false;
            for (int i = 0; i < value.size(); i++) {
                Holder<Item> originalHolder = value.get(i);
                Item original = originalHolder.value();
                int id = original.indexInItemRegistry;
                Item mapped = fromToMap.computeIfAbsent(id, $ -> ITEM.apply(original, context));
                if (mapped != original) {
                    changed = true;
                }
                result.add(changed ? Holder.direct(mapped) : originalHolder);
            }
            return changed ? HolderSet.direct(result) : value;
        }

    };

    public static boolean hasAnyMapping(int awarenessLevelId, int itemIndexInRegistry) {
        return OptimizedItemStackMappings.getChain(awarenessLevelId, itemIndexInRegistry) != null;
    }

    public static boolean hasAnyMapping(int awarenessLevelId, Item item) {
        return hasAnyMapping(awarenessLevelId, item.indexInItemRegistry);
    }

}
