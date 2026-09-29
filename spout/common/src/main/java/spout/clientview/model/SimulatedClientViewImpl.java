package spout.clientview.model;

import net.minecraft.network.Connection;
import org.jspecify.annotations.Nullable;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.packetmapping.itemstack.apply.reverse.ItemStackMappingReverser;

/**
 * A fake {@link ClientView} that is used to simulate mappings
 * outside a context for a specific client.
 */
public final class SimulatedClientViewImpl extends ClientViewImpl {

    private final AwarenessLevel awarenessLevel;

    public static final SimulatedClientViewImpl VANILLA_INSTANCE = new SimulatedClientViewImpl(AwarenessLevels.VANILLA);
    public static final SimulatedClientViewImpl RESOURCE_PACK_INSTANCE = new SimulatedClientViewImpl(AwarenessLevels.RESOURCE_PACK);
    public static final SimulatedClientViewImpl CLIENT_MOD_INSTANCE = new SimulatedClientViewImpl(AwarenessLevels.CLIENT_MOD);

    private SimulatedClientViewImpl(AwarenessLevel awarenessLevel) {
        super();
        this.awarenessLevel = awarenessLevel;
    }

    @Override
    public AwarenessLevel getAwarenessLevel() {
        return this.awarenessLevel;
    }

    @Override
    public @Nullable Connection getConnection() {
        return null;
    }

    @Override
    public @Nullable ItemStackMappingReverser getItemMappingReverser() {
        return null;
    }

}
