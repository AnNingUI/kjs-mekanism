package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;

public interface ItemAndGasToItemRecipeSchema {
	RecipeKey<OutputItem> OUTPUT = ItemComponents.OUTPUT.key(JsonConstants.OUTPUT);
	RecipeKey<ItemStackIngredient> ITEM_INPUT = MekComponents.INPUT_ITEM.key(JsonConstants.ITEM_INPUT);
	RecipeKey<ChemicalStackIngredient.GasStackIngredient> CHEMICAL_INPUT = MekComponents.GAS_INPUT.key(JsonConstants.CHEMICAL_INPUT);

	RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, OUTPUT, ITEM_INPUT, CHEMICAL_INPUT);
}
