package spout.client.fabric.moredatadriven;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import spout.common.moredatadriven.minecraft.blockentity.BlockEntityAlternativeValidation;
import java.util.List;
import java.util.function.Supplier;

/**
 * The {@link TemporaryRegistryModifier} for {@link BuiltInRegistries#BLOCK}.
 */
public final class TemporaryBlockRegistryModifier extends TemporaryRegistryModifier<Block, DefaultedMappedRegistry<Block>> {

    TemporaryBlockRegistryModifier() {
        super((DefaultedMappedRegistry<Block>) BuiltInRegistries.BLOCK);
    }

    @Override
    public void add(List<Pair<ResourceKey<Block>, Supplier<Block>>> resources) {
        super.add(resources);
        if (!resources.isEmpty()) {
            // Update alternatively valid block entities
            BlockEntityAlternativeValidation.update();
        }
    }

    @Override
    public void remove() {
        BlockEntityAlternativeValidation.clear();
        super.remove();
    }

}
