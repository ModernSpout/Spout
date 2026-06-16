package spout.clientview.packetmapping.blockstate.macro.processor;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceGateBlock;
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
 * A {@link BlockStateMappingMacroProcessor} for {@link BlockStateMappingMacroTypes#NETHER_PORTAL}.
 */
public class NetherPortalRequestProcessor extends FilledArrayResultProcessor<FromToBlockMacro, ArrayResultProcessor.RequestBasedResult> {

    public NetherPortalRequestProcessor(FromToBlockMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise kickoff) {
        BlockState[] fromStates = this.macro.getFromStates();
        IntList xResultIndices = new IntArrayList(1);
        IntList zResultIndices = new IntArrayList(1);
        for (int i = 0; i < fromStates.length; i++) {
            Optional<Direction.Axis> optionalAxis = fromStates[i].getOptionalValue(BlockStateProperties.HORIZONTAL_AXIS);
            if (optionalAxis.isPresent()) {
                Direction.Axis axis = optionalAxis.get();
                if (axis == Direction.Axis.X) {
                    xResultIndices.add(i);
                } else if (axis == Direction.Axis.Z) {
                    zResultIndices.add(i);
                }
            }
        }
        int[] xResultIndicesArray = xResultIndices.toIntArray();
        int[] zResultIndicesArray = zResultIndices.toIntArray();
        int[] xResultIndicesIndices = IntStream.range(0, xResultIndicesArray.length).toArray();
        int[] zResultIndicesIndices = IntStream.range(0, zResultIndicesArray.length).toArray();
        return kickoff
            .then(new AttemptToClaimStatesFillPromise(xResultIndicesArray, X_NETHER_PORTAL_PROXY_STATES::get, $ -> xResultIndicesIndices, false))
            .then(new AttemptToClaimStatesFillPromise(zResultIndicesArray, Z_NETHER_PORTAL_PROXY_STATES::get, $ -> zResultIndicesIndices, false))
            .then(CLAIM_FALLBACK_PROMISE_GETTER.get(this))
            .then(new BlockFallbackFillPromise(this.macro.fallbackBlock));
    }

    private static List<BlockState> getFenceGateProxyStates(Direction.Axis axis) {
        return StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false)
            .filter(block -> block instanceof FenceGateBlock)
            .flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
            .filter(state -> state.getValue(BlockStateProperties.OPEN))
            .filter(state -> !state.getValue(BlockStateProperties.IN_WALL))
            .filter(state -> state.getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis() == axis)
            .toList();
    }

    /**
     * A new {@link DynamicClaimableStates} instance,
     * for {@link BlockState}s that can be attempted to be claimed as x-axis nether portal proxies.
     */
    public static final DynamicClaimableStates X_NETHER_PORTAL_PROXY_STATES = SingletonBlockStateDynamicClaimableStates.forProxy(() -> getFenceGateProxyStates(Direction.Axis.Z));

    /**
     * A new {@link DynamicClaimableStates} instance,
     * for {@link BlockState}s that can be attempted to be claimed as z-axis nether portal proxies.
     */
    public static final DynamicClaimableStates Z_NETHER_PORTAL_PROXY_STATES = SingletonBlockStateDynamicClaimableStates.forProxy(() -> getFenceGateProxyStates(Direction.Axis.X));

    public static final FillPromiseGetter<FromToBlockMacro, RequestBasedResult> CLAIM_FALLBACK_PROMISE_GETTER = claimFallbackStatesForAllStatesAtOnceByBlock(
        List.of(Blocks.NETHER_PORTAL)
    );

}
