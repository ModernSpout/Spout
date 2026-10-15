package spout.gamecontent.builtin.recipe;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectObjectImmutablePair;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.jspecify.annotations.Nullable;
import spout.configuration.SpoutGlobalConfiguration;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public final class MissingTransitiveStonecuttingRecipeAdder {

    private MissingTransitiveStonecuttingRecipeAdder() {
        throw new UnsupportedOperationException();
    }

    private static final List<ResourceKey<Recipe<?>>> transitiveRecipes = new ArrayList<>(0);

    public static void run(RecipeMap recipeMap, @Nullable List<RecipeHolder<StonecutterRecipe>> recipesToProcess) {

        // Skip if not needed
        if (!SpoutGlobalConfiguration.get().recipes.stonecutting.addMissingTransitiveRecipes) return;

        // If no new recipes were given, process all of them
        if (recipesToProcess == null) {
            // Remove the existing transitive recipes
            transitiveRecipes.forEach(recipeMap::removeRecipe);
            transitiveRecipes.clear();
            // Assume all remaining recipes must be processed
            recipesToProcess = recipeMap.values().stream()
                .filter(holder -> holder.value().getType() == RecipeType.STONECUTTING && holder.value() instanceof StonecutterRecipe)
                .map(holder -> (RecipeHolder<StonecutterRecipe>) holder).toList();
        }

        // Skip if no recipes to process
        if (recipesToProcess.isEmpty()) return;

        // Create the needed data structures
        Map<Item, Object2IntMap<Item>> inputToOutput = new Object2ObjectOpenHashMap<>();
        Map<Item, Object2IntMap<Item>> outputToInput = new Object2ObjectOpenHashMap<>();
        Deque<ObjectObjectImmutablePair<Item, Item>> inputOutputTodo = new ArrayDeque<>();

        // Populate the data structures
        recipeMap.values().forEach(holder -> {
            Recipe<?> recipe = holder.value();
            if (recipe instanceof StonecutterRecipe stonecutterRecipe) {
                Ingredient inputIngredient = stonecutterRecipe.input();
                if (inputIngredient.getSingleItem().isPresent()) {
                    Item inputItem = inputIngredient.getSingleItem().get().value();
                    if (inputItem != Items.AIR) {
                        ItemStackTemplate outputTemplate = ((SlotDisplay.ItemStackSlotDisplay) stonecutterRecipe.resultDisplay()).stack();
                        if (outputTemplate.components().isEmpty()) {
                            Item outputItem = outputTemplate.item().value();
                            int outputCount = outputTemplate.count();
                            if (outputItem != Items.AIR && outputCount > 0) {
                                Object2IntMap<Item> inputMap = inputToOutput.computeIfAbsent(inputItem, _ -> new Object2IntOpenHashMap<>());
                                int oldCount = inputMap.getInt(outputItem);
                                if (oldCount < outputCount) {
                                    inputMap.put(outputItem, outputCount);
                                    outputToInput.computeIfAbsent(outputItem, _ -> new Object2IntOpenHashMap<>()).put(inputItem, outputCount);
                                    if (oldCount == 0) {
                                        inputOutputTodo.add(ObjectObjectImmutablePair.of(inputItem, outputItem));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        // Determine which new recipes to create
        List<ImmutableTriple<Item, Item, Integer>> newRecipes = new ArrayList<>();
        while (true) {
            ObjectObjectImmutablePair<Item, Item> polled = inputOutputTodo.poll();
            if (polled == null) {
                break;
            }
            Item itemB = polled.left();
            Item itemC = polled.right();
            if (itemB == itemC) {
                continue;
            }
            int bToCCount = inputToOutput.get(itemB).getInt(itemC);
            Object2IntMap<Item> cToD = inputToOutput.get(itemC);
            if (cToD != null) {
                Object2IntMap<Item> bOutgoing = inputToOutput.get(itemB);
                cToD.forEach((itemD, cToDCount) -> {
                    if (itemB == itemD || itemC == itemD) {
                        return;
                    }
                    if (bOutgoing.getInt(itemD) == 0) {
                        int bToDCount = bToCCount * cToDCount;
                        bOutgoing.put(itemD, bToDCount);
                        outputToInput.get(itemD).put(itemB, bToDCount);
                        inputOutputTodo.add(ObjectObjectImmutablePair.of(itemB, itemD));
                        newRecipes.add(ImmutableTriple.of(itemB, itemD, bToDCount));
                    }
                });
            }
            Object2IntMap<Item> bToA = outputToInput.get(itemB);
            if (bToA != null) {
                Object2IntMap<Item> cIncoming = outputToInput.get(itemC);
                bToA.forEach((itemA, aToBCount) -> {
                    if (itemB == itemA || itemC == itemA) {
                        return;
                    }
                    if (cIncoming.getInt(itemA) == 0) {
                        int aToCCount = aToBCount * bToCCount;
                        cIncoming.put(itemA, aToCCount);
                        inputToOutput.get(itemA).put(itemC, aToCCount);
                        inputOutputTodo.add(ObjectObjectImmutablePair.of(itemA, itemC));
                        newRecipes.add(ImmutableTriple.of(itemA, itemC, aToCCount));
                    }
                });
            }
        }

        // Add the new recipes
        for (ImmutableTriple<Item, Item, Integer> newRecipe : newRecipes) {
            Item input = newRecipe.getLeft();
            Item output = newRecipe.getMiddle();
            ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(spout.branding.SpoutNamespace.SPOUT, input.keyInItemRegistry.getNamespace() + "_" + input.keyInItemRegistry.getPath() + "_to_" + output.keyInItemRegistry.getNamespace() + "_" + output.keyInItemRegistry.getPath() + "_transitive_stonecutting"));
            transitiveRecipes.add(key);
            recipeMap.addRecipe(new RecipeHolder<>(
                key,
                new StonecutterRecipe(
                    new net.minecraft.world.item.crafting.Recipe.CommonInfo(true),
                    Ingredient.of(input),
                    new ItemStackTemplate(BuiltInRegistries.ITEM.wrapAsHolder(output), newRecipe.getRight(), DataComponentPatch.EMPTY)
                )
            ));
        }

    }

}
