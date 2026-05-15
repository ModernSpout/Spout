package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.Map;

@Mixin(FeatureFlagRegistry.class)
public interface FeatureFlagRegistryAccessor {

    @Accessor("names")
    Map<Identifier, FeatureFlag> getNames();

}
