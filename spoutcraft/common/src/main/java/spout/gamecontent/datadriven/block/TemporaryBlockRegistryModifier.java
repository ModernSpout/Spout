package spout.gamecontent.datadriven.block;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LilyPadBlock;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import spout.gamecontent.builtin.block.LongHangingPlantBlock;
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
            // Add block colors for tinted leaves
            if (this.resourcesAdded != null) {
                this.resourcesAdded.forEach(pair -> {
                    if (pair.right() instanceof TintedParticleLeavesBlock tintedLeaves) {
                        Minecraft.getInstance().getBlockColors().register(List.of(BlockTintSources.foliage()), tintedLeaves);
                    } else if (pair.right() instanceof LilyPadBlock lilyPad) {
                        Minecraft.getInstance().getBlockColors().register(List.of(BlockTintSources.constant(BlockColors.LILY_PAD_DEFAULT, BlockColors.LILY_PAD_IN_WORLD)), lilyPad);
                    } else if (pair.right() instanceof LongHangingPlantBlock longHangingPlant && longHangingPlant.requiresTint) {
                        Minecraft.getInstance().getBlockColors().register(List.of(BlockTintSources.foliage()), longHangingPlant);
                    }
                });
            }
        }
    }

    @Override
    public void remove() {
        // Clear alternatively valid block entities
        BlockEntityAlternativeValidation.clear();
        // Remove block colors
        if (this.resourcesAdded != null) {
            this.resourcesAdded.forEach(pair ->
                ((AllowUnregisteringBlockColorsDecorator) Minecraft.getInstance().getBlockColors()).unregister(pair.right())
            );
        }
        super.remove();
    }

}
