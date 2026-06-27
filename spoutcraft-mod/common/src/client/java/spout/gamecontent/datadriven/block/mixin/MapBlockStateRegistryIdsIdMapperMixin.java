package spout.gamecontent.datadriven.block.mixin;

import net.minecraft.core.IdMapper;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import spout.gamecontent.datadriven.block.BlockStateRegistryIdMappings;

@Mixin(IdMapper.class)
public abstract class MapBlockStateRegistryIdsIdMapperMixin {

    @ModifyVariable(method = "byId", at = @At("HEAD"), argsOnly = true)
    private int mapBlockStateId(int id) {
        IdMapper<?> registry = (IdMapper<?>) (Object) this;
        if (registry == Block.BLOCK_STATE_REGISTRY) {
            return BlockStateRegistryIdMappings.applyServerToClient(id);
        }
        return id;
    }

}
