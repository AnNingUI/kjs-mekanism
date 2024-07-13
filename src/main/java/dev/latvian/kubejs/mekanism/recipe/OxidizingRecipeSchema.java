package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.recipes.ingredients.ItemStackIngredient;

public interface OxidizingRecipeSchema {
	RecipeKey<ItemStackIngredient> INPUT = MekComponents.INPUT_ITEM.key(JsonConstants.INPUT);

	RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, INPUT);
}
