package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;

public interface PigmentMixingRecipeSchema {
    RecipeKey<ChemicalStackIngredient.PigmentStackIngredient> LEFT_INPUT = MekComponents.PIGMENT_INPUT.key(JsonConstants.LEFT_INPUT);
    RecipeKey<ChemicalStackIngredient.PigmentStackIngredient> RIGHT_INPUT = MekComponents.PIGMENT_INPUT.key(JsonConstants.RIGHT_INPUT);
    RecipeKey<PigmentStack> OUTPUT = MekComponents.PIGMENT_OUTPUT.key(JsonConstants.OUTPUT);

    RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, LEFT_INPUT, RIGHT_INPUT, OUTPUT);
}
