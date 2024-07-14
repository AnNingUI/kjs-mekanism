package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;

public interface EvaporatingRecipeSchema {
    RecipeKey<InputFluid> INPUT = FluidComponents.INPUT.key(JsonConstants.INPUT);
    RecipeKey<OutputFluid> OUTPUT = FluidComponents.OUTPUT.key(JsonConstants.OUTPUT);

    RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, INPUT, OUTPUT);
}
