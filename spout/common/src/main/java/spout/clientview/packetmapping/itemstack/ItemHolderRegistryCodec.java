package spout.clientview.packetmapping.itemstack;

import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import spout.clientview.packetmapping.itemstack.apply.ItemInPacketMappers;

public final class ItemHolderRegistryCodec implements StreamCodec<RegistryFriendlyByteBuf, Holder<Item>> {

    public final StreamCodec<RegistryFriendlyByteBuf, Holder<Item>> unmapped;

    public ItemHolderRegistryCodec(StreamCodec<RegistryFriendlyByteBuf, Holder<Item>> unmapped) {
        this.unmapped = unmapped;
    }

    @Override
    public Holder<Item> decode(final RegistryFriendlyByteBuf buffer) {
        return unmapped.decode(buffer);
    }

    @Override
    public void encode(final RegistryFriendlyByteBuf buffer, final Holder<Item> value) {
        Item item = value.value();
        Item mappedItem = ItemInPacketMappers.ITEM.apply(item);
        unmapped.encode(buffer, mappedItem == item ? value : Holder.direct(mappedItem));
    }

}
