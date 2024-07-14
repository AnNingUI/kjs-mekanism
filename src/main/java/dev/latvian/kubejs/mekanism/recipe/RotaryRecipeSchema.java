package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.fluid.EmptyFluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.OrRecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentBuilder;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentBuilderMap;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;

public interface RotaryRecipeSchema {
    RecipeKey<InputFluid> FLUID_INPUT = FluidComponents.INPUT.key(JsonConstants.FLUID_INPUT).optional(EmptyFluidStackJS.INSTANCE);
    RecipeKey<GasStack> CHEMICAL_OUTPUT = MekComponents.GAS_OUTPUT.key(JsonConstants.GAS_OUTPUT).optional(GasStack.EMPTY);
    RecipeKey<ChemicalStackIngredient.GasStackIngredient> CHEMICAL_INPUT = MekComponents.GAS_INPUT.key(JsonConstants.GAS_INPUT)
            .defaultOptional();
    RecipeKey<OutputFluid> FLUID_OUTPUT = FluidComponents.OUTPUT.key(JsonConstants.FLUID_OUTPUT).optional(EmptyFluidStackJS.INSTANCE);

    RecipeSchema SCHEMA = new RecipeSchema(RotaryRecipeJS.class, RotaryRecipeJS::new, FLUID_INPUT, CHEMICAL_OUTPUT, CHEMICAL_INPUT, FLUID_OUTPUT)
            .constructor();
}

