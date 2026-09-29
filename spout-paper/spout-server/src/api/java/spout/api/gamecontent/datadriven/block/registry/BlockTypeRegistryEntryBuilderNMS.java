package spout.api.gamecontent.datadriven.block.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import spout.api.gamecontent.datadriven.common.keyawareregistry.KeyAwareRegistryEntryNMS;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A {@link BlockTypeRegistryEntry.Builder} that allows building a {@link Block} using Minecraft internals.
 */
public interface BlockTypeRegistryEntryBuilderNMS extends BlockTypeRegistryEntry.Builder, KeyAwareRegistryEntryNMS {

    /**
     * Sets the factory to use, and marks this builder as using NMS.
     */
    BlockTypeRegistryEntryBuilderNMS factoryNMS(Function<BlockBehaviour.Properties, Block> factory);

    /**
     * Replaces the NMS properties for the block.
     */
    BlockTypeRegistryEntryBuilderNMS propertiesNMS(BlockBehaviour.Properties properties);

    /**
     * Modifies the NMS properties for the block.
     */
    BlockTypeRegistryEntryBuilderNMS propertiesNMS(Consumer<BlockBehaviour.Properties> properties);

}
