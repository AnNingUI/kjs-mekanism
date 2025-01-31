package dev.latvian.kubejs.mekanism.registry;

import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IHUDElement;
import mekanism.api.gear.IModule;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class KubeJSUnitItemModule implements ICustomModule<KubeJSUnitItemModule>  {
    public KubeJSUnitItemBuilder itemBuilder;


    public static Map<ResourceLocation, KubeJSUnitItemModule> allInstances = new HashMap<>();

    public KubeJSUnitItemModule(KubeJSUnitItemBuilder builder) {
        this.itemBuilder = builder;
        allInstances.put(builder.id ,this);
    }

    @Override
    public void init(IModule<KubeJSUnitItemModule> module, ModuleConfigItemCreator configItemCreator) {
        if (itemBuilder.initCallBack != null) {
            itemBuilder.initCallBack.apply(module, configItemCreator);
        } else {
            ICustomModule.super.init(module, configItemCreator);
        }
    }

    @Override
    public void tickServer(IModule<KubeJSUnitItemModule> module, Player player) {
        if (itemBuilder.tickServerCallBack != null) {
            itemBuilder.tickServerCallBack.apply(module, player);
        } else {
            ICustomModule.super.tickServer(module, player);
        }
    }

    @Override
    public void addHUDStrings(IModule<KubeJSUnitItemModule> module, Player player, Consumer<Component> hudStringAdder) {
        if (itemBuilder.addHUDStringsCallBack != null) {
            itemBuilder.addHUDStringsCallBack.apply(module, player, hudStringAdder);
        } else {
            ICustomModule.super.addHUDStrings(module, player, hudStringAdder);
        }
    }

    @Override
    public void addHUDElements(IModule<KubeJSUnitItemModule> module, Player player, Consumer<IHUDElement> hudElementAdder) {
        if (itemBuilder.addHUDElementsCallBack != null) {
            itemBuilder.addHUDElementsCallBack.apply(module, player, hudElementAdder);
        } else {
            ICustomModule.super.addHUDElements(module, player, hudElementAdder);
        }
    }

    @Override
    public void tickClient(IModule<KubeJSUnitItemModule> module, Player player) {
        if (itemBuilder.tickClientCallBack != null) {
            itemBuilder.tickClientCallBack.apply(module, player);
        } else {
            ICustomModule.super.tickClient(module, player);
        }
    }
}
