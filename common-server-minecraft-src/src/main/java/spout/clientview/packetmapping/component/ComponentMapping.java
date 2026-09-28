package spout.clientview.packetmapping.component;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import spout.clientview.model.awarenesslevel.AwarenessLevel;
import spout.clientview.model.awarenesslevel.AwarenessLevels;
import spout.clientview.model.awarenesslevel.BuiltInAwarenessLevelRegistry;
import spout.clientview.packetmapping.component.apply.ComponentMappingHandle;
import spout.clientview.packetmapping.component.registry.ComponentMappingRegistryKey;
import spout.util.mapping.handle.MappingStep;
import spout.util.minecraft.resources.IdentifierUtil;
import spout.util.mojang.codec.CodecUtil;
import spout.util.mojang.codec.EnumViaIdentifierCodec;

/**
 * An element of {@link ComponentMappingRegistryKey#COMPONENT_MAPPING}.
 */
public record ComponentMapping(
    List<AwarenessLevel> awarenessLevels,
    List<ComponentTarget> targets,
    MappingStep<ComponentMappingHandle> operation
) {

    public static final Codec<ComponentMapping> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            CodecUtil.optionalFieldOf(
                IdentifierUtil.byNameWithSpoutNamespaceAsDefaultCodec(BuiltInAwarenessLevelRegistry.AWARENESS_LEVEL).listOf(),
                "awareness_levels",
                () -> Arrays.asList(AwarenessLevels.getThatDoNotAlwaysUnderstandsAllServerSideTranslatables())
            ).forGetter(ComponentMapping::awarenessLevels),
            new EnumViaIdentifierCodec<>(ComponentTarget.class, spout.branding.SpoutNamespace.SPOUT)
                .listOf().optionalFieldOf("targets").xmap(optionalList -> optionalList.orElse(List.of(ComponentTarget.ALL)), Optional::of).forGetter(ComponentMapping::targets),
            new Codec<MappingStep<ComponentMappingHandle>>() {

                @Override
                public <T> DataResult<T> encode(final MappingStep<ComponentMappingHandle> componentMappingHandle, final DynamicOps<T> dynamicOps, final T t) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public <T> DataResult<Pair<MappingStep<ComponentMappingHandle>, T>> decode(final DynamicOps<T> dynamicOps, final T t) {
                    throw new UnsupportedOperationException();
                }

            }.fieldOf("operation").forGetter(ComponentMapping::operation)
        ).apply(instance, ComponentMapping::new)
    );

}
