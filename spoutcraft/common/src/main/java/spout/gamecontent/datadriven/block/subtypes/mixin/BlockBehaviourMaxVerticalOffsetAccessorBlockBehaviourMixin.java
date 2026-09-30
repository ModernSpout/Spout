package spout.gamecontent.datadriven.block.subtypes.mixin;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import spout.gamecontent.datadriven.block.subtypes.BlockBehaviourMaxVerticalOffsetAccessor;

/**
 * Mixin applying {@link BlockBehaviourMaxVerticalOffsetAccessor}.
 */
@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMaxVerticalOffsetAccessorBlockBehaviourMixin implements BlockBehaviourMaxVerticalOffsetAccessor {

    @Invoker("getMaxVerticalOffset")
    public abstract float invokeGetMaxVerticalOffset();

    @Override
    public float spout$getMaxVerticalOffset() {
        return this.invokeGetMaxVerticalOffset();
    }

}
