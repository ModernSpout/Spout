package spout.clientview.packetmapping.itemstack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.BuiltInAwarenessLevelRegistry;
import spout.clientview.packetmapping.itemstack.apply.ItemStackMappingHandle;
import spout.clientview.packetmapping.itemstack.changeonlyitem.ChangeOnlyItemItemStackMappingsStep;
import spout.clientview.packetmapping.itemstack.decodingcontext.ItemStackMappingDecodingContextItem;
import spout.clientview.packetmapping.itemstack.registry.ItemStackMappingRegistryKey;
import spout.util.mapping.handle.MappingStep;
import spout.util.minecraft.resources.IdentifierUtil;

/**
 * An element of {@link ItemStackMappingRegistryKey#ITEM_STACK_MAPPING}.
 */
public record ItemStackMapping(List<AwarenessLevel> awarenessLevels, List<Item> targets, MappingStep<ItemStackMappingHandle> operation) {

    public static final Codec<ItemStackMapping> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            IdentifierUtil.byNameWithSpoutNamespaceAsDefaultCodec(BuiltInAwarenessLevelRegistry.AWARENESS_LEVEL)
                .listOf().fieldOf("awareness_levels").forGetter(ItemStackMapping::awarenessLevels),
            BuiltInRegistries.ITEM.byNameCodec()
                .listOf().optionalFieldOf("targets").xmap(optionalList -> optionalList.orElse(List.of(ItemStackMappingDecodingContextItem.get())), Optional::of).forGetter(ItemStackMapping::targets),
            ChangeOnlyItemItemStackMappingsStep.codec(BuiltInRegistries.ITEM.byNameCodec()).fieldOf("operation").forGetter(mapping -> (ChangeOnlyItemItemStackMappingsStep) mapping.operation)
        ).apply(instance, ItemStackMapping::new)
    );

}
