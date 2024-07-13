package dev.latvian.kubejs.mekanism.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.latvian.kubejs.mekanism.util.ChemicalWrapper;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.*;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.typings.desc.DescriptionContext;
import dev.latvian.mods.kubejs.typings.desc.TypeDescJS;
import dev.latvian.mods.kubejs.util.JsonIO;
import dev.latvian.mods.kubejs.util.MapJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.ChemicalType;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.chemical.merged.BoxedChemicalStack;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.api.math.FloatingLong;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.recipe.ingredient.creator.ItemStackIngredientCreator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface MekComponents {
	RecipeComponent<ItemStackIngredient> INPUT_ITEM = new RecipeComponent<>() {
		@Override
		public ComponentRole role() {
			return ComponentRole.INPUT;
		}

		@Override
		public Class<?> componentClass() {
			return ItemStackIngredient.class;
		}

		@Override
		public JsonElement write(RecipeJS recipe, ItemStackIngredient value) {
			return value.serialize();
		}

		@Override
		public ItemStackIngredient read(RecipeJS recipe, Object from) {
			if (from instanceof ItemStackIngredient in) {
				return in;
			} else if (from instanceof JsonElement json) {
				return IngredientCreatorAccess.item().deserialize(json);
			}

			var in = InputItem.of(from);
			return ItemStackIngredientCreator.INSTANCE.from(in.ingredient, in.count);
		}

		@Override
		public boolean isInput(RecipeJS recipe, ItemStackIngredient value, ReplacementMatch match) {
			if (match instanceof ItemMatch m) {
				if (value instanceof ItemStackIngredientCreator.SingleItemStackIngredient in) {
					return m.contains(in.getInputRaw());
				} else if (value instanceof ItemStackIngredientCreator.MultiItemStackIngredient in) {
					for (var in1 : in.getIngredients()) {
						if (m.contains(((ItemStackIngredientCreator.SingleItemStackIngredient) in1).getInputRaw())) {
							return true;
						}
					}
				}
			}

			return false;
		}

		@Override
		public ItemStackIngredient replaceInput(RecipeJS recipe, ItemStackIngredient original, ReplacementMatch match, InputReplacement with) {
			if (match instanceof ItemMatch m) {
				if (original instanceof ItemStackIngredientCreator.SingleItemStackIngredient in) {
					if (m.contains(in.getInputRaw())) {
						var item = InputItem.of(with.replaceInput(recipe, match, InputItem.of(in.getInputRaw(), in.getAmountRaw())));
						return ItemStackIngredientCreator.INSTANCE.from(item.ingredient, item.count);
					}
				} else if (original instanceof ItemStackIngredientCreator.MultiItemStackIngredient in) {
					var ingredients = in.getIngredients();

					for (var in1 : ingredients) {
						if (m.contains(((ItemStackIngredientCreator.SingleItemStackIngredient) in1).getInputRaw())) {
							var repl = new ItemStackIngredient[ingredients.size()];

							for (int i = 0; i < repl.length; i++) {
								var in2 = (ItemStackIngredientCreator.SingleItemStackIngredient) ingredients.get(i);

								if (m.contains(in2.getInputRaw())) {
									var item = InputItem.of(with.replaceInput(recipe, match, InputItem.of(in2.getInputRaw(), in2.getAmountRaw())));
									repl[i] = ItemStackIngredientCreator.INSTANCE.from(item.ingredient, item.count);
								} else {
									repl[i] = in2;
								}
							}

							return ItemStackIngredientCreator.INSTANCE.createMulti(repl);
						}
					}
				}

				return original;
			}

			return original;
		}
	};

	RecipeComponent<ChemicalType> CHEMICAL_TYPE = new EnumComponent<>(ChemicalType.class, ChemicalType::getSerializedName, (c, s) -> ChemicalType.fromString(s));

	RecipeComponent<ChemicalStackIngredient<?, ?>> ANY_CHEMICAL_INPUT = new RecipeComponent<>() {
		@Override
		public ComponentRole role() {
			return ComponentRole.INPUT;
		}

		@Override
		public Class<?> componentClass() {
			return ChemicalStackIngredient.class;
		}

		@Override
		public JsonElement write(RecipeJS recipe, ChemicalStackIngredient<?, ?> value) {
			return value.serialize();
		}

		@Override
		public TypeDescJS constructorDescription(DescriptionContext ctx) {
			return TypeDescJS.any(
					chemicalWithAmount(ChemicalWrapper.GAS, ctx),
					chemicalWithAmount(ChemicalWrapper.INFUSE_TYPE, ctx),
					chemicalWithAmount(ChemicalWrapper.PIGMENT, ctx),
					chemicalWithAmount(ChemicalWrapper.SLURRY, ctx)
			);
		}

		@Override
		public ChemicalStackIngredient<?, ?> read(RecipeJS recipe, Object from) {
			if (from instanceof ChemicalStackIngredient<?, ?> in) {
				return in;
			} else if (from instanceof Map<?, ?> || from instanceof JsonObject) {
				var map = MapJS.of(from);
				var cw = map == null || map.isEmpty() ? null : ChemicalWrapper.find(map);

				if (cw != null) {
					var id = map.get(cw.key());
					var amount = map.containsKey(JsonConstants.AMOUNT) ? ((Number) map.get(JsonConstants.AMOUNT)).longValue() : cw.defaultAmount();
					if (id != null) {
						return cw.ingredient(id.toString(), amount);
					} else {
						if (map.containsKey(JsonConstants.TAG)) {
							return cw.creator().from(TagKey.create(UtilsJS.cast(cw.registry()), new ResourceLocation(map.get(JsonConstants.TAG).toString())), amount);
						}
					}
				}
			}

			throw new RecipeExceptionJS("Cannot read chemical stack ingredient from " + from);
		}

		@Override
		public void writeToJson(RecipeJS recipe, RecipeComponentValue<ChemicalStackIngredient<?, ?>> cv, JsonObject json) {
			RecipeComponent.super.writeToJson(recipe, cv, json);
			json.addProperty(JsonConstants.CHEMICAL_TYPE, ChemicalType.getTypeFor(cv.value).getSerializedName());
		}

		@Override
		public void readFromJson(RecipeJS recipe, RecipeComponentValue<ChemicalStackIngredient<?, ?>> cv, JsonObject json) {
			ChemicalType chemicalType = SerializerHelper.getChemicalType(json);
			cv.value = IngredientCreatorAccess.getCreatorForType(chemicalType).deserialize(json.get(cv.key.name));
		}

		@Override
		public void readFromMap(RecipeJS recipe, RecipeComponentValue<ChemicalStackIngredient<?, ?>> cv, Map<?, ?> map) {
			ChemicalType chemicalType = ChemicalType.fromString(map.get(JsonConstants.CHEMICAL_TYPE).toString());
			cv.value = IngredientCreatorAccess.getCreatorForType(chemicalType).deserialize(JsonIO.of(map.get(cv.key.name)));
		}
	};

	RecipeComponent<ChemicalStack<?>> BOXED_CHEMICAL_OUTPUT = new RecipeComponent<>() {
		@Override
		public ComponentRole role() {
			return ComponentRole.OUTPUT;
		}

		@Override
		public Class<?> componentClass() {
			return ChemicalStack.class;
		}

		@Override
		public JsonElement write(RecipeJS recipe, ChemicalStack<?> value) {
			return SerializerHelper.serializeBoxedChemicalStack(BoxedChemicalStack.box(value));
		}

		@Override
		public TypeDescJS constructorDescription(DescriptionContext ctx) {
			return TypeDescJS.any(
					chemicalWithAmount(ChemicalWrapper.GAS, ctx),
					chemicalWithAmount(ChemicalWrapper.INFUSE_TYPE, ctx),
					chemicalWithAmount(ChemicalWrapper.PIGMENT, ctx),
					chemicalWithAmount(ChemicalWrapper.SLURRY, ctx)
			);
		}

		@Override
		public ChemicalStack<?> read(RecipeJS recipe, Object from) {
			if (from instanceof ChemicalStack<?> in) {
				return in;
			} else if (from instanceof Map<?, ?> || from instanceof JsonObject) {
				var map = MapJS.of(from);
				var cw = map == null || map.isEmpty() ? null : ChemicalWrapper.find(map);

				if (cw != null && map.containsKey(cw.key())) {
					var id = map.get(cw.key());
					var amount = map.containsKey(JsonConstants.AMOUNT) ? ((Number) map.get(JsonConstants.AMOUNT)).longValue() : cw.defaultAmount();
					return cw.stack(id.toString(), amount);
				}
			}

			throw new RecipeExceptionJS("Cannot read boxed chemical stack from " + from);
		}
	};

	@NotNull
	private static TypeDescJS chemicalWithAmount(ChemicalWrapper<?, ?, ?> wrapper, DescriptionContext ctx) {
		return TypeDescJS.object().add(wrapper.key(), wrapper.describe(ctx)).add(JsonConstants.AMOUNT, TypeDescJS.NUMBER, true);
	}

	RecipeComponent<ChemicalStackIngredient.GasStackIngredient> GAS_INPUT = new ChemicalWrapper.InputComponent<>(ChemicalWrapper.GAS);
	RecipeComponent<ChemicalStackIngredient.InfusionStackIngredient> INFUSE_TYPE_INPUT = new ChemicalWrapper.InputComponent<>(ChemicalWrapper.INFUSE_TYPE);
	RecipeComponent<ChemicalStackIngredient.PigmentStackIngredient> PIGMENT_INPUT = new ChemicalWrapper.InputComponent<>(ChemicalWrapper.PIGMENT);
	RecipeComponent<ChemicalStackIngredient.SlurryStackIngredient> SLURRY_INPUT = new ChemicalWrapper.InputComponent<>(ChemicalWrapper.SLURRY);

	RecipeComponent<GasStack> GAS_OUTPUT = new ChemicalWrapper.OutputComponent<>(ChemicalWrapper.GAS);
	RecipeComponent<InfusionStack> INFUSE_TYPE_OUTPUT = new ChemicalWrapper.OutputComponent<>(ChemicalWrapper.INFUSE_TYPE);
	RecipeComponent<PigmentStack> PIGMENT_OUTPUT = new ChemicalWrapper.OutputComponent<>(ChemicalWrapper.PIGMENT);
	RecipeComponent<SlurryStack> SLURRY_OUTPUT = new ChemicalWrapper.OutputComponent<>(ChemicalWrapper.SLURRY);

	RecipeComponent<OutputItem> SECONDARY_OUTPUT_COMPONENT = new RecipeComponentWithParent<>() {
		@Override
		public RecipeComponent<OutputItem> parentComponent() {
			return ItemComponents.OUTPUT;
		}

		@Override
		public void writeToJson(RecipeJS recipe, RecipeComponentValue<OutputItem> cv, JsonObject json) {
			ItemComponents.OUTPUT.writeToJson(recipe, cv, json);

			if (cv.value.hasChance()) {
				json.addProperty(JsonConstants.SECONDARY_CHANCE, cv.value.getChance());
			}
		}

		@Override
		public void readFromJson(RecipeJS recipe, RecipeComponentValue<OutputItem> cv, JsonObject json) {
			ItemComponents.OUTPUT.readFromJson(recipe, cv, json);

			if (cv.value != null && json.has(JsonConstants.SECONDARY_CHANCE)) {
				cv.value = cv.value.withChance(json.get(JsonConstants.SECONDARY_CHANCE).getAsDouble());
			}
		}

		@Override
		public void readFromMap(RecipeJS recipe, RecipeComponentValue<OutputItem> cv, Map<?, ?> map) {
			ItemComponents.OUTPUT.readFromMap(recipe, cv, map);

			if (cv.value != null && map.containsKey(JsonConstants.SECONDARY_CHANCE)) {
				cv.value = cv.value.withChance(((Number) map.get(JsonConstants.SECONDARY_CHANCE)).doubleValue());
			}
		}
	};

	RecipeComponent<FloatingLong> FLOATING_LONG = new RecipeComponent<>() {
		@Override
		public Class<?> componentClass() {
			return FloatingLong.class;
		}

		@Override
		public JsonElement write(RecipeJS recipe, FloatingLong value) {
			return new JsonPrimitive(value);
		}

		@Override
		public TypeDescJS constructorDescription(DescriptionContext ctx) {
			return TypeDescJS.NUMBER;
		}

		@Override
		public FloatingLong read(RecipeJS recipe, Object from) {
			if (from instanceof FloatingLong n) {
				return n;
			} else if (from instanceof Number n) {
				return FloatingLong.createConst(n.doubleValue());
			}

			try {
				return FloatingLong.parseFloatingLong(from instanceof JsonElement json ? json.getAsNumber().toString() : from.toString(), true);
			} catch (Exception ex) {
				throw new RecipeExceptionJS("Expected floating long to be a positive decimal number");
			}
		}
	};
}
