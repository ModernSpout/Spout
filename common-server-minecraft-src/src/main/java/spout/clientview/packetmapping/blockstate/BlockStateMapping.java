package spout.clientview.packetmapping.blockstate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.BuiltInAwarenessLevelRegistry;
import spout.clientview.packetmapping.blockstate.apply.BlockStateMappingStep;
import spout.clientview.packetmapping.blockstate.apply.DirectBlockStateMappingStep;
import spout.clientview.packetmapping.blockstate.decodingcontext.BlockStateMappingDecodingContextBlock;
import spout.clientview.packetmapping.blockstate.registry.BlockStateMappingRegistryKey;
import spout.util.minecraft.blockstate.BlockStateStringConversion;
import spout.util.minecraft.resources.IdentifierUtil;
import java.util.List;
import java.util.Optional;

/**
 * An element of {@link BlockStateMappingRegistryKey#BLOCK_STATE_MAPPING}.
 */
public record BlockStateMapping(List<AwarenessLevel> awarenessLevels, List<BlockState> targets, BlockStateMappingStep operation) {

    public static final Codec<BlockStateMapping> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            IdentifierUtil.byNameWithSpoutNamespaceAsDefaultCodec(BuiltInAwarenessLevelRegistry.AWARENESS_LEVEL)
                .listOf().fieldOf("awareness_levels").forGetter(BlockStateMapping::awarenessLevels),
            BlockStateStringConversion.CODEC
                .listOf().optionalFieldOf("targets").xmap(optionalList -> optionalList.orElse(BlockStateMappingDecodingContextBlock.get().getStateDefinition().getPossibleStates()), Optional::of).forGetter(BlockStateMapping::targets),
            DirectBlockStateMappingStep.codec(BlockStateStringConversion.CODEC).fieldOf("operation").forGetter(mapping -> (DirectBlockStateMappingStep) mapping.operation)
        ).apply(instance, BlockStateMapping::new)
    );

}
