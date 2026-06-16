package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;
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

/**
 * A {@link BlockStateMappingMacroProcessor} for {@link BlockStateMappingMacroTypes#FENCE_GATE}.
 */
public class FenceGateRequestProcessor extends FilledArrayResultProcessor<FromToBlockMacro, ArrayResultProcessor.RequestBasedResult> {

    public FenceGateRequestProcessor(FromToBlockMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise kickoff) {
        return kickoff
            .then(CLAIM_PROXY_PROMISE_GETTER.get(this))
            // TODO try to claim duplicate open states
            .then(CLAIM_FALLBACK_PROMISE_GETTER.get(this))
            .then(new BlockFallbackFillPromise(this.macro.fallbackBlock));
    }

    /**
     * A new {@link FillPromiseGetter} instance,
     * for {@link BlockState}s that can be attempted to be claimed as fence gate proxies.
     */
    public static final FillPromiseGetter<FromToBlockMacro, RequestBasedResult> CLAIM_PROXY_PROMISE_GETTER = claimProxyStatesForAllStatesAtOnceForBlockStatesByDynamicProperties(
        StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false).filter(block -> block instanceof FenceGateBlock).toList(),
        BlockStateProperties.HORIZONTAL_FACING,
        BlockStateProperties.IN_WALL,
        BlockStateProperties.OPEN
    );

    public static final FillPromiseGetter<FromToBlockMacro, RequestBasedResult> CLAIM_FALLBACK_PROMISE_GETTER = claimFallbackStatesForAllStatesAtOnceByBlock(
        Stream.concat(Stream.of(Blocks.OAK_FENCE_GATE), StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false).filter(block -> block instanceof FenceGateBlock)).distinct().toList()
    );

}
