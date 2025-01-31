package dev.latvian.kubejs.mekanism.registry;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
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
    public UnitItemSlots slot;

    public static Map<ResourceLocation, KubeJSUnitItem> ITEM_MAP = new HashMap<>();

    public static Set<KubeJSUnitItemBuilder> allIds = new HashSet<>();


    public static Map<ResourceLocation ,ModuleDeferredRegister> allModulesRegistered = new HashMap<>();

    public BiFunction<IModule<KubeJSUnitItemModule>, ModuleConfigItemCreator, Void> initCallBack = (module,
                                                                                                    configItemCreator) -> null;
    public TriConsumer<IModule<KubeJSUnitItemModule>, Player, Consumer<IHUDElement>> addHUDElementsCallBack = (
            module, player, hudElementAdder) -> {
    };
    public BiFunction<IModule<KubeJSUnitItemModule>, Player, Void> tickServerCallBack = (module, player) -> null;
    public BiFunction<IModule<KubeJSUnitItemModule>, Player, Void> tickClientCallBack = (module, player) -> null;
    public TriConsumer<IModule<KubeJSUnitItemModule>, Player, Consumer<Component>> addHUDStringsCallBack = (
            module, player, hudStringAdder) -> {
    };
    public int exclusive;
    public boolean rendersHUD;

    private IModuleDataProvider<?> moduleData;

    public KubeJSUnitItemBuilder(ResourceLocation i) {
        super(i);
        allIds.add(this);
        allModulesRegistered.put(this.id, new ModuleDeferredRegister(id.getNamespace()));
    }

    public KubeJSUnitItemBuilder init(BiFunction<IModule<KubeJSUnitItemModule>, ModuleConfigItemCreator, Void> initCallBack) {
        this.initCallBack = initCallBack;
        return this;
    }

    public KubeJSUnitItemBuilder tickServer(BiFunction<IModule<KubeJSUnitItemModule>, Player, Void> tickServerCallBack) {
        this.tickServerCallBack = tickServerCallBack;
        return this;
    }

    public KubeJSUnitItemBuilder tickClient(BiFunction<IModule<KubeJSUnitItemModule>, Player, Void> tickClientCallBack) {
        this.tickClientCallBack = tickClientCallBack;
        return this;
    }

    public KubeJSUnitItemBuilder addHUDStrings(
            TriConsumer<IModule<KubeJSUnitItemModule>, Player, Consumer<Component>> addHUDStringsCallBack) {
        this.addHUDStringsCallBack = addHUDStringsCallBack;
        return this;
    }

    public KubeJSUnitItemBuilder addHUDElements(
            TriConsumer<IModule<KubeJSUnitItemModule>, Player, Consumer<IHUDElement>> addHUDElementsCallBack) {
        this.addHUDElementsCallBack = addHUDElementsCallBack;
        return this;
    }

    public KubeJSUnitItemBuilder setSlot(UnitItemSlots slot) {
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
        var r = new KubeJSUnitItem(this, getModuleData());
        allIds.add(this);
        ITEM_MAP.put(this.id, r);
        return r;
    }

    public IModuleDataProvider<?> getModuleData() {
        if (this.moduleData != null) {
            return moduleData;
        } else {
            return KubeJSUnitItemModules.allModules.get(this.id);
        }
    }

    public void setModuleData(IModuleDataProvider<?> moduleData) {
        this.moduleData = moduleData;
    }

    public String getTranslationKey() {
        return "tooltip." + id.getNamespace() + "." + id.getPath();
    }

    // 自定义四参数的回调接口
    @FunctionalInterface
    public interface QuadConsumer<T, U, V, W> {
        void apply(T t, U u, V v, W w);
    }

    // 自定义五参数的回调接口
    @FunctionalInterface
    public interface QuintConsumer<T, U, V, W, X> {
        void apply(T t, U u, V v, W w, X x);
    }

    // 自定义三参数的回调接口
    @FunctionalInterface
    public interface TriConsumer<T, U, V> {
        void apply(T t, U u, V v);
    }



    public enum UnitItemSlots {
        @Info("全部MEK物品")
        ALL,
        @Info("MEK工具")
        MEK_TOOL,
        @Info("MEK头盔")
        MEK_SUIT_HELMET,
        @Info("MEK胸甲")
        MEK_SUIT_BODY,
        @Info("MEK护腿")
        MEK_SUIT_PANTS,
        @Info("MEK靴子")
        MEK_SUIT_BOOTS
    }
}