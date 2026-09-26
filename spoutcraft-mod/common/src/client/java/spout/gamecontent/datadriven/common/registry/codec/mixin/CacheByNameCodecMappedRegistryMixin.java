package spout.gamecontent.datadriven.common.registry.codec.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * Caches the result of {@link Registry#byNameCodec} for any {@link MappedRegistry}.
 */
@Mixin(MappedRegistry.class)
public abstract class CacheByNameCodecMappedRegistryMixin<T> implements WritableRegistry<T> {

    @Unique
    private @Nullable Codec<T> spout$cachedByNameCodec;

    @Override
    public Codec<T> byNameCodec() {
        if (this.spout$cachedByNameCodec == null) {
            this.spout$cachedByNameCodec = WritableRegistry.super.byNameCodec();
        }
        return this.spout$cachedByNameCodec;
    }

}
