package dev.latvian.kubejs.mekanism.util;

import dev.latvian.kubejs.mekanism.custom.module.KubeJSModuleData;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.common.content.gear.ModuleHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;


public class KubeJSModuleUtils {
    public @Nullable <MODULE extends ICustomModule<MODULE>> IModule<MODULE> getModuleById(ItemStack stack, ResourceLocation id) {
        return (IModule<MODULE>) ModuleHelper.INSTANCE.load(stack, KubeJSMekUntiItemUtils.getModuleById(id));
    }

    public @Nullable <MODULE extends ICustomModule<MODULE>> IModule<MODULE> getModuleByClassType(ItemStack stack, ResourceLocation id, Class<MODULE> moduleClass) {
        var module = getModuleById(stack, id);
        if (module == null || !moduleClass.isInstance(module.getCustomInstance())) {
            return null;
        }
        return (IModule<MODULE>) module;
    }

    public IModule<KubeJSModuleData> getModuleByKjs(ItemStack stack, ResourceLocation id) {
        return getModuleByClassType(stack, id, KubeJSModuleData.class);
    }
}
