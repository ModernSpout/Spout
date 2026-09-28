package spout.clientview.packetmapping.itemstack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.model.awarenesslevel.BuiltInAwarenessLevelRegistry;
import spout.clientview.packetmapping.itemstack.apply.ItemStackMappingHandle;
import spout.clientview.packetmapping.itemstack.builtin.changeonlyitem.ChangeOnlyItemItemStackMappingStep;
import spout.clientview.packetmapping.itemstack.decodingcontext.ItemStackMappingDecodingContextItem;
import spout.clientview.packetmapping.itemstack.registry.ItemStackMappingRegistryKey;
import spout.util.mapping.handle.MappingStep;
import spout.util.minecraft.resources.IdentifierUtil;
import spout.util.mojang.codec.CodecUtil;

/**
 * An element of {@link ItemStackMappingRegistryKey#ITEM_STACK_MAPPING}.
 */
public record ItemStackMapping(
    List<AwarenessLevel> awarenessLevels,
    List<Item> targets,
    MappingStep<ItemStackMappingHandle> operation,
    @Nullable Boolean overrideItemModel,
    @Nullable Identifier itemModel
) {

    public ItemStackMapping(
        List<AwarenessLevel> awarenessLevels,
        List<Item> targets,
        MappingStep<ItemStackMappingHandle> operation
    ) {
        this(awarenessLevels, targets, operation, false, null);
    }

    public static final Codec<ItemStackMapping> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            CodecUtil.optionalFieldOf(
                IdentifierUtil.byNameWithSpoutNamespaceAsDefaultCodec(BuiltInAwarenessLevelRegistry.AWARENESS_LEVEL).listOf(),
                "awareness_levels",
                () -> Arrays.asList(AwarenessLevels.getThatDoNotAlwaysUnderstandsAllServerSideItems())
            ).forGetter(ItemStackMapping::awarenessLevels),
            BuiltInRegistries.ITEM.byNameCodec()
                .listOf().optionalFieldOf("targets").xmap(optionalList -> optionalList.orElse(List.of(ItemStackMappingDecodingContextItem.get())), Optional::of).forGetter(ItemStackMapping::targets),
            ChangeOnlyItemItemStackMappingStep.codec(BuiltInRegistries.ITEM.byNameCodec()).fieldOf("operation").forGetter(mapping -> (ChangeOnlyItemItemStackMappingStep) mapping.operation),
            Codec.BOOL.optionalFieldOf("override_item_model").forGetter(mapping -> Optional.ofNullable(mapping.overrideItemModel)),
            Identifier.CODEC.optionalFieldOf("item_model").forGetter(mapping -> Optional.ofNullable(mapping.itemModel))
        ).apply(instance, (awarenessLevels, targets, operation, overrideItemModel, itemModel) -> new ItemStackMapping(awarenessLevels, targets, operation, overrideItemModel.orElse(null), itemModel.orElse(null)))
    );

}
