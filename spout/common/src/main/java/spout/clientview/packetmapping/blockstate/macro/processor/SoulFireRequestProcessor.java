package spout.clientview.packetmapping.blockstate.macro.processor;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import spout.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockStateMacro;
import spout.clientview.packetmapping.blockstate.macro.processor.claimablestates.DynamicClaimableStates;
import spout.clientview.packetmapping.blockstate.macro.processor.claimablestates.SingletonBlockStateDynamicClaimableStates;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;

/**
 * A {@link BlockStateMappingMacroProcessor} for {@link BlockStateMappingMacroTypes#SOUL_FIRE}.
 */
public class SoulFireRequestProcessor extends StandardBlockStateRequestProcessor {

    public SoulFireRequestProcessor(FromToBlockStateMacro macro, Registry<BlockStateMappingMacro> sourceRegistry, Registry<BlockStateMapping> targetRegistry) {
        super(macro, sourceRegistry, targetRegistry);
    }

    @Override
    protected FilledArrayResultProcessor<FromToBlockStateMacro, RequestBasedResult>.FillPromise constructFillPromise(FilledArrayResultProcessor<FromToBlockStateMacro, RequestBasedResult>.FillPromise kickoff) {
        return super.constructFillPromise(
            kickoff
                .then(this.attemptToClaimStatesFillPromiseStateByState(SOUL_FIRE_PROXY_STATES::get, false))
        );
    }

    @Override
    protected FillPromise getSafeFallbackFillPromise() {
        return CLAIM_FALLBACK_PROMISE_GETTER.get(this);
    }

    /**
     * A new {@link DynamicClaimableStates} instance,
     * for {@link BlockState}s that can be attempted to be claimed as soul fire proxies.
     */
    public static final DynamicClaimableStates SOUL_FIRE_PROXY_STATES = SingletonBlockStateDynamicClaimableStates.forProxy(() -> {
        // Build the claimable states
        List<BlockState> states = new ArrayList<>();
        // Standing fire
        BlockState standingFire = Blocks.FIRE.defaultBlockState()
            .setValue(BlockStateProperties.UP, false)
            .setValue(BlockStateProperties.EAST, false)
            .setValue(BlockStateProperties.WEST, false)
            .setValue(BlockStateProperties.SOUTH, false)
            .setValue(BlockStateProperties.NORTH, false);
        for (int age : BlockStateProperties.AGE_15.getPossibleValues()) {
            states.add(standingFire.setValue(BlockStateProperties.AGE_15, age));
        }
        // Full fire
        BlockState fullFire = Blocks.FIRE.defaultBlockState()
            .setValue(BlockStateProperties.EAST, true)
            .setValue(BlockStateProperties.WEST, true)
            .setValue(BlockStateProperties.SOUTH, true)
            .setValue(BlockStateProperties.NORTH, true);
        for (boolean up : BlockStateProperties.UP.getPossibleValues()) {
            for (int age : BlockStateProperties.AGE_15.getPossibleValues()) {
                states.add(fullFire.setValue(BlockStateProperties.UP, up).setValue(BlockStateProperties.AGE_15, age));
            }
        }
        return states;
    });

    public static final List<BlockState> SAFE_FALLBACK_STATES = List.of(Blocks.SOUL_FIRE.defaultBlockState());

    public static final FillPromiseGetter<FromToBlockStateMacro, RequestBasedResult> CLAIM_FALLBACK_PROMISE_GETTER = claimFallbackStatesForAllStatesAtOnceByBlockState(
        SAFE_FALLBACK_STATES
    );

}
