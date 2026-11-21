package spout.gamecontent.builtin.item.tag.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import spout.gamecontent.builtin.item.tag.PlaceOnWaterBlockCallsToUseOnAreSuperCalls;
import spout.gamecontent.builtin.item.tag.PlaceOnWaterBlockCallsToUseAreSuperCalls;

@Mixin(BlockItem.class)
public abstract class PlacedOnWaterBlockBlockItemMixin {

    @WrapMethod(method = "useOn")
    private InteractionResult modifyUseOn(UseOnContext context, Operation<InteractionResult> original) {
        if (PlaceOnWaterBlockCallsToUseOnAreSuperCalls.get()) {
            return original.call(context);
        }
        if (((BlockItem) (Object) this).builtInRegistryHolder().is(spout.gamecontent.builtin.item.tag.SpoutItemTags.PLACED_ON_WATER_BLOCK)) {
            return InteractionResult.PASS;
        }
        PlaceOnWaterBlockCallsToUseAreSuperCalls.set();
        try {
            return original.call(context);
        } finally {
            PlaceOnWaterBlockCallsToUseAreSuperCalls.unset();
        }
    }

}
