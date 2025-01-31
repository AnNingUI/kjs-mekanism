package dev.latvian.kubejs.mekanism.custom.module;

import dev.latvian.mods.kubejs.registry.BuilderBase;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.ModuleData;
import net.minecraft.resources.ResourceLocation;
import oshi.util.Memoizer;

import java.util.function.Supplier;

public abstract class AKubeJSModuleDataBuilder<C extends ModuleData<C> & ICustomModule<C>,
        B extends ModuleData.ModuleDataBuilder<C>,
        S extends AKubeJSModuleDataBuilder<C, B, S>> extends BuilderBase<C> {
    protected AKubeJSModuleDataBuilder(ResourceLocation i) {
        super(i);
        builder = Memoizer.memoize(bindBuilder());
    }

    private final Supplier<B> builder;

    protected abstract Supplier<B> bindBuilder();
    protected final B builder() {
        return builder.get();
    }



    @SuppressWarnings("unchecked")
    protected S self() {
        return (S) this;
    }
}
