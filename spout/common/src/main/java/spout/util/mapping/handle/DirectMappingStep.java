package spout.util.mapping.handle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * A {@link MappingStep} that always maps to a specific value.
 */
public record DirectMappingStep<T, H extends AbstractMappingHandle<T>>(T to) implements MappingStep<H> {

    public static <T, H extends AbstractMappingHandle<T>> Codec<DirectMappingStep<T, H>> codec(Codec<T> valueCodec) {
        return RecordCodecBuilder.create(
            instance -> instance.group(
                valueCodec.fieldOf("to").forGetter(DirectMappingStep::to)
            ).apply(instance, DirectMappingStep::new)
        );
    }

    @Override
    public void apply(H handle) {
        handle.set(this.to);
    }

    @Override
    public boolean isDirect() {
        return true;
    }

}
