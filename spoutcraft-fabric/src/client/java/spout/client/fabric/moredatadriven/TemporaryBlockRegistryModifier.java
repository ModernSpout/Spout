package spout.client.fabric.moredatadriven;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import spout.common.moredatadriven.minecraft.BlockEntityAlternativeValidation;
import spout.common.moredatadriven.minecraft.type.ApplyLazyBlockValues;
import java.util.List;

/**
 * The {@link TemporaryRegistryModifier} for {@link BuiltInRegistries#BLOCK}.
 */
public final class TemporaryBlockRegistryModifier extends TemporaryRegistryModifier<Block, DefaultedMappedRegistry<Block>> {

    TemporaryBlockRegistryModifier() {
        super((DefaultedMappedRegistry<Block>) BuiltInRegistries.BLOCK);
    }

    @Override
    public void add(List<Pair<ResourceKey<Block>, Block>> resources) {
        super.add(resources);
        if (!resources.isEmpty()) {
            // Apply lazy values
            ApplyLazyBlockValues.apply(resources.stream().map(Pair::right));
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
