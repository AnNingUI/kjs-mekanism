package dev.latvian.kubejs.mekanism;

import mekanism.api.MekanismIMC;
import mekanism.common.integration.MekanismHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static dev.latvian.kubejs.mekanism.custom.module.KubeJSModuleDataBuilder.getAllBuilder;
import static dev.latvian.kubejs.mekanism.util.KubeJSMekUntiItemUtils.getModuleById;

@Mod(KubeJSMekanism.MOD_ID)
public class KubeJSMekanism {
	public static final String MOD_ID = "kubejs_mekanism";


	public KubeJSMekanism() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::onImcQueue);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static final MekanismHooks hooks = new MekanismHooks();

	private void onImcQueue(InterModEnqueueEvent event) {
		hooks.sendIMCMessages(event);
		var a = getAllBuilder();
		a.forEach(b -> {
			var s  = b.slot;
			var id = b.id;
			var m  = getModuleById(id);
			switch (s) {
				case ALL:
					MekanismIMC.addModulesToAll(m);
					break;
				case MEK_TOOL:
					MekanismIMC.addMekaToolModules(m);
					break;
				case MEK_SUIT_HELMET:
					MekanismIMC.addMekaSuitHelmetModules(m);
					break;
				case MEK_SUIT_BODY:
					MekanismIMC.addMekaSuitBodyarmorModules(m);
					break;
				case MEK_SUIT_PANTS:
					MekanismIMC.addMekaSuitPantsModules(m);
					break;
				case MEK_SUIT_BOOTS:
					MekanismIMC.addMekaSuitBootsModules(m);
			}
		});
	}
}