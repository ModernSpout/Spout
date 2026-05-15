package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Blocks.class)
public interface BlocksStatePredicatesAccessor {

    @Accessor("NOT_CLOSED_SHULKER")
    static BlockBehaviour.StatePredicate getNotClosedShulker() {
        throw new AssertionError();
    }

    @Accessor("NOT_EXTENDED_PISTON")
    static BlockBehaviour.StatePredicate getNotExtendedPiston() {
        throw new AssertionError();
    }

}
