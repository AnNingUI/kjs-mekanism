package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;

public interface WashingRecipeSchema {
    RecipeKey<InputFluid> INPUT_FLUID = FluidComponents.INPUT.key(JsonConstants.FLUID_INPUT);
    RecipeKey<ChemicalStackIngredient.SlurryStackIngredient> INPUT_SLURRY = MekComponents.SLURRY_INPUT.key(JsonConstants.SLURRY_INPUT);
    RecipeKey<SlurryStack> OUTPUT_SLURRY = MekComponents.SLURRY_OUTPUT.key(JsonConstants.OUTPUT);

    RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, INPUT_FLUID, INPUT_SLURRY, OUTPUT_SLURRY);
}
