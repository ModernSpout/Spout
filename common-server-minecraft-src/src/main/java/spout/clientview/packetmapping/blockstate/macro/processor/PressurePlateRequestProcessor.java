package spout.clientview.packetmapping.blockstate.macro.processor;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.WeightedPressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockMacro;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMapping;
import spout.server.paper.impl.moredatadriven.minecraft.VanillaOnlyBlockRegistry;
import spout.server.paper.impl.packetmapping.block.automatic.DynamicClaimableStates;
import spout.server.paper.impl.packetmapping.block.automatic.SingletonBlockStateDynamicClaimableStates;

/**
 * A {@link BlockStateMappingMacroProcessor} for {@link BlockStateMappingMacroTypes#PRESSURE_PLATE}.
 */
public class PressurePlateRequestProcessor extends FilledArrayResultProcessor<FromToBlockMacro, ArrayResultProcessor.RequestBasedResult> {

    public PressurePlateRequestProcessor(FromToBlockMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise kickoff) {
        BlockState[] fromStates = this.macro.getFromStates();
        IntList downResultIndices = new IntArrayList(fromStates.length - 1);
        IntList upResultIndices = new IntArrayList(1);
        for (int i = 0; i < fromStates.length; i++) {
            if (fromStates[i].getValueOrElse(BlockStateProperties.POWER, 0) != 0 || fromStates[i].getValueOrElse(BlockStateProperties.POWERED, false)) {
                downResultIndices.add(i);
            } else {
                upResultIndices.add(i);
            }
        }
        int[] downResultIndicesArray = downResultIndices.toIntArray();
        int[] upResultIndicesArray = upResultIndices.toIntArray();
        int[] downResultIndicesIndices = IntStream.range(0, downResultIndicesArray.length).toArray();
        int[] upResultIndicesIndices = IntStream.range(0, upResultIndicesArray.length).toArray();
        return kickoff
            .then(new AttemptToClaimStatesFillPromise(downResultIndicesArray, PRESSURE_PLATE_PROXY_STATES::get, $ -> downResultIndicesIndices, false))
            .then(new AttemptToClaimStatesFillPromise(upResultIndicesArray, PRESSURE_PLATE_PROXY_STATES::get, $ -> upResultIndicesIndices, false))
            .then(new AttemptToClaimStatesFillPromise(downResultIndicesArray, POWERED_PRESSURE_PLATE_FALLBACK_STATES::get, $ -> downResultIndicesIndices, true))
            .then(new AttemptToClaimStatesFillPromise(upResultIndicesArray, UNPOWERED_PRESSURE_PLATE_FALLBACK_STATES::get, $ -> upResultIndicesIndices, true))
            .then(new BlockFallbackFillPromise(this.macro.fallbackBlock));
    }

    /**
     * A new {@link DynamicClaimableStates} instance,
     * for {@link BlockState}s that can be attempted to be claimed as powered pressure plate proxies.
     */
    public static final DynamicClaimableStates PRESSURE_PLATE_PROXY_STATES = SingletonBlockStateDynamicClaimableStates.forProxy(() -> StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false)
        .filter(block -> block instanceof WeightedPressurePlateBlock)
        .flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
        .filter(state -> state.getValue(BlockStateProperties.POWER) != 0)
        .toList());

    public static final DynamicClaimableStates POWERED_PRESSURE_PLATE_FALLBACK_STATES = SingletonBlockStateDynamicClaimableStates.forProxy(() -> Stream.concat(
        StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false)
            .filter(block -> block instanceof WeightedPressurePlateBlock)
            .flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
            .filter(state -> state.getValue(BlockStateProperties.POWER) != 0),
        StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false)
            .filter(block -> block instanceof PressurePlateBlock)
            .flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
            .filter(state -> state.getValue(BlockStateProperties.POWERED))
    ).toList());

    public static final DynamicClaimableStates UNPOWERED_PRESSURE_PLATE_FALLBACK_STATES = SingletonBlockStateDynamicClaimableStates.forProxy(() -> Stream.concat(
        StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false)
            .filter(block -> block instanceof WeightedPressurePlateBlock)
            .flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
            .filter(state -> state.getValue(BlockStateProperties.POWER) == 0),
        StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false)
            .filter(block -> block instanceof PressurePlateBlock)
            .flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
            .filter(state -> !state.getValue(BlockStateProperties.POWERED))
    ).toList());

}
