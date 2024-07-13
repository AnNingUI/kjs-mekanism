package dev.latvian.kubejs.mekanism.custom;

import dev.latvian.kubejs.mekanism.MekanismKubeJSPlugin;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.util.Tags;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public sealed abstract class KubeJSSlurryBuilder extends KubeJSChemicalBuilder<Slurry, SlurryBuilder, KubeJSSlurryBuilder> {
	public KubeJSSlurryBuilder(ResourceLocation id) {
		super(id);
	}

	/**
	 * Shortcut for {@link #oreTag(ResourceLocation)}.
	 */
	public KubeJSSlurryBuilder ore(ResourceLocation ore) {
		return oreTag(ore);
	}

	/**
	 * Sets the tag of the ore this slurry is made from.
	 *
	 * @param ore The ore tag, as a resource location.
	 * @return This builder.
	 */
	public KubeJSSlurryBuilder oreTag(ResourceLocation ore) {
		builder().ore(Tags.item(ore));
		return self();
	}

	@Override
	public RegistryInfo getRegistryType() {
		return MekanismKubeJSPlugin.SLURRY;
	}

	@Override
	public Slurry createObject() {
		if (barColor == null) {
			return new Slurry(builder());
		} else {
			int color = barColor;
			return new Slurry(builder()) {
				@Override
				public int getColorRepresentation() {
					return color;
				}
			};
		}
	}

	/**
	 * Builder class for "basic" (in this case, custom) slurries.
	 * <b>Requires</b> a custom texture to be set using {@link #texture(ResourceLocation)}.
	 *
	 * @apiNote You should likely be assigning this a custom {@link #barColor(int)} if you're planning to use a custom texture,
	 * as by default it will use the tint applied to the texture, which you probably don't want.
	 */
	public static final class Basic extends KubeJSSlurryBuilder {
		private ResourceLocation texture = null;

		public Basic(ResourceLocation id) {
			super(id);
		}

		@Override
		protected Supplier<SlurryBuilder> bindBuilder() {
			return () -> {
				if (texture == null) {
					throw new IllegalStateException("ERROR: Texture is required for 'basic' slurries! Please use builder.texture(...) to set it.");
				} else {
					return SlurryBuilder.builder(texture);
				}
			};
		}

		/**
		 * Sets the custom texture of the slurry.
		 * <b>This should be the first method called on this builder</b>,
		 * to avoid creating an internal builder without a texture.
		 *
		 * @param texture Resource location of the texture.
		 * @return This builder.
		 */
		public Basic texture(ResourceLocation texture) {
			this.texture = texture;
			return this;
		}
	}

	/**
	 * Builder class for "dirty" slurries.
	 * <p>
	 * This is essentially just a wrapper around "basic" slurries
	 * using Mekanism's provided "dirty" texture, but we choose to provide
	 * it as a "shortcut" and to mirror Mekanism's basic API structure.
	 */
	public static final class Dirty extends KubeJSSlurryBuilder {
		public Dirty(ResourceLocation id) {
			super(id);
		}

		@Override
		protected Supplier<SlurryBuilder> bindBuilder() {
			return SlurryBuilder::dirty;
		}
	}

	/**
	 * Builder class for "dirty" slurries.
	 * <p>
	 * This is essentially just a wrapper around "basic" slurries
	 * using Mekanism's provided "clean" texture, but we choose to provide
	 * it as a "shortcut" and to mirror Mekanism's basic API structure.
	 */
	public static final class Clean extends KubeJSSlurryBuilder {
		public Clean(ResourceLocation id) {
			super(id);
		}

		@Override
		protected Supplier<SlurryBuilder> bindBuilder() {
			return SlurryBuilder::clean;
		}
	}
}
