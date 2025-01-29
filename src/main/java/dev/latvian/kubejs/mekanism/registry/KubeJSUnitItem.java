package dev.latvian.kubejs.mekanism.registry;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.item.custom.BasicItemJS;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IHUDElement;
import mekanism.api.gear.IModule;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public class  KubeJSUnitItem extends BasicItemJS implements ICustomModule<KubeJSUnitItem> {

    private final KubeJSUnitItemBuilder itemBuilder;

    @Override
    public void init(IModule<KubeJSUnitItem> module, ModuleConfigItemCreator configItemCreator) {
        // 修改 initCallBack 的类型以匹配 IModule<KubeJSUnitItem>
        if (itemBuilder.initCallBack != null) {
            itemBuilder.initCallBack.apply(module, configItemCreator);
        } else {
            ICustomModule.super.init(module, configItemCreator);
        }
    }

    @Override
    public void tickServer(IModule<KubeJSUnitItem> module, Player player) {
        if (itemBuilder.tickServerCallBack != null) {
            itemBuilder.tickServerCallBack.apply(module, player);
        } else {
            ICustomModule.super.tickServer(module, player);
        }
    }

    @Override
    public void addHUDStrings(IModule<KubeJSUnitItem> module, Player player, Consumer<Component> hudStringAdder) {
        if (itemBuilder.addHUDStringsCallBack != null) {
            itemBuilder.addHUDStringsCallBack.apply(module, player, hudStringAdder);
        } else {
            ICustomModule.super.addHUDStrings(module, player, hudStringAdder);
        }
    }

    @Override
    public void addHUDElements(IModule<KubeJSUnitItem> module, Player player, Consumer<IHUDElement> hudElementAdder) {
        if (itemBuilder.addHUDElementsCallBack != null) {
            itemBuilder.addHUDElementsCallBack.apply(module, player, hudElementAdder);
        } else {
            ICustomModule.super.addHUDElements(module, player, hudElementAdder);
        }
    }

    @Override
    public void tickClient(IModule<KubeJSUnitItem> module, Player player) {
        if (itemBuilder.tickClientCallBack != null) {
            itemBuilder.tickClientCallBack.apply(module, player);
        } else {
            ICustomModule.super.tickClient(module, player);
        }
    }

    public KubeJSUnitItem(ItemBuilder p) {
        super(p);
        this.itemBuilder = (KubeJSUnitItemBuilder) p;
    }

}


/**
 * MEK UNIT ITEM 注册
 ENERGY_UNIT -> 通过 在 `MekanismModules` 类 注册 其中需要参数要 `MODULE_ENERGY` ,而 `MODULE_ENERGY` 是 在 `MekanismItems` 注册
 ，要用到`ENERGY_UNIT`，而`ENERGY_UNIT`还需要去监听`InterModEnqueueEvent`事件调用MekanismIMC.addModulesToAll方法才可以确定可用槽位

 * KUBEJS ITEM 注册
 先确定ItemBuilder类，再Item类实例方法传参，最后注册到KubeJS插件主类在重写init方法中RegistryInfo.ITEM.addType("mek_unit", KubeJSUnitItemBuilder.class, KubeJSUnitItemBuilder::new);
 就可以添加对应的物品注册类型了

 ** 那么怎么样通过编写KJS 插件提供出Js Api让开发者可以在kjs脚本中调用快速创建MEK UNIT ITEM？

 * */
