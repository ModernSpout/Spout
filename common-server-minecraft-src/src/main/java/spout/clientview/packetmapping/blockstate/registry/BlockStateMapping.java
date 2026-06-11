package spout.clientview.packetmapping.blockstate.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.model.awarenesslevel.BuiltInAwarenessLevelRegistry;
import spout.clientview.packetmapping.blockstate.apply.BlockStateMappingHandle;
import spout.gamecontent.datadriven.block.subtypes.BlockStateStringConversion;
import spout.util.mapping.handle.DirectMappingStep;
import spout.util.mapping.handle.MappingStep;
import spout.util.minecraft.resources.IdentifierUtil;
import java.util.List;

/**
 * An element of {@link BlockStateMappingRegistry#BLOCK_STATE_MAPPING}.
 */
public record BlockStateMapping(List<AwarenessLevel> awarenessLevels, List<BlockState> targets, MappingStep<BlockStateMappingHandle> operation) {

    public static final Codec<BlockStateMapping> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            // MapCodec.<AwarenessLevel>unit(null).codec()
            IdentifierUtil.byNameWithSpoutNamespaceAsDefaultCodec(BuiltInAwarenessLevelRegistry.AWARENESS_LEVEL)
                .listOf().fieldOf("awareness_levels").forGetter(BlockStateMapping::awarenessLevels),
            BlockStateStringConversion.CODEC
                .listOf().fieldOf("targets").forGetter(BlockStateMapping::targets),
            DirectMappingStep.<BlockState, BlockStateMappingHandle>codec(BlockStateStringConversion.CODEC).fieldOf("operation").forGetter(mapping -> (DirectMappingStep<BlockState, BlockStateMappingHandle>) mapping.operation)
        ).apply(instance, BlockStateMapping::new)
    );

}
