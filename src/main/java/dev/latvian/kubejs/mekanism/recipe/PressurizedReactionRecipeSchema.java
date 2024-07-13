package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.recipes.ingredients.ItemStackIngredient;

public interface PressurizedReactionRecipeSchema {
	RecipeKey<ItemStackIngredient> ITEM_INPUT = MekComponents.INPUT_ITEM.key(JsonConstants.ITEM_INPUT);
	RecipeKey<OutputItem> ITEM_OUTPUT = ItemComponents.OUTPUT.key(JsonConstants.ITEM_OUTPUT).optional(OutputItem.EMPTY);

	RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, ITEM_INPUT, ITEM_OUTPUT);
}
