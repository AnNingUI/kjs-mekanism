package dev.latvian.kubejs.mekanism.util;

import dev.latvian.kubejs.mekanism.custom.module.KubeJSModuleData;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.math.FloatingLong;
import mekanism.common.config.MekanismConfig;
import mekanism.common.content.gear.ModuleHelper;
import mekanism.common.content.gear.shared.ModuleEnergyUnit;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.item.gear.ItemMekaTool;
import mekanism.common.registries.MekanismModules;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;


public class KubeJSModuleUtils {
    public static @Nullable <MODULE extends ICustomModule<MODULE>> IModule<MODULE> getModuleById(ItemStack stack, ResourceLocation id) {
        return (IModule<MODULE>) ModuleHelper.INSTANCE.load(stack, KubeJSMekUntiItemUtils.getModuleById(id));
    }

    public static @Nullable <MODULE extends ICustomModule<MODULE>> IModule<MODULE> getModuleByClassType(ItemStack stack, ResourceLocation id, Class<MODULE> moduleClass) {
        var module = getModuleById(stack, id);
        if (module == null || !moduleClass.isInstance(module.getCustomInstance())) {
            return null;
        }
        return (IModule<MODULE>) module;
    }

    public static IModule<KubeJSModuleData> getModuleByKjs(ItemStack stack, ResourceLocation id) {
        return getModuleByClassType(stack, id, KubeJSModuleData.class);
    }

    public static FloatingLong getMaxEnergy(ItemStack stack) {
        if (stack.getItem() instanceof ItemMekaTool) {
            IModule<ModuleEnergyUnit> module = ModuleHelper.get().load(stack, MekanismModules.ENERGY_UNIT);
            return module == null ? MekanismConfig.gear.mekaToolBaseEnergyCapacity.get() : module.getCustomInstance().getEnergyCapacity(module);
        } else if (stack.getItem() instanceof ItemMekaSuitArmor) {
            IModule<ModuleEnergyUnit> module = ModuleHelper.get().load(stack, MekanismModules.ENERGY_UNIT);
            return module == null ? MekanismConfig.gear.mekaSuitBaseEnergyCapacity.get() : module.getCustomInstance().getEnergyCapacity(module);
        } else {
            return FloatingLong.ZERO;
        }
    }

    public static FloatingLong getChargeRate(ItemStack stack) {
        if (stack.getItem() instanceof ItemMekaTool) {
            IModule<ModuleEnergyUnit> module = ModuleHelper.get().load(stack, MekanismModules.ENERGY_UNIT);
            return module == null ? MekanismConfig.gear.mekaToolBaseChargeRate.get() : module.getCustomInstance().getChargeRate(module);
        } else if (stack.getItem() instanceof ItemMekaSuitArmor) {
            IModule<ModuleEnergyUnit> module = ModuleHelper.get().load(stack, MekanismModules.ENERGY_UNIT);
            return module == null ? MekanismConfig.gear.mekaSuitBaseChargeRate.get() : module.getCustomInstance().getChargeRate(module);
        } else {
            return FloatingLong.ZERO;
        }
    }
}
