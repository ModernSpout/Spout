package spout.clientview.packetmapping.itemstack.apply.onlyapplicablestonecuttingrecipes;

import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SelectableRecipe;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import org.jspecify.annotations.Nullable;
import spout.clientview.model.ClientView;
import spout.clientview.packetmapping.clientviewlookup.ThreadLocalClientViewLookup;
import java.util.Collections;
import java.util.Set;

public final class UpdateRecipesPacketFilter {

    private UpdateRecipesPacketFilter() {
        throw new UnsupportedOperationException();
    }

    public static SelectableRecipe.SingleInputSet<StonecutterRecipe> filter(SelectableRecipe.SingleInputSet<StonecutterRecipe> set) {
        @Nullable ClientView clientView = ThreadLocalClientViewLookup.getThreadLocalClientView();
        if (clientView == null) {
            return set;
        }
        @Nullable ServerPlayer player = clientView.getPlayerNMS();
        if (player == null) {
            return set;
        }
        @Nullable ItemStack lastInput = player.spout$lastStonecutterInput;
        if (lastInput == null) {
            return new SelectableRecipe.SingleInputSet<>(Collections.emptyList());
        }
        return new SelectableRecipe.SingleInputSet<>(set.entries().stream().filter(entry -> {
            Ingredient input = entry.input();
            Set<ItemStack> inputAsItemStacks = input.itemStacks();
            ItemStack itemStack = inputAsItemStacks != null ? inputAsItemStacks.stream().findFirst().orElse(null) : null;
            if (itemStack == null) {
                itemStack = new ItemStack(input.getSingleItem().get().value());
            }
            if (itemStack.getCount() != 1) {
                itemStack = itemStack.copyWithCount(1);
            }
            return ItemStack.matches(lastInput, itemStack);
        }).toList());
    }

    public static void resendRecipes(ServerPlayer player, boolean refreshContainerMenu) {
        ItemStack inputStack = player.containerMenu.getSlot(StonecutterMenu.INPUT_SLOT).getItem();
        if (inputStack.isEmpty()) {
            inputStack = null;
        }
        player.spout$lastStonecutterInput = inputStack != null ? inputStack.copyWithCount(1) : null;
        RecipeManager recipeManager = MinecraftServer.getServer().getRecipeManager();
        ClientboundUpdateRecipesPacket recipesPacket = new ClientboundUpdateRecipesPacket(
            recipeManager.getSynchronizedItemProperties(), recipeManager.getSynchronizedStonecutterRecipes()
        );
        player.connection.send(recipesPacket);
        if (inputStack != null && refreshContainerMenu) {
            player.connection.send(new ClientboundContainerSetSlotPacket(player.containerMenu.containerId, player.containerMenu.incrementStateId(), StonecutterMenu.INPUT_SLOT, ItemStack.EMPTY));
            player.connection.send(new ClientboundContainerSetSlotPacket(player.containerMenu.containerId, player.containerMenu.incrementStateId(), StonecutterMenu.INPUT_SLOT, inputStack));
        }
    }

}
