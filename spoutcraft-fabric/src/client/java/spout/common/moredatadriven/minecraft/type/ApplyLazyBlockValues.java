package spout.common.moredatadriven.minecraft.type;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.CoralFanBlock;
import net.minecraft.world.level.block.CoralPlantBlock;
import net.minecraft.world.level.block.CoralWallFanBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import spout.client.fabric.moredatadriven.minecraft.type.mixin.LazyBaseStateStairBlockAccessor;
import spout.client.fabric.moredatadriven.minecraft.type.mixin.LazyCandleBlockCandleCakeBlockAccessor;
import spout.client.fabric.moredatadriven.minecraft.type.mixin.LazyConcreteConcretePowderBlockAccessor;
import spout.client.fabric.moredatadriven.minecraft.type.mixin.LazyDeadBlockCoralBlockAccessor;
import spout.client.fabric.moredatadriven.minecraft.type.mixin.LazyDeadBlockCoralFanBlockAccessor;
import spout.client.fabric.moredatadriven.minecraft.type.mixin.LazyDeadBlockCoralPlantBlockAccessor;
import spout.client.fabric.moredatadriven.minecraft.type.mixin.LazyDeadBlockCoralWallFanBlockAccessor;
import spout.client.fabric.moredatadriven.minecraft.type.mixin.LazyHostBlockInfestedBlockAccessor;
import spout.client.fabric.moredatadriven.minecraft.type.mixin.LazyPlantChorusFlowerBlockAccessor;
import spout.client.fabric.moredatadriven.minecraft.type.mixin.LazyPottedFlowerPotBlockAccessor;
import spout.client.fabric.moredatadriven.minecraft.type.mixin.LazyTurnsIntoBrushableBlockAccessor;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * A utility class that applies lazily applied block values,
 * such as the base state of stairs.
 */
public final class ApplyLazyBlockValues {

    private ApplyLazyBlockValues() {
        throw new UnsupportedOperationException();
    }

