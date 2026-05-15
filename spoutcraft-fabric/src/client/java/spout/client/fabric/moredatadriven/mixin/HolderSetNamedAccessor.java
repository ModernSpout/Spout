package spout.client.fabric.moredatadriven.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import java.util.List;

@Mixin(HolderSet.Named.class)
public interface HolderSetNamedAccessor<T> {

    @Accessor("contents")
    @Nullable List<Holder<T>> getContents();

    @Invoker("bind")
    void callBind(List<Holder<T>> contents);

}
