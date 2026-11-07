package spout.clientview.packetmapping.blockstate.apply.blocktransformer;

import net.minecraft.core.Holder;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.jspecify.annotations.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Filters {@link BlockTransformer.BlockTransformData}
 * on being able to be interpreted by a client.
 */
public final class BlockTransformerDataMapper {

    private BlockTransformerDataMapper() {
        throw new UnsupportedOperationException();
    }

    public static List<BlockTransformer.BlockTransformData> map(List<BlockTransformer.BlockTransformData> data) {
        // For now, we just return a fake list because the client doesn't use this data
        // return List.of(new BlockTransformer.BlockTransformData(
        //     Holder.direct(BlockStateProvider.of(Blocks.COMMAND_BLOCK)),
        //     SoundEvents.AXE_STRIP,
        //     BlockTransformer.TransformParticle.NONE,
        //     Collections.emptyList(),
        //     Optional.empty(),
        //     BlockTransformer.DropStrategy.CLICKED_FACE,
        //     true,
        //     BlockTransformer.TransformType.SINGLE_BLOCK,
        //     false,
        //     0
        // ));
        // return data.stream().map(BlockTransformerDataMapper::map).filter(Objects::nonNull).toList();
        return data;
    }

    // public static BlockTransformer.@Nullable BlockTransformData map(BlockTransformer.BlockTransformData data) {
    //     return new BlockTransformer.BlockTransformData(
    //         data.sound(),
    //         particle,
    //     )
    // }

}
