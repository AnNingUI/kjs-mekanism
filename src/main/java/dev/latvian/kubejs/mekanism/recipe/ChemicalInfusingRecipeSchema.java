package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;

public interface ChemicalInfusingRecipeSchema {
	RecipeKey<GasStack> OUTPUT = MekComponents.GAS_OUTPUT.key(JsonConstants.OUTPUT);
	RecipeKey<ChemicalStackIngredient.GasStackIngredient> LEFT_INPUT = MekComponents.GAS_INPUT.key(JsonConstants.LEFT_INPUT);
	RecipeKey<ChemicalStackIngredient.GasStackIngredient> RIGHT_INPUT = MekComponents.GAS_INPUT.key(JsonConstants.RIGHT_INPUT);

	RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, OUTPUT, LEFT_INPUT, RIGHT_INPUT);
}
