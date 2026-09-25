package spout.api.clientview.packetmapping.blockstate.registry;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import java.util.List;

/**
 * NMS extension for {@link BlockStateMapping}.
 *
 * <p>
 * Every instance of {@link BlockStateMapping}
 * is an instance of {@link BlockStateMappingNMS}.
 * </p>
 *
 * <p>
 * Always an instance of either {@link DirectBlockStateMappingNMS} or {@link FunctionBlockStateMappingNMS}.
 * </p>
 */
public interface BlockStateMappingNMS extends BlockStateMapping {

    /**
     * @see #getFrom()
     */
    List<BlockState> getFromNMS();

}
