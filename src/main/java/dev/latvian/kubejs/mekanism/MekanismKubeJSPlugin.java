package dev.latvian.kubejs.mekanism;

import dev.latvian.kubejs.mekanism.custom.chemical.KubeJSGasBuilder;
import dev.latvian.kubejs.mekanism.custom.chemical.KubeJSInfuseTypeBuilder;
import dev.latvian.kubejs.mekanism.custom.chemical.KubeJSPigmentBuilder;
import dev.latvian.kubejs.mekanism.custom.chemical.KubeJSSlurryBuilder;
import dev.latvian.kubejs.mekanism.util.UnitItemSlots;
import dev.latvian.kubejs.mekanism.custom.item.KubeJSUnitItemBuilder;
import dev.latvian.kubejs.mekanism.recipe.*;
import dev.latvian.kubejs.mekanism.util.ChemicalWrapper;
import dev.latvian.kubejs.mekanism.util.KubeJSModuleUtils;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import mekanism.api.MekanismAPI;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.ModuleData;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;

import static dev.latvian.mods.kubejs.registry.RegistryInfo.ITEM;


@SuppressWarnings({ "unchecked", "rawtypes" })
public class MekanismKubeJSPlugin extends KubeJSPlugin {
	// registry builders for all mekanism chemical subtypes
	public static final RegistryInfo GAS = RegistryInfo.of(MekanismAPI.GAS_REGISTRY_NAME);

	public static final RegistryInfo MODULE_DATA = RegistryInfo.of(MekanismAPI.MODULE_REGISTRY_NAME);

	public static final RegistryInfo INFUSE_TYPE = RegistryInfo.of(MekanismAPI.INFUSE_TYPE_REGISTRY_NAME);
	public static final RegistryInfo PIGMENT = RegistryInfo.of(MekanismAPI.PIGMENT_REGISTRY_NAME);
	public static final RegistryInfo SLURRY = RegistryInfo.of(MekanismAPI.SLURRY_REGISTRY_NAME);

	@Override
	public void init() {
		ITEM.addType("mek_unit", KubeJSUnitItemBuilder.class, KubeJSUnitItemBuilder::new);
		GAS.addType("basic", KubeJSGasBuilder.class, KubeJSGasBuilder::new);
		INFUSE_TYPE.addType("basic", KubeJSInfuseTypeBuilder.class, KubeJSInfuseTypeBuilder::new);
		PIGMENT.addType("basic", KubeJSPigmentBuilder.class, KubeJSPigmentBuilder::new);
		SLURRY.addType("basic", KubeJSSlurryBuilder.Basic.class, KubeJSSlurryBuilder.Basic::new);
		SLURRY.addType("dirty", KubeJSSlurryBuilder.Dirty.class, KubeJSSlurryBuilder.Dirty::new);
		SLURRY.addType("clean", KubeJSSlurryBuilder.Clean.class, KubeJSSlurryBuilder.Clean::new);
	}

	@Override
	public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
		event.namespace(MekanismAPI.MEKANISM_MODID)
				.register("crushing", ItemToItemRecipeSchema.SCHEMA)
				.register("enriching", ItemToItemRecipeSchema.SCHEMA)
				.register("smelting", ItemToItemRecipeSchema.SCHEMA)
				.register("chemical_infusing", ChemicalInfusingRecipeSchema.SCHEMA)
				.register("combining", CombiningRecipeSchema.SCHEMA)
				.register("separating", SeparatingRecipeSchema.SCHEMA)
				.register("washing", WashingRecipeSchema.SCHEMA)
				.register("evaporating", EvaporatingRecipeSchema.SCHEMA)
				.register("centrifuging", CentrifugingRecipeSchema.SCHEMA)
				.register("crystallizing", CrystallizingRecipeSchema.SCHEMA)
				.register("dissolution", ChemicalDissolutionRecipeSchema.SCHEMA)
				.register("compressing", ItemAndGasToItemRecipeSchema.SCHEMA)
				.register("purifying", ItemAndGasToItemRecipeSchema.SCHEMA)
				.register("injecting", ItemAndGasToItemRecipeSchema.SCHEMA)
				.register("nucleosynthesizing", NucleosynthesizingRecipeSchema.SCHEMA)
				.register("energy_conversion", EnergyConversionRecipeSchema.SCHEMA)
				.register("gas_conversion", GasConversionRecipeSchema.SCHEMA)
				.register("oxidizing", OxidizingRecipeSchema.SCHEMA)
				.register("metallurgic_infusing", MetallurgicInfusingRecipeSchema.SCHEMA)
				.register("reaction", PressurizedReactionRecipeSchema.SCHEMA)
				.register("sawing", SawingRecipeSchema.SCHEMA)
				.register("infusion_conversion", ItemToInfuseTypeRecipeSchema.SCHEMA)
				.register("activating", ActivatingRecipeSchema.SCHEMA)
				.register("pigment_mixing", PigmentMixingRecipeSchema.SCHEMA)
				.register("pigment_extracting", PigmentExtractingRecipeSchema.SCHEMA)
				.register("painting", PaintingRecipeSchema.SCHEMA)
				.register("rotary", RotaryRecipeSchema.SCHEMA);
	}

	@Override
	public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
		typeWrappers.registerSimple(ChemicalStackIngredient.GasStackIngredient.class, ChemicalWrapper::ofGasIngredient);
	}

	@Override
	public void registerBindings(BindingsEvent event) {
		event.add("MekUnitItemSlots", UnitItemSlots.Slots.class);
		event.add("ExclusiveFlag", ModuleData.ExclusiveFlag.class);
		event.add("KJSModuleUtils", KubeJSModuleUtils.class);
		event.add("ModuleDamageAbsorbInfo", ICustomModule.ModuleDamageAbsorbInfo.class);
	}
}
