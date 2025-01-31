package dev.latvian.kubejs.mekanism.custom;

import dev.latvian.kubejs.mekanism.MekanismKubeJSPlugin;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfuseTypeBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class KubeJSInfuseTypeBuilder extends KubeJSChemicalBuilder<InfuseType, InfuseTypeBuilder, KubeJSInfuseTypeBuilder> {

	private ResourceLocation texture = null;

	public KubeJSInfuseTypeBuilder(ResourceLocation id) {
		super(id);
	}

	@Override
	protected Supplier<InfuseTypeBuilder> bindBuilder() {
		return () -> texture == null ? InfuseTypeBuilder.builder() : InfuseTypeBuilder.builder(texture);
	}

	/**
	 * Sets the custom texture of the slurry.
	 * <b>If you are planning to use a custom texture, this should be the first method you call</b>,
	 * otherwise the default texture will be used.
	 *
	 * @param texture Resource location of the texture.
	 * @return This builder.
	 */
	public KubeJSInfuseTypeBuilder texture(ResourceLocation texture) {
		this.texture = texture;
		return this;
	}

	@Override
	public RegistryInfo getRegistryType() {
		return MekanismKubeJSPlugin.INFUSE_TYPE;
	}

	@Override
	public InfuseType createObject() {
		if (barColor == null) {
			return new InfuseType(builder());
		} else {
			int color = barColor;
			return new InfuseType(builder()) {
				@Override
				public int getColorRepresentation() {
					return color;
				}
			};
		}
	}
}
