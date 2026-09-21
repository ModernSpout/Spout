package spout.clientview.packetmapping.blockstate.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.BuiltInAwarenessLevelRegistry;
import spout.clientview.packetmapping.blockstate.apply.BlockStateMappingStep;
import spout.clientview.packetmapping.blockstate.apply.DirectBlockStateMappingStep;
import spout.util.minecraft.blockstate.BlockStateStringConversion;
import spout.util.minecraft.resources.IdentifierUtil;
import java.util.List;

/**
 * An element of {@link BlockStateMappingRegistry#BLOCK_STATE_MAPPING}.
 */
public record BlockStateMapping(List<AwarenessLevel> awarenessLevels, List<BlockState> targets, BlockStateMappingStep operation) {

    public static final Codec<BlockStateMapping> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            // MapCodec.<AwarenessLevel>unit(null).codec()
            IdentifierUtil.byNameWithSpoutNamespaceAsDefaultCodec(BuiltInAwarenessLevelRegistry.AWARENESS_LEVEL)
                .listOf().fieldOf("awareness_levels").forGetter(BlockStateMapping::awarenessLevels),
            BlockStateStringConversion.CODEC
                .listOf().fieldOf("targets").forGetter(BlockStateMapping::targets),
            DirectBlockStateMappingStep.codec(BlockStateStringConversion.CODEC).fieldOf("operation").forGetter(mapping -> (DirectBlockStateMappingStep) mapping.operation)
        ).apply(instance, BlockStateMapping::new)
    );

}
