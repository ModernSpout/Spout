package spout.api.clientview.packetmapping.itemstack.registry;

import io.papermc.paper.registry.PaperRegistryBuilder;
import io.papermc.paper.registry.data.util.Conversions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.resources.Identifier;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.inventory.CraftItemType;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import spout.api.clientview.model.awarenesslevel.AwarenessLevel;
import spout.api.clientview.model.awarenesslevel.CraftAwarenessLevel;
import spout.api.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.api.clientview.packetmapping.itemstack.handle.ItemStackMappingHandle;
import spout.api.clientview.packetmapping.itemstack.handle.ItemStackMappingHandleImpl;
import spout.api.clientview.packetmapping.itemstack.handle.ItemStackMappingHandleNMS;
import spout.api.clientview.packetmapping.itemstack.handle.ItemStackMappingHandleNMSImpl;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.packetmapping.itemstack.builtin.changeonlyitem.ChangeOnlyItemItemStackMappingStep;
import spout.util.mapping.handle.FunctionMappingStep;
import spout.util.mapping.handle.MappingStep;

/**
 * The implementation for {@link ItemStackMappingRegistryEntry}
 * and {@link ItemStackMappingRegistryEntryNMS}.
 */
public class ItemStackMappingRegistryEntryImpl implements ItemStackMappingRegistryEntryNMS, ItemStackMappingRegistryEntryNMS.Builder {

    protected @Nullable ArrayList<AwarenessLevel> awarenessLevels;
    protected @Nullable ArrayList<ItemType> from;
    protected @Nullable ItemType to;
    protected @Nullable Consumer<ItemStackMappingHandle> toFunction;
    protected @Nullable Consumer<ItemStackMappingHandleNMS> toFunctionNMS;
    protected @Nullable Boolean overrideItemModel;
    protected @Nullable Identifier itemModel;

    public ItemStackMappingRegistryEntryImpl(
        final Conversions ignoredConversions,
        final spout.clientview.packetmapping.itemstack.ItemStackMapping internal
    ) {
        if (internal == null) return;

        this.awarenessLevels = new ArrayList<>(internal.awarenessLevels().stream().map(CraftAwarenessLevel::toBukkit).toList());
        this.from = new ArrayList<>(internal.targets().stream().map(CraftItemType::minecraftToBukkitNew).toList());
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
    public @Nullable List<ItemType> getFrom() {
        return this.from == null ? null : Collections.unmodifiableList(this.from);
    }

    @Override
    public void setFrom(Collection<? extends ItemType> from) {
        this.from = new ArrayList<>(from);
    }

    @Override
    public void addFrom(ItemType from) {
        if (this.from == null) {
            this.from = new ArrayList<>(1);
        }
        this.from.add(from);
    }

    @Override
    public @Nullable ItemType getTo() {
        return this.to;
    }

    @Override
    public void setTo(ItemType to) {
        this.to = to;
        this.toFunction = null;
        this.toFunctionNMS = null;
    }

    @Override
    public void setToFunction(Consumer<ItemStackMappingHandle> function) {
        this.to = null;
        this.toFunction = function;
        this.toFunctionNMS = null;
    }

    @Override
    public void setToFunctionNMS(Consumer<ItemStackMappingHandleNMS> function) {
        this.to = null;
        this.toFunction = null;
        this.toFunctionNMS = function;
    }

    @Override
    public @Nullable Boolean getOverrideItemModel() {
        return this.overrideItemModel;
    }

    @Override
    public void setOverrideItemModel(@Nullable final Boolean overrideItemModel) {
        this.overrideItemModel = overrideItemModel;
    }

    @Override
    public @Nullable NamespacedKey getItemModel() {
        return this.itemModel == null ? null : CraftNamespacedKey.fromMinecraft(this.itemModel);
    }

    @Override
    public void setItemModel(@Nullable NamespacedKey itemModel) {
        this.itemModel = itemModel == null ? null : CraftNamespacedKey.toMinecraft(itemModel);
    }

    @Override
    public @Nullable Identifier getItemModelNMS() {
        return this.itemModel;
    }

    @Override
    public void setItemModelNMS(@Nullable Identifier itemModel) {
        this.itemModel = itemModel;
    }

    /**
     * The implementation for {@link ItemStackMappingRegistryEntry.Builder}
     * and {@link ItemStackMappingRegistryEntryNMS.Builder}.
     */
    public static final class Builder extends ItemStackMappingRegistryEntryImpl implements ItemStackMappingRegistryEntryNMS.Builder,
        PaperRegistryBuilder<spout.clientview.packetmapping.itemstack.ItemStackMapping, ItemStackMapping> {

        public Builder(
            final Conversions conversions,
            final spout.clientview.packetmapping.itemstack.ItemStackMapping internal
        ) {
            super(conversions, internal);
        }

        @Override
        public spout.clientview.packetmapping.itemstack.ItemStackMapping build() {
            List<spout.clientview.model.awarenesslevel.AwarenessLevel> awarenessLevels = this.awarenessLevels != null ? this.awarenessLevels.stream().map(CraftAwarenessLevel::fromBukkit).toList() : Arrays.asList(AwarenessLevels.getThatDoNotAlwaysUnderstandsAllServerSideItems());
            if (this.from == null) {
                throw new IllegalStateException("No from was specified");
            }
            MappingStep<spout.clientview.packetmapping.itemstack.apply.ItemStackMappingHandle> operation;
            if (this.toFunction != null) {
                operation = new FunctionMappingStep<>(bukkitFunctionToInternalFunction(this.toFunction));
            } else if (this.toFunctionNMS != null) {
                operation = new FunctionMappingStep<>(nmsFunctionToInternalFunction(this.toFunctionNMS));
            } else if (this.to != null) {
                operation = new ChangeOnlyItemItemStackMappingStep(CraftItemType.bukkitToMinecraftNew(this.to));
            } else {
                throw new IllegalStateException("No to given");
            }
            return new spout.clientview.packetmapping.itemstack.ItemStackMapping(
                awarenessLevels,
                this.from.stream().map(CraftItemType::bukkitToMinecraftNew).toList(),
                operation,
                this.overrideItemModel,
                this.itemModel
            );
        }

    }

    private static Consumer<spout.clientview.packetmapping.itemstack.apply.ItemStackMappingHandle> bukkitFunctionToInternalFunction(Consumer<ItemStackMappingHandle> function) {
        return handle -> function.accept(new ItemStackMappingHandleImpl(handle));
    }

    private static Consumer<spout.clientview.packetmapping.itemstack.apply.ItemStackMappingHandle> nmsFunctionToInternalFunction(Consumer<ItemStackMappingHandleNMS> function) {
        return handle -> function.accept(new ItemStackMappingHandleNMSImpl(handle));
    }

}
