package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.recipes.ingredients.ItemStackIngredient;

public interface SawingRecipeSchema {
	RecipeKey<ItemStackIngredient> INPUT = MekComponents.INPUT_ITEM.key(JsonConstants.INPUT);
	RecipeKey<OutputItem> MAIN_OUTPUT = ItemComponents.OUTPUT.key(JsonConstants.MAIN_OUTPUT).optional(OutputItem.EMPTY);
	RecipeKey<OutputItem> SECONDARY_OUTPUT = MekComponents.SECONDARY_OUTPUT_COMPONENT.key(JsonConstants.SECONDARY_OUTPUT).optional(OutputItem.EMPTY);

	RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, INPUT, MAIN_OUTPUT, SECONDARY_OUTPUT);
}
