package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.recipes.ingredients.ItemStackIngredient;

public interface PigmentExtractingRecipeSchema {
    RecipeKey<ItemStackIngredient> INPUT = MekComponents.INPUT_ITEM.key(JsonConstants.INPUT);
    RecipeKey<PigmentStack> OUTPUT = MekComponents.PIGMENT_OUTPUT.key(JsonConstants.OUTPUT);

    RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, INPUT, OUTPUT);
}
