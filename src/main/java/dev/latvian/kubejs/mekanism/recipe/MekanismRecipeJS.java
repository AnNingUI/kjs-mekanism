package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;

public class MekanismRecipeJS extends RecipeJS {
	@Override
	public InputItem readInputItem(Object from) {
		throw new RecipeExceptionJS("Mekanism recipes need to use MekComponents.INPUT_ITEM instead of ItemComponents.INPUT");
	}
}