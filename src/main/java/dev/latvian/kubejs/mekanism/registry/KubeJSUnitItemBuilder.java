package dev.latvian.kubejs.mekanism.registry;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import mekanism.api.gear.IHUDElement;
import mekanism.api.gear.IModule;
import mekanism.api.gear.ModuleData;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.providers.IItemProvider;
import mekanism.common.registration.impl.ModuleDeferredRegister;
import mekanism.common.registration.impl.ModuleRegistryObject;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class KubeJSUnitItemBuilder extends ItemBuilder {

    public UnitItemSlots slot;

    public BiFunction<IModule<KubeJSUnitItem>, ModuleConfigItemCreator, Void> initCallBack = (module,
            configItemCreator) -> null;
    public BiFunction<IModule<KubeJSUnitItem>, Player, Void> tickServerCallBack = (module, player) -> null;
    public BiFunction<IModule<KubeJSUnitItem>, Player, Void> tickClientCallBack = (module, player) -> null;
    public TriConsumer<IModule<KubeJSUnitItem>, Player, Consumer<Component>> addHUDStringsCallBack = (
            module, player, hudStringAdder) -> {
    };

    public TriConsumer<IModule<KubeJSUnitItem>, Player, Consumer<IHUDElement>> addHUDElementsCallBack = (
            module, player, hudElementAdder) -> {
    };

    public KubeJSUnitItemBuilder(ResourceLocation id) {
        super(id);
    }

    public KubeJSUnitItemBuilder init(BiFunction<IModule<KubeJSUnitItem>, ModuleConfigItemCreator, Void> initCallBack) {
        this.initCallBack = initCallBack;
        return this;
    }

    public KubeJSUnitItemBuilder tickServer(BiFunction<IModule<KubeJSUnitItem>, Player, Void> tickServerCallBack) {
        this.tickServerCallBack = tickServerCallBack;
        return this;
    }

    public KubeJSUnitItemBuilder tickClient(BiFunction<IModule<KubeJSUnitItem>, Player, Void> tickClientCallBack) {
        this.tickClientCallBack = tickClientCallBack;
        return this;
    }

    public KubeJSUnitItemBuilder addHUDStrings(
            TriConsumer<IModule<KubeJSUnitItem>, Player, Consumer<Component>> addHUDStringsCallBack) {
        this.addHUDStringsCallBack = addHUDStringsCallBack;
        return this;
    }

    public KubeJSUnitItemBuilder addHUDElements(
            TriConsumer<IModule<KubeJSUnitItem>, Player, Consumer<IHUDElement>> addHUDElementsCallBack) {
        this.addHUDElementsCallBack = addHUDElementsCallBack;
        return this;
    }

    public KubeJSUnitItemBuilder setSlot(UnitItemSlots slot) {
        this.slot = slot;
        return this;
    }



    @Override
    public Item createObject() {
        return new KubeJSUnitItem(this);
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
        MEK_SUIT_CHESTPLATE,
        @Info("MEK护腿")
        MEK_SUIT_PANTS,
        @Info("MEK靴子")
        MEK_SUIT_BOOTS
    }
}


class KubeJSUnitModule {
    private KubeJSUnitModule(ResourceLocation id) {}

    public static ModuleRegistryObject<KubeJSUnitItem> registerKjsUnit(
            ResourceLocation id, IItemProvider itemProvider,
            UnaryOperator<ModuleData.ModuleDataBuilder<KubeJSUnitItem>> builderModifier,
            KubeJSUnitItemBuilder builder
    ) {
        ModuleDeferredRegister MODULES = new ModuleDeferredRegister(id.getNamespace());
        return MODULES.register(id.getPath(), () -> new KubeJSUnitItem(builder),
                itemProvider, builderModifier);
    };
}
