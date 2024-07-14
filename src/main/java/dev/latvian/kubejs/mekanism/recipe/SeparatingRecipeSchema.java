package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import mekanism.api.JsonConstants;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.math.FloatingLong;

public interface SeparatingRecipeSchema {
    RecipeKey<InputFluid> INPUT = FluidComponents.INPUT.key(JsonConstants.INPUT);
    RecipeKey<FloatingLong> ENERGY_MULTIPLIER = MekComponents.FLOATING_LONG.key(JsonConstants.ENERGY_MULTIPLIER).optional(FloatingLong.create(0L));
    RecipeKey<GasStack> LEFT_CHEMICAL_OUTPUT = MekComponents.GAS_OUTPUT.key(JsonConstants.LEFT_GAS_OUTPUT);
    RecipeKey<GasStack> RIGHT_CHEMICAL_OUTPUT = MekComponents.GAS_OUTPUT.key(JsonConstants.RIGHT_GAS_OUTPUT);

    RecipeSchema SCHEMA = new RecipeSchema(MekanismRecipeJS.class, MekanismRecipeJS::new, INPUT, LEFT_CHEMICAL_OUTPUT, RIGHT_CHEMICAL_OUTPUT, ENERGY_MULTIPLIER);
}
