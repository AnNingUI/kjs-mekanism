package dev.latvian.kubejs.mekanism.custom;

import dev.latvian.kubejs.mekanism.MekanismKubeJSPlugin;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class KubeJSPigmentBuilder extends KubeJSChemicalBuilder<Pigment, PigmentBuilder, KubeJSPigmentBuilder> {

	private ResourceLocation texture = null;

	public KubeJSPigmentBuilder(ResourceLocation id) {
		super(id);
	}

	@Override
	protected Supplier<PigmentBuilder> bindBuilder() {
		return () -> texture == null ? PigmentBuilder.builder() : PigmentBuilder.builder(texture);
	}

	/**
	 * Sets the custom texture of the slurry.
	 * <b>If you are planning to use a custom texture, this should be the first method you call</b>,
	 * otherwise the default texture will be used.
	 *
	 * @param texture Resource location of the texture.
	 * @return This builder.
	 */
	public KubeJSPigmentBuilder texture(ResourceLocation texture) {
		this.texture = texture;
		return this;
	}

	@Override
	public RegistryInfo getRegistryType() {
		return MekanismKubeJSPlugin.PIGMENT;
	}

	@Override
	public Pigment createObject() {
		if (barColor == null) {
			return new Pigment(builder());
		} else {
			int color = barColor;
			return new Pigment(builder()) {
				@Override
				public int getColorRepresentation() {
					return color;
				}
			};
		}
	}
}
