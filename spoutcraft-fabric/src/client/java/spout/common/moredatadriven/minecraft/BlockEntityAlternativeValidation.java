package spout.common.moredatadriven.minecraft;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Provides alternative validation for the {@link BlockEntityType#isValid}.
 */
public final class BlockEntityAlternativeValidation {

    private BlockEntityAlternativeValidation() {
        throw new UnsupportedOperationException();
    }

    private static boolean updated = false;
    private static boolean skipValidation = false;

    private static final Map<BlockEntityType<?>, Set<Block>> alternativelyValid = new HashMap<>(1);

    public static boolean isAlternativelyValid(BlockEntityType<?> blockEntityType, BlockState state) {
        if (skipValidation) return true;
        if (!updated) {
            update();
        }
        Set<Block> blocks = alternativelyValid.get(blockEntityType);
        return blocks != null && blocks.contains(state.getBlock());
    }

    public static Collection<Block> getAlternativelyValidBlocks(BlockEntityType<?> blockEntityType) {
        if (!updated) {
            update();
        }
        return alternativelyValid.getOrDefault(blockEntityType, Collections.emptySet());
    }

    public static void clear() {
        alternativelyValid.clear();
        updated = false;
    }

    public static void update() {
        clear();
        BuiltInRegistries.BLOCK.forEach(block -> {
            if (!block.builtInRegistryHolder().key().identifier().getNamespace().equals(Identifier.DEFAULT_NAMESPACE) && block instanceof EntityBlock entityBlock) {
                skipValidation = true;
                BlockEntity entity = entityBlock.newBlockEntity(BlockPos.ZERO, block.defaultBlockState());
                skipValidation = false;
                if (entity != null) {
                    alternativelyValid.computeIfAbsent(entity.getType(), $ -> new HashSet<>(1)).add(block);
                }
            }
        });
        updated = true;
    }

}
