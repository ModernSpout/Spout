package spout.common.moredatadriven.minecraft.common.type;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import net.minecraft.resources.Identifier;
import spout.common.moredatadriven.minecraft.common.nonbuiltin.SpoutNonBuiltInResource;

/**
 * A type that provides a codec for its resources.
 */
public interface TypeWithCodec<V, R extends SpoutNonBuiltInResource<V, ?>> {

    Identifier getIdentifier();

    MapCodec<R> getCodec();

    <T> DataResult<? extends V> decodeValueFromInput(DynamicOps<T> dynamicOps, MapLike<T> mapLike);

}
