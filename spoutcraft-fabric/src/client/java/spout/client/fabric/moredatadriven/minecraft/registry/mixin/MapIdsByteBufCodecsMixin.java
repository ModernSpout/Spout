package spout.client.fabric.moredatadriven.minecraft.registry.mixin;

import net.minecraft.core.IdMap;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import spout.client.fabric.moredatadriven.minecraft.registry.RegistryIdMappings;
import java.util.function.Function;

@Mixin(ByteBufCodecs.class)
public interface MapIdsByteBufCodecsMixin {

    /**
     * @author Spout authors and contributors
     * @reason Map received server ids to the corresponding client ids
     */
    @Overwrite
    private static <T, R> StreamCodec<RegistryFriendlyByteBuf, R> registry(
        ResourceKey<? extends Registry<T>> registryKey,
        Function<Registry<T>, IdMap<R>> mapExtractor
    ) {
        return RegistryIdMappings.getStreamCodec(registryKey, mapExtractor);
    }

}
