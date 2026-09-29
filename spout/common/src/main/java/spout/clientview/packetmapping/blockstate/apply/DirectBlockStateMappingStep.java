package spout.clientview.packetmapping.blockstate.apply;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import spout.util.mapping.handle.DirectMappingStep;

/**
 * A {@link BlockStateMappingStep} that always maps to a specific value.
 *
 * <p>
 * This is a conceptual extension of {@link DirectMappingStep}.
 * </p>
 */
public record DirectBlockStateMappingStep(BlockState to) implements BlockStateMappingStep {

    public static Codec<DirectBlockStateMappingStep> codec(Codec<BlockState> valueCodec) {
        return RecordCodecBuilder.create(
            instance -> instance.group(
                valueCodec.fieldOf("to").forGetter(DirectBlockStateMappingStep::to)
            ).apply(instance, DirectBlockStateMappingStep::new)
        );
    }

    @Override
    public void apply(BlockStateMappingHandle handle) {
        handle.set(this.to);
    }

    @Override
    public boolean isDirect() {
        return true;
    }

}
