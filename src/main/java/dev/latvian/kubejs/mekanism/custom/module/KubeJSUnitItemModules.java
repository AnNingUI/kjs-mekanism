package dev.latvian.kubejs.mekanism.registry;

import mekanism.common.registration.impl.ModuleDeferredRegister;
import mekanism.common.registration.impl.ModuleRegistryObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static dev.latvian.kubejs.mekanism.registry.KubeJSUnitItemBuilder.allIds;
import static dev.latvian.kubejs.mekanism.registry.KubeJSUnitItemBuilder.allModulesRegistered;

public class KubeJSUnitItemModules {
    private KubeJSUnitItemModules() {
    }

    public static Map<ResourceLocation, ModuleRegistryObject<KubeJSUnitItemModule>> allModules = new HashMap<>();
    static {
        allIds.forEach(itemBuilder -> {
            ModuleDeferredRegister MODULES = allModulesRegistered.get(itemBuilder.id);
            ModuleRegistryObject<KubeJSUnitItemModule> itemModules = MODULES.register("module_" + itemBuilder.id.getPath(), () -> new KubeJSUnitItemModule(itemBuilder), () -> KubeJSUnitItemBuilder.ITEM_MAP.get(itemBuilder.id), (builder) -> {
                int maxStackSize = itemBuilder.maxStackSize == 0 ? 1 : itemBuilder.maxStackSize;
                Rarity rarity = itemBuilder.rarity == null ? Rarity.COMMON : itemBuilder.rarity;
                var b = builder.maxStackSize(maxStackSize).rarity(rarity);
                if (itemBuilder.rendersHUD) {
                    b.rendersHUD();
                }
                if (itemBuilder.exclusive != 0) {
                    b.exclusive(itemBuilder.exclusive);
                }
                return builder;
            });
            itemBuilder.setModuleData(itemModules);
            allModules.put(itemBuilder.id, itemModules);
        });
    }

    public static void register(IEventBus eventBus){
        if (!allIds.isEmpty()) {
            Set<ModuleDeferredRegister> modules = new HashSet<>(allModulesRegistered.values());
            modules.forEach(module -> module.createAndRegister(eventBus));
        }
    }
}
