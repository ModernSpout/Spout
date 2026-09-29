package spout.clientview.packetmapping.itemstack.builtin.addtooltip;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import org.jspecify.annotations.Nullable;
import spout.clientview.packetmapping.itemstack.apply.ItemStackMappingHandle;
import spout.server.paper.impl.configuration.SpoutGlobalConfiguration;
import spout.gamecontent.datadriven.namespacename.NamespaceNames;
import spout.util.mapping.handle.MappingStep;

/**
 * A {@link MappingStep} that can add some default tooltips to items.
 * It currently supports:
 * <ul>
 *     <li>The namespace</li>
 * </ul>
 */
public final class AddTooltipItemStackMappingStep implements MappingStep<ItemStackMappingHandle> {

    @Override
    public void apply(ItemStackMappingHandle handle) {
        if (!SpoutGlobalConfiguration.get().tooltips.items.namespace) return;
        Component nameComponent = NamespaceNames.getTranslatable(handle.getOriginal().getItem().keyInItemRegistry.getNamespace())
            .withStyle(style -> style
                .withItalic(true)
                .withColor(ChatFormatting.BLUE)
            );
        ItemStack itemStack = handle.getMutable();
        @Nullable ItemLore lore = itemStack.get(DataComponents.LORE);
        if (lore == null) {
            lore = new ItemLore(List.of(nameComponent));
        } else {
            lore = lore.withLineAdded(nameComponent);
        }
        itemStack.set(DataComponents.LORE, lore);
    }

}
