package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.math.FloatingLong;
import mekanism.api.recipes.ingredients.ItemStackIngredient;

public interface EnergyConversionRecipeSchema {
	RecipeKey<ItemStackIngredient> INPUT = MekComponents.INPUT_ITEM.key(JsonConstants.INPUT);
	RecipeKey<FloatingLong> ENERGY = MekComponents.FLOATING_LONG.key(JsonConstants.OUTPUT);

	RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, INPUT, ENERGY);
}
