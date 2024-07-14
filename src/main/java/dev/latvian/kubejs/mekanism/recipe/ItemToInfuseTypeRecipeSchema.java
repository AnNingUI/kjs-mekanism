package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.recipes.ingredients.ItemStackIngredient;

public interface ItemToInfuseTypeRecipeSchema {
    RecipeKey<ItemStackIngredient> INPUT_ITEM = MekComponents.INPUT_ITEM.key(JsonConstants.INPUT);
    RecipeKey<InfusionStack> CHEMICAL_OUTPUT = MekComponents.INFUSE_TYPE_OUTPUT.key(JsonConstants.OUTPUT);

    RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, INPUT_ITEM, CHEMICAL_OUTPUT);
}
