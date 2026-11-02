package spout.gamecontent.datadriven.block.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import spout.clientview.clientmod.protocol.ClientModState;
import spout.clientview.clientmod.protocol.ClientModStateHandler;

@Mixin(ClientLevel.class)
public abstract class AlwaysShowMarkersWhenItemIsHeldMixin {

    @Redirect(
        method = "getMarkerParticleTarget()Lnet/minecraft/world/level/block/Block;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;getPlayerMode()Lnet/minecraft/world/level/GameType;"
        )
    )
    private GameType alwaysCreative(MultiPlayerGameMode instance) {
        if (ClientModStateHandler.getState() == ClientModState.ADDED_CUSTOM_CONTENT) {
            return GameType.CREATIVE;
        }
        return instance.localPlayerMode;
    }

}
