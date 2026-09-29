package spout.gamecontent.datadriven.common.type;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import net.minecraft.resources.Identifier;
import spout.gamecontent.datadriven.common.nonbuiltin.SpoutNonBuiltInResource;
import spout.util.mojang.codec.MapInputAndOps;
import java.util.List;

/**
 * A type that provides a codec for its resources.
 */
public interface TypeWithCodec<V, R extends SpoutNonBuiltInResource<V, ?>> {

    Identifier getIdentifier();

    MapCodec<R> getCodec();

    <T> DataResult<? extends V> decodeValueFromInput(DynamicOps<T> dynamicOps, MapLike<T> mapLike);

    List<Identifier> decodeRequiredResources(MapInputAndOps<?> input);

}
