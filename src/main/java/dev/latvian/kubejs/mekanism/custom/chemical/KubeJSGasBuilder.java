package dev.latvian.kubejs.mekanism.custom;

import dev.latvian.kubejs.mekanism.MekanismKubeJSPlugin;
import dev.latvian.kubejs.mekanism.util.CachingGasProvider;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasBuilder;
import mekanism.api.chemical.gas.attribute.GasAttributes;
import mekanism.api.math.FloatingLong;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class KubeJSGasBuilder extends KubeJSChemicalBuilder<Gas, GasBuilder, KubeJSGasBuilder> {

	private ResourceLocation texture = null;

	public KubeJSGasBuilder(ResourceLocation id) {
		super(id);
	}

	@Override
	protected Supplier<GasBuilder> bindBuilder() {
		return () -> texture == null ? GasBuilder.builder() : GasBuilder.builder(texture);
	}

	/**
	 * Sets the custom texture of the slurry.
	 * <b>If you are planning to use a custom texture, this should be the first method you call</b>,
	 * otherwise the default texture will be used.
	 *
	 * @param texture Resource location of the texture.
	 * @return This builder.
	 */
	public KubeJSGasBuilder texture(ResourceLocation texture) {
		this.texture = texture;
		return this;
	}

	/**
	 * Declares that this gas is radioactive.
	 * Due to the nature of radioactive gases, this means this gas will not be accepted
	 * by most chemical containers, see {@link GasAttributes.Radiation#needsValidation()}.
	 *
	 * @param radioactivity The radioactivity of this gas (in Sv/h).
	 * @return This builder.
	 */
	public KubeJSGasBuilder radioactivity(double radioactivity) {
		return with(new GasAttributes.Radiation(radioactivity));
	}

	/**
	 * Declares that this gas is a coolant that may be used inside a fission reactor.
	 *
	 * @param heated          Whether this is the heated form of the coolant.
	 * @param counterpart     The cooled (or heated if this is the cooled form) counterpart of this gas.
	 * @param thermalEnthalpy The thermal enthalpy of this gas, referring to the amount of thermal
	 *                        energy it takes to heat up 1mB of it. (i.e. lower values = more coolant required)
	 * @param conductivity    The thermal conductivity of this gas, this is the fraction of a reactor's heat
	 *                        that may be used to convert this coolant's cool variant to its heated variant at any given time.
	 *                        (should be between 0 and 1)
	 * @return This builder.
	 */
	public KubeJSGasBuilder coolant(boolean heated, ResourceLocation counterpart, double thermalEnthalpy, double conductivity) {
		if (heated) {
			return with(new GasAttributes.HeatedCoolant(new CachingGasProvider(counterpart), thermalEnthalpy, conductivity));
		} else {
			return with(new GasAttributes.CooledCoolant(new CachingGasProvider(counterpart), thermalEnthalpy, conductivity));
		}
	}

	/**
	 * Declares that this gas may be burned as fuel in a gas generator.
	 *
	 * @param burnTicks The amount of time it takes to burn 1mB of this gas.
	 * @param energy    The amount of energy 1mB of this gas burns for.
	 * @return This builder.
	 */
	public KubeJSGasBuilder fuel(int burnTicks, double energy) {
		var density = FloatingLong.createConst(energy);

		if (burnTicks <= 0) {
			throw new IllegalArgumentException("Fuel attributes must burn for at least one tick! Burn Ticks: " + burnTicks);
		}
		if (density.isZero()) {
			throw new IllegalArgumentException("Fuel attributes must have an energy density greater than zero!");
		}
		return with(new GasAttributes.Fuel(() -> burnTicks, () -> density));
	}

	@Override
	public RegistryInfo getRegistryType() {
		return MekanismKubeJSPlugin.GAS;
	}

	@Override
	public Gas createObject() {
		if (barColor == null) {
			return new Gas(builder());
		} else {
			int color = barColor;
			return new Gas(builder()) {
				@Override
				public int getColorRepresentation() {
					return color;
				}
			};
		}
	}
}
