package spout.api.clientview.packetmapping.component.registry;

import io.papermc.paper.registry.PaperRegistryBuilder;
import io.papermc.paper.registry.data.util.Conversions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.jspecify.annotations.Nullable;
import spout.api.clientview.model.awarenesslevel.AwarenessLevel;
import spout.api.clientview.model.awarenesslevel.CraftAwarenessLevel;
import spout.api.clientview.packetmapping.component.ComponentMapping;
import spout.api.clientview.packetmapping.component.ComponentTarget;
import spout.api.clientview.packetmapping.component.CraftComponentTarget;
import spout.api.clientview.packetmapping.component.handle.ComponentMappingHandle;
import spout.api.clientview.packetmapping.component.handle.ComponentMappingHandleImpl;
import spout.api.clientview.packetmapping.component.handle.ComponentMappingHandleNMS;
import spout.api.clientview.packetmapping.component.handle.ComponentMappingHandleNMSImpl;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.util.mapping.handle.FunctionMappingStep;
import spout.util.mapping.handle.MappingStep;

/**
 * The implementation for {@link ComponentMappingRegistryEntry}
 * and {@link ComponentMappingRegistryEntryNMS}.
 */
public class ComponentMappingRegistryEntryImpl implements ComponentMappingRegistryEntryNMS, ComponentMappingRegistryEntryNMS.Builder {

    protected @Nullable ArrayList<AwarenessLevel> awarenessLevels;
    protected @Nullable ArrayList<ComponentTarget> from;
    protected @Nullable Consumer<ComponentMappingHandle> toFunction;
    protected @Nullable Consumer<ComponentMappingHandleNMS> toFunctionNMS;

    public ComponentMappingRegistryEntryImpl(
        final Conversions ignoredConversions,
        final spout.clientview.packetmapping.component.ComponentMapping internal
    ) {
        if (internal == null) return;

        this.awarenessLevels = new ArrayList<>(internal.awarenessLevels().stream().map(CraftAwarenessLevel::toBukkit).toList());
        this.from = new ArrayList<>(internal.targets().stream().map(CraftComponentTarget::toBukkit).toList());
    }

    @Override
    public @Nullable List<? extends AwarenessLevel> getAwarenessLevels() {
        return this.awarenessLevels == null ? null : Collections.unmodifiableList(this.awarenessLevels);
    }

    @Override
    public void setAwarenessLevels(Collection<AwarenessLevel> awarenessLevels) {
        this.awarenessLevels = new ArrayList<>(awarenessLevels);
    }

    @Override
    public void addAwarenessLevel(AwarenessLevel awarenessLevel) {
        if (this.awarenessLevels == null) {
            this.awarenessLevels = new ArrayList<>(1);
        }
        this.awarenessLevels.add(awarenessLevel);
    }

    @Override
    public @Nullable List<ComponentTarget> getFrom() {
        return this.from == null ? null : Collections.unmodifiableList(this.from);
    }

    @Override
    public void setFrom(Collection<? extends ComponentTarget> from) {
        this.from = new ArrayList<>(from);
    }

    @Override
    public void addFrom(ComponentTarget from) {
        if (this.from == null) {
            this.from = new ArrayList<>(1);
        }
        this.from.add(from);
    }

    @Override
    public void setToFunction(Consumer<ComponentMappingHandle> function) {
        this.toFunction = function;
        this.toFunctionNMS = null;
    }

    @Override
    public void setToFunctionNMS(Consumer<ComponentMappingHandleNMS> function) {
        this.toFunction = null;
        this.toFunctionNMS = function;
    }

    /**
     * The implementation for {@link ComponentMappingRegistryEntry.Builder}
     * and {@link ComponentMappingRegistryEntryNMS.Builder}.
     */
    public static final class Builder extends ComponentMappingRegistryEntryImpl implements ComponentMappingRegistryEntryNMS.Builder,
        PaperRegistryBuilder<spout.clientview.packetmapping.component.ComponentMapping, ComponentMapping> {

        public Builder(
            final Conversions conversions,
            final spout.clientview.packetmapping.component.ComponentMapping internal
        ) {
            super(conversions, internal);
        }

        @Override
        public spout.clientview.packetmapping.component.ComponentMapping build() {
            List<spout.clientview.model.awarenesslevel.AwarenessLevel> awarenessLevels = this.awarenessLevels != null ? this.awarenessLevels.stream().map(CraftAwarenessLevel::fromBukkit).toList() : Arrays.asList(AwarenessLevels.getThatDoNotAlwaysUnderstandsAllServerSideTranslatables());
            List<ComponentTarget> from = this.from != null ? this.from : List.of(ComponentTarget.ALL);
            MappingStep<spout.clientview.packetmapping.component.apply.ComponentMappingHandle> operation;
            if (this.toFunction != null) {
                operation = new FunctionMappingStep<>(bukkitFunctionToInternalFunction(this.toFunction));
            } else if (this.toFunctionNMS != null) {
                operation = new FunctionMappingStep<>(nmsFunctionToInternalFunction(this.toFunctionNMS));
            } else {
                throw new IllegalStateException("No to given");
            }
            return new spout.clientview.packetmapping.component.ComponentMapping(
                awarenessLevels,
                this.from.stream().map(CraftComponentTarget::fromBukkit).toList(),
                operation
            );
        }

    }

    private static Consumer<spout.clientview.packetmapping.component.apply.ComponentMappingHandle> bukkitFunctionToInternalFunction(Consumer<ComponentMappingHandle> function) {
        return handle -> function.accept(new ComponentMappingHandleImpl(handle));
    }

    private static Consumer<spout.clientview.packetmapping.component.apply.ComponentMappingHandle> nmsFunctionToInternalFunction(Consumer<ComponentMappingHandleNMS> function) {
        return handle -> function.accept(new ComponentMappingHandleNMSImpl(handle));
    }

}
