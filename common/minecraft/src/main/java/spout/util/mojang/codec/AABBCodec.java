package spout.util.mojang.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.phys.AABB;

public final class AABBCodec {

    private AABBCodec() {
        throw new UnsupportedOperationException();
    }

    public static final MapCodec<AABB> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.DOUBLE.fieldOf("min_x").forGetter(b -> b.minX),
        Codec.DOUBLE.fieldOf("min_y").forGetter(b -> b.minY),
        Codec.DOUBLE.fieldOf("min_z").forGetter(b -> b.minZ),
        Codec.DOUBLE.fieldOf("max_x").forGetter(b -> b.maxX),
        Codec.DOUBLE.fieldOf("max_y").forGetter(b -> b.maxY),
        Codec.DOUBLE.fieldOf("max_z").forGetter(b -> b.maxZ)
    ).apply(instance, AABB::new));

}
