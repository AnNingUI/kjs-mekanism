package dev.latvian.kubejs.mekanism.custom.item;

import dev.latvian.kubejs.mekanism.MekanismKubeJSPlugin;
import dev.latvian.kubejs.mekanism.custom.enums.UnitItemSlots;
import dev.latvian.kubejs.mekanism.custom.module.KubeJSModuleData;
import dev.latvian.kubejs.mekanism.custom.module.KubeJSModuleDataBuilder;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import mekanism.api.functions.TriConsumer;
import mekanism.api.gear.IHUDElement;
import mekanism.api.gear.IModule;
import mekanism.api.gear.ModuleData;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.providers.IModuleDataProvider;
import mekanism.common.registration.impl.ModuleDeferredRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class KubeJSUnitItemBuilder extends ItemBuilder {
    public UnitItemSlots.Slots slot;

    public static Map<ResourceLocation, KubeJSUnitItem> ITEM_MAP = new HashMap<>();

    public static Set<KubeJSUnitItemBuilder> allIds = new HashSet<>();


    public static Map<ResourceLocation ,ModuleDeferredRegister> allModulesRegistered = new HashMap<>();

    public BiFunction<IModule<KubeJSModuleData>, ModuleConfigItemCreator, Void> initCallBack = (module,
                                                                                                    configItemCreator) -> null;
    public TriConsumer<IModule<KubeJSModuleData>, Player, Consumer<IHUDElement>> addHUDElementsCallBack = (
            module, player, hudElementAdder) -> {
    };
    public BiFunction<IModule<KubeJSModuleData>, Player, Void> tickServerCallBack = (module, player) -> null;
    public BiFunction<IModule<KubeJSModuleData>, Player, Void> tickClientCallBack = (module, player) -> null;
    public TriConsumer<IModule<KubeJSModuleData>, Player, Consumer<Component>> addHUDStringsCallBack = (
            module, player, hudStringAdder) -> {
    };
    public int exclusive;
    public boolean rendersHUD;

    public int maxModuleSize = 1;
    public boolean handlesModeChange;
    public boolean modeChangeDisabledByDefault;

    private IModuleDataProvider<?> moduleData;

    public KubeJSUnitItemBuilder(ResourceLocation i) {
        super(i);
        allIds.add(this);
        allModulesRegistered.put(this.id, new ModuleDeferredRegister(id.getNamespace()));
    }

    public KubeJSUnitItemBuilder handlesModeChange(boolean handlesModeChange) {
        this.handlesModeChange = handlesModeChange;
        return this;
    };

    public KubeJSUnitItemBuilder modeChangeDisabledByDefault(boolean modeChangeDisabledByDefault) {
        this.modeChangeDisabledByDefault = modeChangeDisabledByDefault;
        return this;
    };

    public KubeJSUnitItemBuilder maxModuleSize(int maxModuleSize) {
        this.maxModuleSize = maxModuleSize;
        return this;
    }

    public KubeJSUnitItemBuilder init(BiFunction<IModule<KubeJSModuleData>, ModuleConfigItemCreator, Void> initCallBack) {
        this.initCallBack = initCallBack;
        return this;
    }

    public KubeJSUnitItemBuilder tickServer(BiFunction<IModule<KubeJSModuleData>, Player, Void> tickServerCallBack) {
        this.tickServerCallBack = tickServerCallBack;
        return this;
    }

    public KubeJSUnitItemBuilder tickClient(BiFunction<IModule<KubeJSModuleData>, Player, Void> tickClientCallBack) {
        this.tickClientCallBack = tickClientCallBack;
        return this;
    }

    public KubeJSUnitItemBuilder addHUDStrings(
            TriConsumer<IModule<KubeJSModuleData>, Player, Consumer<Component>> addHUDStringsCallBack) {
        this.addHUDStringsCallBack = addHUDStringsCallBack;
        return this;
    }

    public KubeJSUnitItemBuilder addHUDElements(
            TriConsumer<IModule<KubeJSModuleData>, Player, Consumer<IHUDElement>> addHUDElementsCallBack) {
        this.addHUDElementsCallBack = addHUDElementsCallBack;
        return this;
    }

    public KubeJSUnitItemBuilder setSlot(UnitItemSlots.Slots slot) {
        this.slot = slot;
        return this;
    }

    public KubeJSUnitItemBuilder setExclusive(int exclusive) {
        this.exclusive = exclusive;
        return this;
    }

    public KubeJSUnitItemBuilder setExclusiveByFlag(ModuleData.ExclusiveFlag... flags) {
        this.exclusive = flags.length == 0 ? ModuleData.ExclusiveFlag.ANY : ModuleData.ExclusiveFlag.getCompoundMask(flags);
        return this;
    }

    public KubeJSUnitItemBuilder setRendersHUD(boolean rendersHUD) {
        this.rendersHUD = rendersHUD;
        return this;
    }

    @Override
    public KubeJSUnitItem createObject() {
        var r = new KubeJSUnitItem(getModuleData(), this);
        allIds.add(this);
        ITEM_MAP.put(this.id, r);
        return r;
    }

    @Override
    public void createAdditionalObjects() {
        super.createAdditionalObjects();
        if (getModuleData() == null) {
            MekanismKubeJSPlugin.MODULE_DATA.addBuilder(KubeJSModuleDataBuilder.create(this));
        }
    }

    public IModuleDataProvider<?> getModuleData() {
        return moduleData;
    }

    public void setModuleData(IModuleDataProvider<?> moduleData) {
        this.moduleData = moduleData;
    }


}