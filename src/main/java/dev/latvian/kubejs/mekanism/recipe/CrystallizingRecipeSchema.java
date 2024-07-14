package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.ChemicalType;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;

public interface CrystallizingRecipeSchema {
	RecipeKey<OutputItem> OUTPUT = ItemComponents.OUTPUT.key(JsonConstants.OUTPUT);
	RecipeKey<ChemicalStackIngredient<?, ?>> INPUT = MekComponents.ANY_CHEMICAL_INPUT.key(JsonConstants.INPUT);
	RecipeKey<ChemicalType> CHEMICAL_TYPE = MekComponents.CHEMICAL_TYPE.key(JsonConstants.CHEMICAL_TYPE);

	RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, CHEMICAL_TYPE, OUTPUT, INPUT);
}