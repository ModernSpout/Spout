package spout.common.protocol;

import it.unimi.dsi.fastutil.ints.IntObjectPair;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import spout.common.moredatadriven.minecraft.block.BlockStateRegistryEntryIdList;
import spout.common.moredatadriven.minecraft.block.SpoutNonBuiltInBlock;
import spout.common.moredatadriven.minecraft.common.subtypes.BlockStateStringConversion;
import spout.common.moredatadriven.minecraft.item.SpoutNonBuiltInItem;
import spout.common.moredatadriven.minecraft.registry.RegistryEntryIdList;
import spout.common.util.minecraft.resources.KeyedValue;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all custom content sent by a Spout server.
 */
public final class ClientModCustomContent {

    private static final int REGISTRY_ENTRY_ID_LIST_MAX_SIZE = 4096;

    /**
     * The blocks.
     */
    private final List<KeyedValue<SpoutNonBuiltInBlock>> blocks;

    /**
     * The items.
     */
    private final List<KeyedValue<SpoutNonBuiltInItem>> items;

    /**
     * The {@link RegistryEntryIdList}s.
     */
    private final List<RegistryEntryIdList> registryEntryIdLists;

    /**
     * The {@link BlockStateRegistryEntryIdList}s.
     */
    private final List<BlockStateRegistryEntryIdList> blockStateRegistryEntryIdLists;

    private ClientModCustomContent() {
        this.blocks = new ArrayList<>();
        this.items = new ArrayList<>();
        this.registryEntryIdLists = new ArrayList<>();
        this.blockStateRegistryEntryIdLists = new ArrayList<>();
    }

    public List<KeyedValue<SpoutNonBuiltInBlock>> getBlocks() {
        return this.blocks;
    }

    public List<KeyedValue<SpoutNonBuiltInItem>> getItems() {
        return this.items;
    }

    public List<RegistryEntryIdList> getRegistryEntryIdLists() {
        return this.registryEntryIdLists;
    }

    public List<BlockStateRegistryEntryIdList> getBlockStateRegistryEntryIdLists() {
        return this.blockStateRegistryEntryIdLists;
    }

    private <T> void addRegistryEntryIds(Registry<T> registry, List<T> entries) {
        Identifier registryKey = registry.key().identifier();
        for (int batchStart = 0; batchStart < entries.size(); batchStart += REGISTRY_ENTRY_ID_LIST_MAX_SIZE) {
            int batchSize = Math.min(REGISTRY_ENTRY_ID_LIST_MAX_SIZE, entries.size() - batchStart);
            List<IntObjectPair<Identifier>> list = new ArrayList<>(batchSize);
            int batchEnd = batchStart + batchSize;
            for (int i = batchStart; i < batchEnd; i++) {
                T entry = entries.get(i);
                list.add(IntObjectPair.of(registry.getId(entry), registry.getKey(entry)));
            }
            this.registryEntryIdLists.add(new RegistryEntryIdList(registryKey, list));
        }
    }

    private <T> void addBlockStateRegistryEntryIds(List<Block> blocks) {
        List<BlockState> entries = blocks.stream().flatMap(block -> block.getStateDefinition().getPossibleStates().stream()).toList();
        for (int batchStart = 0; batchStart < entries.size(); batchStart += REGISTRY_ENTRY_ID_LIST_MAX_SIZE) {
            int batchSize = Math.min(REGISTRY_ENTRY_ID_LIST_MAX_SIZE, entries.size() - batchStart);
            List<IntObjectPair<String>> list = new ArrayList<>(batchSize);
            int batchEnd = batchStart + batchSize;
            for (int i = batchStart; i < batchEnd; i++) {
                BlockState entry = entries.get(i);
                list.add(IntObjectPair.of(Block.BLOCK_STATE_REGISTRY.getId(entry), BlockStateStringConversion.blockStateToString(entry)));
            }
            this.blockStateRegistryEntryIdLists.add(new BlockStateRegistryEntryIdList(list));
        }
    }

    public static ClientModCustomContent createFilled(
        List<Block> blocks,
        List<Item> items
    ) {
        ClientModCustomContent content = new ClientModCustomContent();
        content.blocks.addAll(
            blocks.stream().map(block -> KeyedValue.of(block.builtInRegistryHolder().key().identifier(), new SpoutNonBuiltInBlock(block))).toList()
        );
        content.items.addAll(
            items.stream().map(item -> KeyedValue.of(item.builtInRegistryHolder().key().identifier(), new SpoutNonBuiltInItem(item))).toList()
        );
        content.addRegistryEntryIds(BuiltInRegistries.BLOCK, blocks);
        content.addRegistryEntryIds(BuiltInRegistries.ITEM, items);
        content.addBlockStateRegistryEntryIds(blocks);
        return content;
    }

    public static ClientModCustomContent createEmpty() {
        return new ClientModCustomContent();
    }

}
