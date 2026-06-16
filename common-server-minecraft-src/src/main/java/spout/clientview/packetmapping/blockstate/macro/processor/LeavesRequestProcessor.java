package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.UntintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.LeavesMacro;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMapping;
import spout.server.paper.impl.moredatadriven.minecraft.VanillaOnlyBlockRegistry;

/**
 * A {@link BlockStateMappingMacroProcessor} for {@link BlockStateMappingMacroTypes#LEAVES}.
 */
public class LeavesRequestProcessor extends FilledArrayResultProcessor<LeavesMacro, ArrayResultProcessor.RequestBasedResult> {

    public LeavesRequestProcessor(LeavesMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<LeavesMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<LeavesMacro, RequestBasedResult>.FillPromise kickoff) {
        return kickoff
            .then(this.macro.getTinted() ? TINTED_CLAIM_PROXY_PROMISE_GETTER.get(this) : UNTINTED_CLAIM_PROXY_PROMISE_GETTER.get(this))
            .then(this.macro.getTinted() ? TINTED_CLAIM_FALLBACK_PROMISE_GETTER.get(this) : UNTINTED_CLAIM_FALLBACK_PROMISE_GETTER.get(this))
            .then(new BlockFallbackFillPromise(this.macro.fallbackBlock));
    }

    /**
     * A new {@link FillPromiseGetter} instance,
     * for {@link BlockState}s that can be attempted to be claimed as tinted leaves proxies.
     */
    public static final FillPromiseGetter<LeavesMacro, RequestBasedResult> TINTED_CLAIM_PROXY_PROMISE_GETTER = claimProxyStatesForAllStatesAtOnceForBlockStatesByDynamicProperties(
        StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false).filter(block -> block instanceof TintedParticleLeavesBlock).toList(),
        BlockStateProperties.WATERLOGGED
    );

    /**
     * A new {@link FillPromiseGetter} instance,
     * for {@link BlockState}s that can be attempted to be claimed as untinted leaves proxies.
     */
    public static final FillPromiseGetter<LeavesMacro, RequestBasedResult> UNTINTED_CLAIM_PROXY_PROMISE_GETTER = claimProxyStatesForAllStatesAtOnceForBlockStatesByDynamicProperties(
        StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false).filter(block -> block instanceof UntintedParticleLeavesBlock).toList(),
        BlockStateProperties.WATERLOGGED
    );

    public static final FillPromiseGetter<LeavesMacro, RequestBasedResult> TINTED_CLAIM_FALLBACK_PROMISE_GETTER = claimFallbackStatesForAllStatesAtOnceByBlock(
        Stream.concat(Stream.of(Blocks.OAK_LEAVES), StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false).filter(block -> block instanceof TintedParticleLeavesBlock)).distinct().toList()
    );

    public static final FillPromiseGetter<LeavesMacro, RequestBasedResult> UNTINTED_CLAIM_FALLBACK_PROMISE_GETTER = claimFallbackStatesForAllStatesAtOnceByBlock(
        Stream.concat(Stream.of(Blocks.PALE_OAK_LEAVES), StreamSupport.stream(VanillaOnlyBlockRegistry.get().spliterator(), false).filter(block -> block instanceof UntintedParticleLeavesBlock)).distinct().toList()
    );

}
