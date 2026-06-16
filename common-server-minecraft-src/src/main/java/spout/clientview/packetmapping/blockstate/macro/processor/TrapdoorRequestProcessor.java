package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockMacro;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMapping;
import spout.server.paper.impl.moredatadriven.minecraft.VanillaOnlyBlockRegistry;
import spout.server.paper.impl.packetmapping.block.automatic.RequestProcessor;

/**
 * A {@link RequestProcessor} for {@link BlockStateMappingMacroTypes#TRAPDOOR}.
 */
public class TrapdoorRequestProcessor extends FilledArrayResultProcessor<FromToBlockMacro, ArrayResultProcessor.RequestBasedResult> {

    public TrapdoorRequestProcessor(FromToBlockMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise kickoff) {
        return kickoff
            .then(PROMISE_GETTER.get(this))
            .then(CLAIM_FALLBACK_PROMISE_GETTER.get(this))
            .then(new BlockFallbackFillPromise(this.macro.fallbackBlock));
    }

    /**
     * A new {@link FillPromiseGetter} instance,
     * for {@link BlockState}s that can be attempted to be claimed as trapdoor proxies.
     */
    public static final FillPromiseGetter<FromToBlockMacro, RequestBasedResult> PROMISE_GETTER = claimProxyStatesForAllStatesAtOnceForBlockStatesByDynamicProperties(
        StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false).filter(block -> block instanceof TrapDoorBlock).toList(),
        BlockStateProperties.HORIZONTAL_FACING,
        BlockStateProperties.HALF,
        BlockStateProperties.OPEN,
        BlockStateProperties.WATERLOGGED
    );

    public static final FillPromiseGetter<FromToBlockMacro, RequestBasedResult> CLAIM_FALLBACK_PROMISE_GETTER = claimFallbackStatesForAllStatesAtOnceByBlock(
        Stream.concat(Stream.of(Blocks.OAK_TRAPDOOR), StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false).filter(block -> block instanceof TrapDoorBlock)).distinct().toList()
    );

}
