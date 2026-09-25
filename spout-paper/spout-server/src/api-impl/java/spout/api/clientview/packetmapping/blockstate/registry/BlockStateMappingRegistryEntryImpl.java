package spout.api.clientview.packetmapping.blockstate.registry;

import io.papermc.paper.registry.PaperRegistryBuilder;
import io.papermc.paper.registry.data.util.Conversions;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jspecify.annotations.Nullable;
import spout.api.clientview.model.ClientView;
import spout.api.clientview.model.CraftAwarenessLevel;
import spout.api.clientview.packetmapping.blockstate.handle.BlockStateMappingHandle;
import spout.api.clientview.packetmapping.blockstate.handle.BlockStateMappingHandleImpl;
import spout.api.clientview.packetmapping.blockstate.handle.BlockStateMappingHandleNMS;
import spout.api.clientview.packetmapping.blockstate.handle.BlockStateMappingHandleNMSImpl;
import spout.clientview.packetmapping.blockstate.apply.BlockStateMappingStep;
import spout.clientview.packetmapping.blockstate.apply.DirectBlockStateMappingStep;
import spout.clientview.packetmapping.blockstate.apply.FunctionBlockStateMappingStep;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * The implementation for {@link BlockStateMappingRegistryEntry}
 * and {@link BlockStateMappingRegistryEntryNMS}.
 */
public class BlockStateMappingRegistryEntryImpl implements BlockStateMappingRegistryEntryNMS, BlockStateMappingRegistryEntryNMS.Builder {

    protected @Nullable ArrayList<ClientView.AwarenessLevel> awarenessLevels;
    protected @Nullable ArrayList<BlockData> from;
    protected @Nullable BlockData to;
    protected @Nullable Consumer<BlockStateMappingHandle> toFunction;
    protected @Nullable Consumer<BlockStateMappingHandleNMS> toFunctionNMS;
    protected boolean toFunctionRequiresCoordinates;

    public BlockStateMappingRegistryEntryImpl(
        final Conversions ignoredConversions,
        final spout.clientview.packetmapping.blockstate.registry.BlockStateMapping internal
    ) {
        if (internal == null) return;

        this.awarenessLevels = new ArrayList<>(internal.awarenessLevels().stream().map(CraftAwarenessLevel::toBukkit).toList());
        this.from = new ArrayList<>(internal.targets().stream().map(BlockBehaviour.BlockStateBase::asBlockData).toList());
    }

    @Override
    public @Nullable List<? extends ClientView.AwarenessLevel> getAwarenessLevels() {
        return this.awarenessLevels == null ? null : Collections.unmodifiableList(this.awarenessLevels);
    }

    @Override
    public void setAwarenessLevels(Collection<ClientView.AwarenessLevel> awarenessLevels) {
        this.awarenessLevels = new ArrayList<>(awarenessLevels);
    }

    @Override
    public void addAwarenessLevel(ClientView.AwarenessLevel awarenessLevel) {
        if (this.awarenessLevels == null) {
            this.awarenessLevels = new ArrayList<>(1);
        }
        this.awarenessLevels.add(awarenessLevel);
    }

    @Override
    public @Nullable List<BlockData> getFrom() {
        return this.from == null ? null : Collections.unmodifiableList(this.from);
    }

    @Override
    public void setFrom(Collection<? extends BlockData> from) {
        this.from = new ArrayList<>(from);
    }

    @Override
    public void addFrom(BlockData from) {
        if (this.from == null) {
            this.from = new ArrayList<>(1);
        }
        this.from.add(from);
    }

    @Override
    public @Nullable BlockData getTo() {
        return this.to;
    }

    @Override
    public void setTo(BlockData to) {
        this.to = to;
        this.toFunction = null;
        this.toFunctionNMS = null;
    }

    @Override
    public void setToFunction(Consumer<BlockStateMappingHandle> function, boolean requiresCoordinates) {
        this.to = null;
        this.toFunction = function;
        this.toFunctionNMS = null;
        this.toFunctionRequiresCoordinates = requiresCoordinates;
    }

    @Override
    public void setToFunctionNMS(Consumer<BlockStateMappingHandleNMS> function, boolean requiresCoordinates) {
        this.to = null;
        this.toFunction = null;
        this.toFunctionNMS = function;
        this.toFunctionRequiresCoordinates = requiresCoordinates;
    }

    /**
     * The implementation for {@link BlockStateMappingRegistryEntry.Builder}
     * and {@link BlockStateMappingRegistryEntryNMS.Builder}.
     */
    public static final class Builder extends BlockStateMappingRegistryEntryImpl implements BlockStateMappingRegistryEntryNMS.Builder,
        PaperRegistryBuilder<spout.clientview.packetmapping.blockstate.registry.BlockStateMapping, BlockStateMapping> {

        public Builder(
            final Conversions conversions,
            final spout.clientview.packetmapping.blockstate.registry.BlockStateMapping internal
        ) {
            super(conversions, internal);
        }

        @Override
        public spout.clientview.packetmapping.blockstate.registry.BlockStateMapping build() {
            BlockStateMappingStep operation;
            if (this.toFunction != null) {
                operation = new FunctionBlockStateMappingStep(bukkitFunctionToInternalFunction(this.toFunction), this.toFunctionRequiresCoordinates);
            } else if (this.toFunctionNMS != null) {
                operation = new FunctionBlockStateMappingStep(nmsFunctionToInternalFunction(this.toFunctionNMS), this.toFunctionRequiresCoordinates);
            } else if (this.to != null) {
                operation = new DirectBlockStateMappingStep(((CraftBlockData) this.to).getState());
            } else {
                throw new IllegalStateException("No to given");
            }
            return new spout.clientview.packetmapping.blockstate.registry.BlockStateMapping(
                this.awarenessLevels.stream().map(CraftAwarenessLevel::fromBukkit).toList(),
                this.from.stream().map(state -> ((CraftBlockData) state).getState()).toList(),
                operation
            );
        }

    }

    private static Consumer<spout.clientview.packetmapping.blockstate.apply.BlockStateMappingHandle> bukkitFunctionToInternalFunction(Consumer<BlockStateMappingHandle> function) {
        return handle -> function.accept(new BlockStateMappingHandleImpl(handle));
    }

    private static Consumer<spout.clientview.packetmapping.blockstate.apply.BlockStateMappingHandle> nmsFunctionToInternalFunction(Consumer<BlockStateMappingHandleNMS> function) {
        return handle -> function.accept(new BlockStateMappingHandleNMSImpl(handle));
    }

}
