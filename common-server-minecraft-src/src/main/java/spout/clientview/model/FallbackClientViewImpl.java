package spout.clientview.model;

import net.minecraft.network.Connection;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.packetmapping.itemstack.apply.reverse.ItemStackMappingReverser;
import org.jspecify.annotations.Nullable;

/**
 * A fallback {@link ClientView} that is used for mappings when no view is known.
 */
public final class FallbackClientViewImpl extends ClientViewImpl {

    /**
     * A usable instance of {@link FallbackClientViewImpl}.
     */
    public static final FallbackClientViewImpl INSTANCE = new FallbackClientViewImpl();

    private FallbackClientViewImpl() {
        super();
    }

    @Override
    public AwarenessLevel getAwarenessLevel() {
        return AwarenessLevels.VANILLA;
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
