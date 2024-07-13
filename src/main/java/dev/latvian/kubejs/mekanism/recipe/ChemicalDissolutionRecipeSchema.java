package dev.latvian.kubejs.mekanism.recipe;


import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;

public interface ChemicalDissolutionRecipeSchema {
	RecipeKey<ChemicalStack<?>> OUTPUT = MekComponents.BOXED_CHEMICAL_OUTPUT.key(JsonConstants.OUTPUT);
	RecipeKey<ChemicalStackIngredient.GasStackIngredient> CHEMICAL_INPUT = MekComponents.GAS_INPUT.key(JsonConstants.GAS_INPUT);
	RecipeKey<ItemStackIngredient> ITEM_INPUT = MekComponents.INPUT_ITEM.key(JsonConstants.ITEM_INPUT);

	RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, OUTPUT, CHEMICAL_INPUT, ITEM_INPUT);
}
