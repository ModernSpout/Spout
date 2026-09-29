package spout.clientview.model;

import com.mojang.serialization.Codec;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.model.awarenesslevel.BuiltInAwarenessLevelRegistry;
import spout.clientview.packetmapping.itemstack.apply.reverse.ItemStackMappingReverser;
import org.jspecify.annotations.Nullable;
import java.util.List;

/**
 * The base implementation of {@link ClientView}.
 *
 * <p>
 * Every instance of {@link ClientView} is also an instance of {@link ClientViewImpl}.
 * </p>
 */
public abstract class ClientViewImpl implements ClientView {

    public static final Codec<AwarenessLevel> AWARENESS_LEVEL_CODEC = BuiltInAwarenessLevelRegistry.AWARENESS_LEVEL.byNameCodec();;
    public static final Codec<List<AwarenessLevel>> AWARENESS_LEVEL_LIST_CODEC = Codec.list(AWARENESS_LEVEL_CODEC);

    /**
     * @return The {@link ItemStackMappingReverser} of this client,
     * or null if not available.
     *
     * <p>
     * The reverser (if present) instance stays the same during the entire connection session of a client.
     * </p>
     */
    public abstract @Nullable ItemStackMappingReverser getItemMappingReverser();

    public static ClientView getSimulatedForAwarenessLevel(AwarenessLevel awarenessLevel) {
        if (awarenessLevel == AwarenessLevels.VANILLA) {
            return SimulatedClientViewImpl.VANILLA_INSTANCE;
        } else if (awarenessLevel == AwarenessLevels.RESOURCE_PACK) {
            return SimulatedClientViewImpl.RESOURCE_PACK_INSTANCE;
        } else if (awarenessLevel == AwarenessLevels.CLIENT_MOD) {
            return SimulatedClientViewImpl.CLIENT_MOD_INSTANCE;
        }
        throw new IllegalArgumentException();
    }

}