    public static void apply(Stream<Block> blocks) {
        blocks.forEach(block -> {
            // Set turnsInto for BrushableBlock
            if (block instanceof BrushableBlock castBlock) {
                Identifier temporaryValue = TemporaryValuesForLazyValues.pollBlockIdentifier(castBlock);
                if (temporaryValue != null) {
                    Block lazyValue = Objects.requireNonNull(BuiltInRegistries.BLOCK.getValue(temporaryValue));
                    LazyTurnsIntoBrushableBlockAccessor accessor = (LazyTurnsIntoBrushableBlockAccessor) castBlock;
                    accessor.setTurnsInto(lazyValue);
                }
            }
            // Set candleBlock for CandleCakeBlock
            if (block instanceof CandleCakeBlock castBlock) {
                Identifier temporaryValue = TemporaryValuesForLazyValues.pollBlockIdentifier(castBlock);
                if (temporaryValue != null) {
                    CandleBlock lazyValue = (CandleBlock) Objects.requireNonNull(BuiltInRegistries.BLOCK.getValue(temporaryValue));
                    LazyCandleBlockCandleCakeBlockAccessor.getByCandle().put(lazyValue, castBlock);
                    LazyCandleBlockCandleCakeBlockAccessor accessor = (LazyCandleBlockCandleCakeBlockAccessor) castBlock;
                    accessor.setCandleBlock(lazyValue);
                }
            }
            // Set plant for ChorusFlowerBlock
            if (block instanceof ChorusFlowerBlock castBlock) {
                Identifier temporaryValue = TemporaryValuesForLazyValues.pollBlockIdentifier(castBlock);
                if (temporaryValue != null) {
                    Block lazyValue = Objects.requireNonNull(BuiltInRegistries.BLOCK.getValue(temporaryValue));
                    LazyPlantChorusFlowerBlockAccessor accessor = (LazyPlantChorusFlowerBlockAccessor) castBlock;
                    accessor.setPlant(lazyValue);
                }
            }
            // Set concrete for ConcretePowderBlock
            if (block instanceof ConcretePowderBlock castBlock) {
                Identifier temporaryValue = TemporaryValuesForLazyValues.pollBlockIdentifier(castBlock);
                if (temporaryValue != null) {
                    Block lazyValue = Objects.requireNonNull(BuiltInRegistries.BLOCK.getValue(temporaryValue));
                    LazyConcreteConcretePowderBlockAccessor accessor = (LazyConcreteConcretePowderBlockAccessor) castBlock;
                    accessor.setConcrete(lazyValue);
                }
            }
            // Set deadBlock for CoralBlock
            if (block instanceof CoralBlock castBlock) {
                Identifier temporaryValue = TemporaryValuesForLazyValues.pollBlockIdentifier(castBlock);
                if (temporaryValue != null) {
                    Block lazyValue = Objects.requireNonNull(BuiltInRegistries.BLOCK.getValue(temporaryValue));
                    LazyDeadBlockCoralBlockAccessor accessor = (LazyDeadBlockCoralBlockAccessor) castBlock;
                    accessor.setDeadBlock(lazyValue);
                }
            }
            // Set deadBlock for CoralFanBlock
            if (block instanceof CoralFanBlock castBlock) {
                Identifier temporaryValue = TemporaryValuesForLazyValues.pollBlockIdentifier(castBlock);
                if (temporaryValue != null) {
                    Block lazyValue = Objects.requireNonNull(BuiltInRegistries.BLOCK.getValue(temporaryValue));
                    LazyDeadBlockCoralFanBlockAccessor accessor = (LazyDeadBlockCoralFanBlockAccessor) castBlock;
                    accessor.setDeadBlock(lazyValue);
                }
            }
            // Set deadBlock for CoralPlantBlock
            if (block instanceof CoralPlantBlock castBlock) {
                Identifier temporaryValue = TemporaryValuesForLazyValues.pollBlockIdentifier(castBlock);
                if (temporaryValue != null) {
                    Block lazyValue = Objects.requireNonNull(BuiltInRegistries.BLOCK.getValue(temporaryValue));
                    LazyDeadBlockCoralPlantBlockAccessor accessor = (LazyDeadBlockCoralPlantBlockAccessor) castBlock;
                    accessor.setDeadBlock(lazyValue);
                }
            }
            // Set deadBlock for CoralWallFanBlock
            if (block instanceof CoralWallFanBlock castBlock) {
                Identifier temporaryValue = TemporaryValuesForLazyValues.pollBlockIdentifier(castBlock);
                if (temporaryValue != null) {
                    Block lazyValue = Objects.requireNonNull(BuiltInRegistries.BLOCK.getValue(temporaryValue));
                    LazyDeadBlockCoralWallFanBlockAccessor accessor = (LazyDeadBlockCoralWallFanBlockAccessor) castBlock;
                    accessor.setDeadBlock(lazyValue);
                }
            }
            // Set potted for FlowerPotBlock
            if (block instanceof FlowerPotBlock castBlock) {
                Identifier temporaryValue = TemporaryValuesForLazyValues.pollBlockIdentifier(castBlock);
                if (temporaryValue != null) {
                    Block lazyValue = Objects.requireNonNull(BuiltInRegistries.BLOCK.getValue(temporaryValue));
                    LazyPottedFlowerPotBlockAccessor.getPottedByContent().put(lazyValue, castBlock);
                    LazyPottedFlowerPotBlockAccessor accessor = (LazyPottedFlowerPotBlockAccessor) castBlock;
                    accessor.setPotted(lazyValue);
                }
            }
            // Set hostBlock for InfestedBlock
            if (block instanceof InfestedBlock castBlock) {
                Identifier temporaryValue = TemporaryValuesForLazyValues.pollBlockIdentifier(castBlock);
                if (temporaryValue != null) {
                    Block lazyValue = Objects.requireNonNull(BuiltInRegistries.BLOCK.getValue(temporaryValue));
                    LazyHostBlockInfestedBlockAccessor.getBlockByHostBlock().put(lazyValue, castBlock);
                    LazyHostBlockInfestedBlockAccessor accessor = (LazyHostBlockInfestedBlockAccessor) castBlock;
                    accessor.setHostBlock(lazyValue);
                }
            }
            // Set baseState for StairBlock
            if (block instanceof StairBlock castBlock) {
                String temporaryValue = TemporaryValuesForLazyValues.pollBlockString(castBlock);
                if (temporaryValue != null) {
                    BlockState lazyValue = BlockStateStringConversion.blockStateFromString(temporaryValue);
                    LazyBaseStateStairBlockAccessor accessor = (LazyBaseStateStairBlockAccessor) castBlock;
                    accessor.setBaseState(lazyValue);
                    accessor.setBase(lazyValue.getBlock());
                }
            }
        });
    }

}
