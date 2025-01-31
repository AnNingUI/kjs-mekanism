package dev.latvian.kubejs.mekanism.custom.module;

import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.ModuleData;

public class KubeJSModuleData extends ModuleData<KubeJSModuleData> implements ICustomModule<KubeJSModuleData> {
    public ModuleDataBuilder<KubeJSModuleData> builder;
    public KubeJSModuleData(ModuleDataBuilder<KubeJSModuleData> pBuilder) {
        super(pBuilder);
        this.builder = pBuilder;
    }
}
