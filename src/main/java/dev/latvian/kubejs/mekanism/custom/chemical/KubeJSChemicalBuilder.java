package dev.latvian.kubejs.mekanism.custom;

import dev.latvian.mods.kubejs.registry.BuilderBase;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalBuilder;
import mekanism.api.chemical.attribute.ChemicalAttribute;
import net.minecraft.resources.ResourceLocation;
import oshi.util.Memoizer;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Base class for all custom chemical builders.
 *
 * @param <C> - The type of chemical this builder creates.
 * @param <B> - The type of (Mekanism) builder this builder will use internally.
 * @param <S> - The "self" type of this builder.
 */
public abstract class KubeJSChemicalBuilder<C extends Chemical<C>,
		B extends ChemicalBuilder<C, B>,
		S extends KubeJSChemicalBuilder<C, B, S>> extends BuilderBase<C> {

	private final Supplier<B> builder;

	@Nullable
	protected Integer barColor;

	protected KubeJSChemicalBuilder(ResourceLocation id) {
		super(id);
		builder = Memoizer.memoize(bindBuilder());
	}

	protected abstract Supplier<B> bindBuilder();

	/**
	 * Adds a (custom) attribute to this chemical.
	 *
	 * @param attribute The attribute to add.
	 * @return This builder.
	 *
	 * @apiNote Most if not all the builtin attributes should be available within the relevant chemical builders
	 * (e.g. {@link KubeJSGasBuilder#fuel(int, double)}), but if you need to add an entirely custom attribute,
	 * you can still use this method.
	 *
	 * (Note that there is **no** type wrapping for these, so you WILL need to use native Java access.)
	 */
	public S with(ChemicalAttribute attribute) {
		builder().with(attribute);
		return self();
	}

	/**
	 * Tints the texture of this chemical with the given color.
	 * @param color - The color to tint the texture with, in ARGB format.
	 * @return This builder.
	 */
	public S color(int color) {
		builder().tint(color);
		return self();
	}

	/**
	 * Hides this chemical from JEI and the list of creative chemical tanks.
	 * @return This builder.
	 */
	public S hide() {
		builder().hidden();
		return self();
	}

	/**
	 * Sets the "color representation" of this chemical.
	 * In practice, this refers to things like the color of the durability bar on chemical tanks.
	 *
	 * If you do not set this, the color will be determined by the tint applied to the texture,
	 * see {@link #color(int)}.
	 *
	 * @param color - The color to represent this chemical with, in ARGB format.
	 * @return This builder.
	 */
	public S barColor(int color) {
		barColor = color;
		return self();
	}

	protected final B builder() {
		return builder.get();
	}

	@SuppressWarnings("unchecked")
	protected S self() {
		return (S) this;
	}
}
