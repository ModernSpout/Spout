package spout.clientview.packetmapping.blockstate.apply.blocktransformer;

import net.minecraft.core.component.BlockTransformer;
import java.util.Collections;
import java.util.List;

/**
 * Filters {@link BlockTransformer.BlockTransformData}
 * on being able to be interpreted by a client.
 */
public final class BlockTransformerDataMapper {

    private BlockTransformerDataMapper() {
        throw new UnsupportedOperationException();
    }

    public static List<BlockTransformer.BlockTransformData> map(List<BlockTransformer.BlockTransformData> data) {
        // For now, we just return an empty list because the client doesn't use this data
        return Collections.emptyList();
    }

}
