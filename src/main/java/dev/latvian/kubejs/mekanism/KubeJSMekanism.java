package dev.latvian.kubejs.mekanism;

import mekanism.common.integration.MekanismHooks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(KubeJSMekanism.MOD_ID)
public class KubeJSMekanism {
	public static final String MOD_ID = "kubejs_mekanism";

	public static final MekanismHooks hooks = new MekanismHooks();
	IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
	public KubeJSMekanism() {
		modEventBus.addListener(this::onImcQueue);
	}

	private void onImcQueue(InterModEnqueueEvent event) {
		hooks.sendIMCMessages(event);
	}
}