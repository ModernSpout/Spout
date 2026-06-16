package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.function.TriFunction;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockMacro;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMapping;
import spout.server.paper.impl.moredatadriven.minecraft.VanillaOnlyBlockRegistry;
import spout.server.paper.impl.packetmapping.block.automatic.BlockDynamicClaimableStates;
import spout.server.paper.impl.packetmapping.block.automatic.DynamicClaimableStates;
import spout.server.paper.impl.packetmapping.block.automatic.SortedClaimableStates;

/**
 * A {@link BlockStateMappingMacroProcessor} for {@link BlockStateMappingMacro}s
 * that are backed by a block.
 */
public abstract class StandardBlockTypeRequestProcessor extends FilledArrayResultProcessor<FromToBlockMacro, ArrayResultProcessor.RequestBasedResult> {

    public StandardBlockTypeRequestProcessor(FromToBlockMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<FromToBlockMacro, RequestBasedResult>.FillPromise kickoff) {
        return kickoff
            .then(this.getFallbackFillPromise())
            .then(new BlockFallbackFillPromise(this.macro.fallbackBlock));
    }

    protected abstract List<Block> getFallbackBlocks();

    protected Block getReferenceBlock() {
        return this.getFallbackBlocks().get(0);
    }

    protected FillPromise getFallbackFillPromise() {
        DynamicClaimableStates defaultDynamicClaimableStates = BlockDynamicClaimableStates.forFallback(this::getFallbackBlocks);
        DynamicClaimableStates preferredDynamicClaimableStates = BlockDynamicClaimableStates.forFallback(() -> List.of(this.macro.fallbackBlock));
        return this.attemptToClaimStatesFillPromiseForAllStatesAtOnceForBlock(state -> SortedClaimableStates.concat(preferredDynamicClaimableStates.get(state), defaultDynamicClaimableStates.get(state)), this.getReferenceBlock(),true);
    }

    public static TriFunction<FromToBlockMacro, Registry<BlockStateMappingMacro>, Registry<BlockStateMapping>, StandardBlockTypeRequestProcessor> withFallbackBlocks(Block... fallbackBlocks) {
        return withFallbackBlocks(Arrays.asList(fallbackBlocks));
    }

    public static TriFunction<FromToBlockMacro, Registry<BlockStateMappingMacro>, Registry<BlockStateMapping>, StandardBlockTypeRequestProcessor> withFallbackBlocks(List<Block> fallbackBlocks) {
        class WithGivenFallbackBlocks extends StandardBlockTypeRequestProcessor {

            public WithGivenFallbackBlocks(FromToBlockMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
                super(macro, sourceRegistry, targetRegistry);
            }

            @Override
            protected List<Block> getFallbackBlocks() {
                return fallbackBlocks;
            }

        }
        return WithGivenFallbackBlocks::new;
    }

    public static TriFunction<FromToBlockMacro, Registry<BlockStateMappingMacro>, Registry<BlockStateMapping>, StandardBlockTypeRequestProcessor> withFallbackBlocks(Block initialBlock, Class<? extends Block> type) {
        return withFallbackBlocks(Stream.concat(Stream.of(initialBlock), StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false).filter(type::isInstance)).distinct().toList());
    }

}
