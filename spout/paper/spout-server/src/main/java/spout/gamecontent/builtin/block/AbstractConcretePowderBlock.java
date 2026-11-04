package spout.gamecontent.builtin.block;

import com.mojang.datafixers.util.Function8;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A common base for combinations of {@link ConcretePowderBlock} and other classes.
 */
public interface AbstractConcretePowderBlock extends AbstractFallingBlock {

    default void onLandConcretePowder(final Level level, final BlockPos pos, final BlockState state, final BlockState replacedBlock, final FallingBlockEntity entity) {
        if (ConcretePowderBlock.shouldSolidify(level, pos, replacedBlock)) {
            org.bukkit.craftbukkit.event.CraftEventFactory.handleBlockFormEvent(level, pos, this.getConcrete().defaultBlockState().withPropertiesOf(state), Block.UPDATE_ALL); // CraftBukkit
        }
    }

    default BlockState getStateForPlacementConcretePowder(final BlockPlaceContext context, final Function<BlockPlaceContext, BlockState> superMethod) {
        Level level = context.getLevel(); // Paper
        BlockPos pos = context.getClickedPos();
        BlockState replacedBlock = level.getBlockState(pos);
        // CraftBukkit start
        if (ConcretePowderBlock.shouldSolidify(level, pos, replacedBlock)) {
            // TODO: An event factory call for methods like this
            org.bukkit.craftbukkit.block.CraftBlockState snapshot = org.bukkit.craftbukkit.block.CraftBlockStates.getBlockState(level, pos);
            snapshot.setBlock(this.getConcrete().getStateForPlacement(context));

            org.bukkit.event.block.BlockFormEvent event = new org.bukkit.event.block.BlockFormEvent(snapshot.getBlock(), snapshot);
            event.callEvent();

            if (!event.isCancelled()) {
                return snapshot.getHandle();
            }
        }
        // CraftBukkit end
        return superMethod.apply(context);
    }

    default BlockState updateShapeConcretePowder(
        final BlockState state,
        final LevelReader level,
        final ScheduledTickAccess ticks,
        final BlockPos pos,
        final Direction directionToNeighbour,
        final BlockPos neighbourPos,
        final BlockState neighbourState,
        final RandomSource random,
        final Function8<BlockState, LevelReader, ScheduledTickAccess, BlockPos, Direction, BlockPos, BlockState, RandomSource, BlockState> superMethod
    ) {
        // CraftBukkit start
        if (ConcretePowderBlock.touchesLiquid(level, pos)) {
            // Suppress during worldgen
            if (!(level instanceof Level world1)) {
                return this.getConcrete().defaultBlockState().withPropertiesOf(state);
            }
            org.bukkit.craftbukkit.block.CraftBlockState snapshot = org.bukkit.craftbukkit.block.CraftBlockStates.getBlockState(world1, pos);
            snapshot.setBlock(this.getConcrete().defaultBlockState().withPropertiesOf(state));

            org.bukkit.event.block.BlockFormEvent event = new org.bukkit.event.block.BlockFormEvent(snapshot.getBlock(), snapshot);
            world1.getCraftServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                return snapshot.getHandle();
            }
        }

        return superMethod.apply(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
        // CraftBukkit end
    }

    @Override
    default int getDustColor(final BlockState blockState, final BlockGetter level, final BlockPos pos) {
        return blockState.getMapColor(level, pos).col;
    }

    Block getConcrete();

}
