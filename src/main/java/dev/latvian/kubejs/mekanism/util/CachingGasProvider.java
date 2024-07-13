package dev.latvian.kubejs.mekanism.util;

import mekanism.api.MekanismAPI;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.providers.IGasProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class CachingGasProvider implements IGasProvider {

	private final ResourceLocation id;
	private Supplier<Gas> getter;
	private Gas gas = MekanismAPI.EMPTY_GAS;

	public CachingGasProvider(ResourceLocation id) {
		this.id = id;
		this.getter = () -> Gas.getFromRegistry(id);
	}

	@Nonnull
	@Override
	public Gas getChemical() {
		if (gas.isEmptyType()) {
			gas = getter.get().getChemical();
			if (gas.isEmptyType()) {
				throw new IllegalStateException("Gas with id %s does not exist or is empty!".formatted(id));
			}
			getter = null;
		}
		return gas;
	}
}