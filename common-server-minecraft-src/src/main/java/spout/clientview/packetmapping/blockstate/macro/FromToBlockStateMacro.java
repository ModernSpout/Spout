package spout.clientview.packetmapping.blockstate.macro;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import java.util.List;
import java.util.function.Consumer;

/**
 * A {@link BlockStateMappingMacro} that maps from and to a block state.
 */
public class FromToBlockStateMacro extends FromToItemMacro implements FromToBlockStatesMacro {

    public final BlockState fromState;
    public final BlockState fallbackState;

    private BlockState @Nullable [] fromStates;
    private BlockState @Nullable [] fallbackStates;

    public FromToBlockStateMacro(
        BlockStateMappingMacroType type,
        boolean createFromToUsedStatesMappings,
        boolean createProxyToVisualDuplicateMappings,
        boolean createItemMappings,
        boolean createVanillaMappings,
        boolean createResourcePackBlockstatesEntries,
        @Nullable List<Consumer<UsedStates>> resultConsumers,
        @Nullable Item fromItem,
        @Nullable Item fallbackItem,
        BlockState fromState,
        BlockState fallbackState
    ) {
        super(
            type,
            createFromToUsedStatesMappings,
            createProxyToVisualDuplicateMappings,
            createItemMappings,
            createVanillaMappings,
            createResourcePackBlockstatesEntries,
            resultConsumers,
            fromItem,
            fallbackItem
        );
        this.fromState = fromState;
        this.fallbackState = fallbackState;
    }

    @Override
    protected @Nullable Block getBlockToInferFromItem() {
        return this.fromState.getBlock();
    }

    @Override
    protected @Nullable Block getBlockToInferFallbackItem() {
        return this.fallbackState.getBlock();
    }

    @Override
    public BlockState[] getFromStates() {
        if (this.fromStates == null) {
            this.fromStates = new BlockState[]{this.fromState};
        }
        return this.fromStates;
    }

    @Override
    public BlockState[] getFallbackStates() {
        if (this.fallbackStates == null) {
            this.fallbackStates = new BlockState[]{this.fallbackState};
        }
        return this.fallbackStates;
    }

}
