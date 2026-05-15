package spout.common.moredatadriven.clientmodprotocol;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockTypes;
import spout.common.moredatadriven.minecraft.type.ItemTypes;
import org.jspecify.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all custom content sent by a Spout server.
 */
public final class ClientModCustomContent {

    /**
     * The block JSONs.
     */
    final List<JsonElement> blockJSONs;

    /**
     * The item JSONs.
     */
    final List<JsonElement> itemJSONs;

    ClientModCustomContent() {
        this.blockJSONs = new ArrayList<>();
        this.itemJSONs = new ArrayList<>();
    }

    /**
     * The parsed blocks,
     * of null if not initialized yet.
     */
    private @Nullable List<Block> parsedBlocks;

    /**
     * The parsed blocks,
     * of null if not initialized yet.
     */
    private @Nullable List<Item> parsedItems;

    public List<Block> getParsedBlocks() {
        if (this.parsedBlocks == null) {
            this.parsedBlocks = this.blockJSONs.stream().map(ClientModCustomContent::parseBlock).toList();
        }
        return this.parsedBlocks;
    }

    public List<Item> getParsedItems() {
        if (this.parsedItems == null) {
            this.parsedItems = this.itemJSONs.stream().map(ClientModCustomContent::parseItem).toList();
        }
        return this.parsedItems;
    }

    private static Block parseBlock(JsonElement json) {
        return BlockTypes.CODEC.codec().decode(JsonOps.INSTANCE, json).getOrThrow().getFirst();
    }

    private static Item parseItem(JsonElement json) {
        return ItemTypes.CODEC.codec().decode(JsonOps.INSTANCE, json).getOrThrow().getFirst();
    }

}
