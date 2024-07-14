package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.fluid.EmptyFluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.math.FloatingLong;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;

public interface PressurizedReactionRecipeSchema {
	RecipeKey<ItemStackIngredient> ITEM_INPUT = MekComponents.INPUT_ITEM.key(JsonConstants.ITEM_INPUT);
	RecipeKey<InputFluid> FLUID_INPUT = FluidComponents.INPUT.key(JsonConstants.FLUID_INPUT).optional(EmptyFluidStackJS.INSTANCE);
	RecipeKey<FloatingLong> REQ_ENERGY = MekComponents.FLOATING_LONG.key(JsonConstants.ENERGY_REQUIRED).optional(FloatingLong.create(0L));
	RecipeKey<Integer> DURATION = NumberComponent.INT.key(JsonConstants.DURATION).optional(100);
	RecipeKey<OutputItem> ITEM_OUTPUT = ItemComponents.OUTPUT.key(JsonConstants.ITEM_OUTPUT).optional(OutputItem.EMPTY);
	RecipeKey<GasStack> CHEMICAL_OUTPUT = MekComponents.GAS_OUTPUT.key(JsonConstants.GAS_OUTPUT).optional(GasStack.EMPTY);

	RecipeKey<ChemicalStackIngredient.GasStackIngredient> CHEMICAL_INPUT = MekComponents.GAS_INPUT.key(JsonConstants.GAS_INPUT);

	RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new,
			ITEM_INPUT, CHEMICAL_INPUT, FLUID_INPUT, ITEM_OUTPUT, CHEMICAL_OUTPUT, DURATION, REQ_ENERGY);
}
