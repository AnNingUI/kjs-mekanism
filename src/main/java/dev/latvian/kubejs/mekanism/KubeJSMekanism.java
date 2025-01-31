package dev.latvian.kubejs.mekanism;

import dev.latvian.kubejs.mekanism.registry.KubeJSUnitItemModule;
import dev.latvian.kubejs.mekanism.registry.KubeJSUnitItemModules;
import mekanism.api.MekanismIMC;
import mekanism.common.integration.MekanismHooks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static dev.latvian.kubejs.mekanism.registry.KubeJSUnitItemBuilder.allIds;

@Mod(KubeJSMekanism.MOD_ID)
public class KubeJSMekanism {
	public static final String MOD_ID = "kubejs_mekanism";


	public KubeJSMekanism() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		if (!allIds.isEmpty()) {
			KubeJSUnitItemModules.register(modEventBus);
		}
		if (!KubeJSUnitItemModule.allInstances.isEmpty()) {
			modEventBus.addListener(this::onImcQueue);
		}
	}

	public static final MekanismHooks hooks = new MekanismHooks();

	private void onImcQueue(InterModEnqueueEvent event) {
		hooks.sendIMCMessages(event);
		var allItems = KubeJSUnitItemModule.allInstances.values();
//		System.out.println("Sending IMC messages for " + allItems.size() + " unit items." + allItems);
		for (KubeJSUnitItemModule instance : allItems) {
			var moduleRegistryObject = KubeJSUnitItemModules.allModules.get(instance.itemBuilder.id);
			switch (instance.itemBuilder.slot) {
				case ALL:
					MekanismIMC.addModulesToAll(moduleRegistryObject);
					break;
				case MEK_TOOL:
					MekanismIMC.addMekaToolModules(moduleRegistryObject);
					break;
				case MEK_SUIT_HELMET:
					MekanismIMC.addMekaSuitHelmetModules(moduleRegistryObject);
					break;
				case MEK_SUIT_BODY:
					MekanismIMC.addMekaSuitBodyarmorModules(moduleRegistryObject);
					break;
				case MEK_SUIT_PANTS:
					MekanismIMC.addMekaSuitPantsModules(moduleRegistryObject);
					break;
				case MEK_SUIT_BOOTS:
					MekanismIMC.addMekaSuitBootsModules(moduleRegistryObject);
					break;
			}

		}
	}
}