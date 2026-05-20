package spout.common.moredatadriven.minecraft.common.nonbuiltin;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import spout.common.moredatadriven.minecraft.common.dependent.DependentNonBuiltInResource;
import spout.common.moredatadriven.minecraft.common.type.TypeWithCodec;
import spout.common.util.mojang.codec.MapInputAndOps;

/**
 * A blueprint/wrapper class for any resource that is not built-in.
 *
 * <p>
 * It wraps the resource it represents in {@link #value}.
 * For resources received on the client, or resources added on the server from a data pack, this is initially null,
 * until initialized from {@link #input} with {@link #initializeValueFromInput}
 * ({@link #input} is the blueprint of the resource, either sent from server to client, or read by the server
 * from a data pack).
 * </p>
 */
public abstract class SpoutNonBuiltInResource<V, T extends TypeWithCodec<V, ? extends SpoutNonBuiltInResource<V, ?>>> implements DependentNonBuiltInResource {

    protected static <V, T extends TypeWithCodec<V, ? extends SpoutNonBuiltInResource<V, ?>>, R extends SpoutNonBuiltInResource<V, T>> MapCodec<R> codec(Registry<T> typeRegistry) {
        return typeRegistry.byNameCodec().dispatchMap(resource -> resource.type, type -> (MapCodec<R>) type.getCodec());
    }

    /**
     * The data-driven type of this instance.
     */
    public final T type;

    /**
     * The resource instance that this instance represents,
     * or null if not initialized yet.
     */
    private @Nullable V value;

    /**
     * The received information to later construct the {@link #value} value,
     * or null if not necessary.
     */
    private @Nullable MapInputAndOps<?> input;

    public SpoutNonBuiltInResource(T type, MapInputAndOps<?> input) {
        this.type = type;
        this.input = input;
    }

    public SpoutNonBuiltInResource(V value) {
        this.type = this.valueToType(value);
        this.value = value;
    }

    protected abstract T valueToType(V value);

    /**
     * @return The resource instance that this instance represents.
     * @throws NullPointerException If the resource instance is not initialized yet.
     */
    public V getValue() {
        return Objects.requireNonNull(this.value);
    }

    public @Nullable MapInputAndOps<?> getInput() {
        return this.input;
    }

    public void initializeValueFromInput(boolean clearInput) {
        this.value = (V) Objects.requireNonNull(this.input).<V>decodeValue((BiFunction) (((dynamicOps, mapLike) -> (DataResult) SpoutNonBuiltInResource.this.type.decodeValueFromInput((DynamicOps) dynamicOps, (MapLike) mapLike))));
        if (clearInput) {
            this.clearInput();
        }
    }

    public void clearInput() {
        this.input = null;
    }

    @Override
    public List<Identifier> getRequiredResources() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return this.type + ": " + (this.value != null ? "item = " + this.value : "input = " + this.input);
    }

}
