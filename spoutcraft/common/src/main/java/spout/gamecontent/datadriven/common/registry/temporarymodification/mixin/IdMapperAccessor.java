package spout.gamecontent.datadriven.common.registry.temporarymodification.mixin;

import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import net.minecraft.core.IdMapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.List;

@Mixin(IdMapper.class)
public interface IdMapperAccessor<T> {

    @Accessor("nextId")
    int getNextId();

    @Accessor("nextId")
    void setNextId(int nextId);

    @Accessor("tToId")
    Reference2IntMap<T> getTToId();

    @Accessor("idToT")
    List<T> getIdToT();

}
