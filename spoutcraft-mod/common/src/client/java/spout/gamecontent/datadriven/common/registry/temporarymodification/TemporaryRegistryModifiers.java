package spout.gamecontent.datadriven.common.registry.temporarymodification;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;
import spout.gamecontent.datadriven.block.TemporaryBlockRegistryModifier;
import spout.gamecontent.datadriven.item.TemporaryItemRegistryModifier;
import java.util.List;
import java.util.function.Supplier;

/**
 * A central access point for modifying the registries we wish to modify.
 */
public final class TemporaryRegistryModifiers {

    private TemporaryRegistryModifiers() {
        throw new UnsupportedOperationException();
    }

    /**
     * The Minecraft block registry modifier, or null if not initialized yet.
     */
    private static @Nullable TemporaryRegistryModifier<Block, ?> blockRegistryModifier;

    /**
     * The Minecraft block registry modifier, or null if not initialized yet.
     */
    private static @Nullable TemporaryRegistryModifier<Item, ?> itemRegistryModifier;

    private static void initializeIfNecessary() {
        if (blockRegistryModifier != null) {
            // Already initialized
            return;
        }
        blockRegistryModifier = new TemporaryBlockRegistryModifier();
        itemRegistryModifier = new TemporaryItemRegistryModifier();
    }

    public static void prepareToAddCustomContent() {
        initializeIfNecessary();
        blockRegistryModifier.unfreeze();
        itemRegistryModifier.unfreeze();
    }

    public static void addCustomContent(
        Supplier<List<Pair<ResourceKey<Block>, Supplier<Block>>>> blocks,
        Supplier<List<Pair<ResourceKey<Item>, Supplier<Item>>>> items
    ) {
        initializeIfNecessary();
        blockRegistryModifier.addAndRefreeze(blocks.get());
        itemRegistryModifier.addAndRefreeze(items.get());
    }

    public static void removeCustomContent() {
        initializeIfNecessary();
        blockRegistryModifier.remove();
        itemRegistryModifier.remove();
    }

}
