package spout.client.fabric.moredatadriven.mixin;

import net.minecraft.core.component.DataComponentInitializers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.List;

@Mixin(DataComponentInitializers.class)
public interface DataComponentInitializersAccessor {

    @Accessor("initializers")
    List<?> getInitializers();

}
