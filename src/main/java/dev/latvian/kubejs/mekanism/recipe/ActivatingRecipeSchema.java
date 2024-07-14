package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;

public interface ActivatingRecipeSchema {
    RecipeKey<ChemicalStackIngredient.GasStackIngredient> CHEMICAL_INPUT = MekComponents.GAS_INPUT.key(JsonConstants.INPUT);
    RecipeKey<GasStack> CHEMICAL_OUTPUT = MekComponents.GAS_OUTPUT.key(JsonConstants.OUTPUT);

    RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, CHEMICAL_INPUT, CHEMICAL_OUTPUT);
}
