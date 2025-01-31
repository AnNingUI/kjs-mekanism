package dev.latvian.kubejs.mekanism.util;

import mekanism.api.MekanismAPI;
import mekanism.api.gear.ModuleData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;

public class KubeJSMekUntiItemUtils {
    public static ModuleData<?> getModuleById(ResourceLocation id) {
        return MekanismAPI.moduleRegistry().getValue(id);
    }

    public static Item getItemById(ResourceLocation id) {
        return ForgeRegistries.ITEMS.getValue(id);
    }

    public static Collection<ModuleData<?>> getAllModule() {
        return MekanismAPI.moduleRegistry().getValues();
    }
}
