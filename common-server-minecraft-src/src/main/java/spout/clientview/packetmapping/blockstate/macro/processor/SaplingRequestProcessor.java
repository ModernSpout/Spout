package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockMacro;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMapping;
import spout.server.paper.impl.moredatadriven.minecraft.VanillaOnlyBlockRegistry;
import spout.server.paper.impl.packetmapping.block.automatic.DynamicClaimableStates;
import spout.server.paper.impl.packetmapping.block.automatic.SingletonBlockStateDynamicClaimableStates;

/**
 * A {@link BlockStateMappingMacroProcessor} for {@link BlockStateMappingMacroTypes#SAPLING}.
 */
public class SaplingRequestProcessor extends StandardBlockTypeRequestProcessor {

    public SaplingRequestProcessor(FromToBlockMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise kickoff) {
        return super.constructFillPromise(
            kickoff
                .then(this.attemptToClaimStatesFillPromiseStateByState(SAPLING_PROXY_STATES::get, false))
        );
    }

    @Override
    protected List<Block> getFallbackBlocks() {
        return SAFE_FALLBACK_BLOCKS;
    }

    /**
     * A new {@link DynamicClaimableStates} instance,
     * for {@link BlockState}s that can be attempted to be claimed as sapling proxies.
     */
    public static final DynamicClaimableStates SAPLING_PROXY_STATES = SingletonBlockStateDynamicClaimableStates.forProxy(() -> StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false)
        .filter(block -> block.getClass().equals(SaplingBlock.class))
        .flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
        .toList()
    );

    public static final List<Block> SAFE_FALLBACK_BLOCKS = Stream.concat(Stream.of(Blocks.OAK_SAPLING), StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false).filter(block -> block.getClass().equals(SaplingBlock.class))).distinct().toList();

}
