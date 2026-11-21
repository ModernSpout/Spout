package spout.gamecontent.builtin.item.tag.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import spout.gamecontent.builtin.item.tag.PlaceOnWaterBlockCallsToUseOnAreSuperCalls;
import spout.gamecontent.builtin.item.tag.PlaceOnWaterBlockCallsToUseAreSuperCalls;

@Mixin(Item.class)
public abstract class PlacedOnWaterBlockItemMixin {

    @WrapMethod(method = "use")
    private InteractionResult modifyUse(
        Level level,
        Player player,
        InteractionHand hand,
        Operation<InteractionResult> original
    ) {
        PlaceOnWaterBlockCallsToUseOnAreSuperCalls.set();
        try {
            if (PlaceOnWaterBlockCallsToUseAreSuperCalls.get()) {
                return original.call(level, player, hand);
            }
            if (((Object) this) instanceof BlockItem blockItem && blockItem.builtInRegistryHolder().is(spout.gamecontent.builtin.item.tag.SpoutItemTags.PLACED_ON_WATER_BLOCK)) {
                net.minecraft.world.phys.BlockHitResult hitResult = Item.getPlayerPOVHitResult(level, player, net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY);
                net.minecraft.world.phys.BlockHitResult blockAboveResult = hitResult.withPosition(hitResult.getBlockPos().above());
                return blockItem.useOn(new UseOnContext(player, hand, blockAboveResult));
            }
            return original.call(level, player, hand);
        } finally {
            PlaceOnWaterBlockCallsToUseOnAreSuperCalls.unset();
        }
    }

}
