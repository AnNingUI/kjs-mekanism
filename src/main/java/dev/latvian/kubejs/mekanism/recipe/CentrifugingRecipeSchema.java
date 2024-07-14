package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;

public interface CentrifugingRecipeSchema {
    RecipeKey<ChemicalStackIngredient.GasStackIngredient> INPUT = MekComponents.GAS_INPUT.key(JsonConstants.INPUT);
    RecipeKey<GasStack> OUTPUT = MekComponents.GAS_OUTPUT.key(JsonConstants.OUTPUT);

    RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, INPUT, OUTPUT);
}
