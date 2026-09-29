package spout.gamecontent.datadriven.block;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import spout.gamecontent.datadriven.blockentity.BlockEntityAlternativeValidation;
import spout.gamecontent.datadriven.common.registry.temporarymodification.TemporaryRegistryModifier;

import java.util.List;
import java.util.function.Supplier;

/**
 * The {@link TemporaryRegistryModifier} for {@link BuiltInRegistries#BLOCK}.
 */
public final class TemporaryBlockRegistryModifier extends TemporaryRegistryModifier<Block, DefaultedMappedRegistry<Block>> {

    public TemporaryBlockRegistryModifier() {
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
