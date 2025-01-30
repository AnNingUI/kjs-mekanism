package dev.latvian.kubejs.mekanism.registry;

import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.config.ModuleConfigItemCreator;

public class KubeJSUnitItemUnit implements ICustomModule<KubeJSUnitItemUnit>  {
    public KubeJSUnitItemBuilder builder;

    public KubeJSUnitItemUnit(KubeJSUnitItemBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void init(IModule<KubeJSUnitItemUnit> module, ModuleConfigItemCreator configItemCreator) {
        ICustomModule.super.init(module, configItemCreator);
    }
}
