package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;

public class RotaryRecipeJS extends MekanismRecipeJS {
    public RotaryRecipeJS decondensentrating(InputFluid fluidInput, GasStack gasOutput) {
        setValue(RotaryRecipeSchema.FLUID_INPUT, fluidInput);
        setValue(RotaryRecipeSchema.CHEMICAL_OUTPUT, gasOutput);
        return this;
    }

    public RotaryRecipeJS condensentrating(ChemicalStackIngredient.GasStackIngredient gasInput, OutputFluid fluidOutput) {
        setValue(RotaryRecipeSchema.CHEMICAL_INPUT, gasInput);
        setValue(RotaryRecipeSchema.FLUID_OUTPUT, fluidOutput);
        return this;
    }
}
