package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.recipes.ingredients.ItemStackIngredient;

public interface CombiningRecipeSchema {
	RecipeKey<OutputItem> OUTPUT = ItemComponents.OUTPUT.key(JsonConstants.OUTPUT);
	RecipeKey<ItemStackIngredient> MAIN_INPUT = MekComponents.INPUT_ITEM.key(JsonConstants.MAIN_INPUT);
	RecipeKey<ItemStackIngredient> EXTRA_INPUT = MekComponents.INPUT_ITEM.key(JsonConstants.EXTRA_INPUT);

	RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, OUTPUT, MAIN_INPUT, EXTRA_INPUT);
}
