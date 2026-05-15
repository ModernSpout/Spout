package spout.client.fabric.moredatadriven.mixin;

import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import java.util.Map;

@Mixin(MappedRegistry.class)
public interface MappedRegistryAccessor<T> {

    @Accessor("byId")
    ObjectList<Holder.Reference<T>> getById();

    @Accessor("toId")
    Reference2IntMap<T> getToId();

    @Accessor("byLocation")
    Map<Identifier, Holder.Reference<T>> getByLocation();

    @Accessor("byKey")
    Map<ResourceKey<T>, Holder.Reference<T>> getByKey();

    @Accessor("byValue")
    Map<T, Holder.Reference<T>> getByValue();

    @Accessor("registrationInfos")
    Map<ResourceKey<T>, RegistrationInfo> getRegistrationInfos();

    @Accessor("frozenTags")
    Map<TagKey<T>, HolderSet.Named<T>> getFrozenTags();

    @Accessor("frozen")
    void setFrozen(boolean frozen);

    @Accessor("unregisteredIntrusiveHolders")
    Map<T, Holder.Reference<T>> getUnregisteredIntrusiveHolders();

    @Accessor("unregisteredIntrusiveHolders")
    void setUnregisteredIntrusiveHolders(Map<T, Holder.Reference<T>> map);

    @Invoker("createTag")
    HolderSet.Named<T> callCreateTag(TagKey<T> tagKey);

}
