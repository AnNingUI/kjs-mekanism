package dev.latvian.kubejs.mekanism.custom.item;

import dev.latvian.kubejs.mekanism.MekanismKubeJSPlugin;
import dev.latvian.kubejs.mekanism.custom.CustomInterface;
import dev.latvian.kubejs.mekanism.custom.enums.UnitItemSlots;
import dev.latvian.kubejs.mekanism.custom.module.KubeJSModuleData;
import dev.latvian.kubejs.mekanism.custom.module.KubeJSModuleDataBuilder;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import mekanism.api.functions.TriConsumer;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IHUDElement;
import mekanism.api.gear.IModule;
import mekanism.api.gear.ModuleData;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.providers.IModuleDataProvider;
import mekanism.api.radial.RadialData;
import mekanism.api.radial.mode.IRadialMode;
import mekanism.api.radial.mode.NestedRadialMode;
import net.minecraft.core.BlockSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.common.ToolAction;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class KubeJSUnitItemBuilder extends ItemBuilder {
    public UnitItemSlots.Slots slot;

    public BiFunction<IModule<KubeJSModuleData>, ModuleConfigItemCreator, Void> initCallback = (module,
                                                                                                    configItemCreator) -> null;
    public TriConsumer<IModule<KubeJSModuleData>, Player, Consumer<IHUDElement>> addHUDElementsCallback = (
            module, player, hudElementAdder) -> {
    };
    public BiFunction<IModule<KubeJSModuleData>, Player, Void> tickServerCallback = (module, player) -> null;
    public BiFunction<IModule<KubeJSModuleData>, Player, Void> tickClientCallback = (module, player) -> null;
    public TriConsumer<IModule<KubeJSModuleData>, Player, Consumer<Component>> addHUDStringsCallback = (
            module, player, hudStringAdder) -> {
    };

    public Function<IModule<KubeJSModuleData>, Boolean> canChangeModeWhenDisabledCallback;

    public Function<IModule<KubeJSModuleData>, Boolean> canChangeRadialModeWhenDisabledCallback;

    public BiFunction<IModule<KubeJSModuleData>, ItemStack, Component> getModeScrollComponentCallback;

    public CustomInterface.KQuintConsumer<IModule<KubeJSModuleData>, Player, ItemStack, Integer, Boolean, Void> changeModeCallback;

    public CustomInterface.KTriConsumer<IModule<KubeJSModuleData>, ItemStack, Consumer<NestedRadialMode>, Void> addRadialModesCallback;

    public CustomInterface.KTriConsumer<IModule<KubeJSModuleData>, ItemStack, RadialData<?>, ?> getModeCallback;

    public CustomInterface.KQuintConsumer<IModule<KubeJSModuleData>, Player, ItemStack, RadialData<? extends IRadialMode>, ? extends IRadialMode, Boolean> setModeCallback;

    public CustomInterface.KQuadConsumer<IModule<KubeJSModuleData>, Player, LivingEntity, InteractionHand, InteractionResult> onInteractCallback;

    public BiFunction<IModule<KubeJSModuleData>, BlockSource, ICustomModule.ModuleDispenseResult> onDispenseCallback;

    public BiFunction<IModule<KubeJSModuleData>, Boolean, Void> onAddedCallback;

    public BiFunction<IModule<KubeJSModuleData>, Boolean, Void> onRemovedCallback;

    public Function<IModule<KubeJSModuleData>, Void> onEnabledStateChangeCallback;

    public BiFunction<IModule<KubeJSModuleData>, DamageSource, ICustomModule.ModuleDamageAbsorbInfo> getDamageAbsorbInfoCallback;

    public BiFunction<IModule<KubeJSModuleData>, UseOnContext, InteractionResult> onItemUseCallback;

    public BiFunction<IModule<KubeJSModuleData>, ToolAction, Boolean> canPerformActionCallback;

    public int exclusive;
    public boolean rendersHUD;

    public int maxModuleSize = 1;
    public boolean handlesModeChange;
    public boolean modeChangeDisabledByDefault;

    private IModuleDataProvider<?> moduleData;

    public KubeJSUnitItemBuilder(ResourceLocation i) {
        super(i);
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

    public KubeJSUnitItemBuilder init(BiFunction<IModule<KubeJSModuleData>, ModuleConfigItemCreator, Void> initCallback) {
        this.initCallback = initCallback;
        return this;
    }

    public KubeJSUnitItemBuilder tickServer(BiFunction<IModule<KubeJSModuleData>, Player, Void> tickServerCallback) {
        this.tickServerCallback = tickServerCallback;
        return this;
    }

    public KubeJSUnitItemBuilder tickClient(BiFunction<IModule<KubeJSModuleData>, Player, Void> tickClientCallback) {
        this.tickClientCallback = tickClientCallback;
        return this;
    }

    public KubeJSUnitItemBuilder addHUDStrings(
            TriConsumer<IModule<KubeJSModuleData>, Player, Consumer<Component>> addHUDStringsCallback) {
        this.addHUDStringsCallback = addHUDStringsCallback;
        return this;
    }

    public KubeJSUnitItemBuilder addHUDElements(
            TriConsumer<IModule<KubeJSModuleData>, Player, Consumer<IHUDElement>> addHUDElementsCallback) {
        this.addHUDElementsCallback = addHUDElementsCallback;
        return this;
    }

    public KubeJSUnitItemBuilder changeMode(
            CustomInterface.KQuintConsumer<IModule<KubeJSModuleData>, Player, ItemStack, Integer, Boolean, Void> changeModeCallback) {
        this.changeModeCallback = changeModeCallback;
        return this;
    }

    public KubeJSUnitItemBuilder canChangeModeWhenDisabled(Function<IModule<KubeJSModuleData>, Boolean> canChangeModeWhenDisabledCallback) {
        this.canChangeModeWhenDisabledCallback = canChangeModeWhenDisabledCallback;
        return this;
    }

    public KubeJSUnitItemBuilder canChangeRadialModeWhenDisabled(Function<IModule<KubeJSModuleData>, Boolean> canChangeRadialModeWhenDisabledCallback) {
        this.canChangeRadialModeWhenDisabledCallback = canChangeRadialModeWhenDisabledCallback;
        return this;
    }

    public KubeJSUnitItemBuilder getModeScrollComponent(BiFunction<IModule<KubeJSModuleData>, ItemStack, Component> getModeScrollComponentCallback) {
        this.getModeScrollComponentCallback = getModeScrollComponentCallback;
        return this;
    }

    public KubeJSUnitItemBuilder addRadialModes(CustomInterface.KTriConsumer<IModule<KubeJSModuleData>, ItemStack, Consumer<NestedRadialMode>, Void> addRadialModesCallback) {
        this.addRadialModesCallback = addRadialModesCallback;
        return this;
    }

    public KubeJSUnitItemBuilder getMode(CustomInterface.KTriConsumer<IModule<KubeJSModuleData>, ItemStack, RadialData<? extends IRadialMode>, ? extends IRadialMode> getModeCallback) {
        this.getModeCallback = getModeCallback;
        return this;
    }

    public KubeJSUnitItemBuilder setMode(CustomInterface.KQuintConsumer<IModule<KubeJSModuleData>, Player, ItemStack, RadialData<? extends IRadialMode>, ? extends IRadialMode, Boolean> setModeCallback) {
        this.setModeCallback = setModeCallback;
        return this;
    }

    public KubeJSUnitItemBuilder onInteract(CustomInterface.KQuadConsumer<IModule<KubeJSModuleData>, Player, LivingEntity, InteractionHand, InteractionResult> onInteractCallback) {
        this.onInteractCallback = onInteractCallback;
        return this;
    }

    public KubeJSUnitItemBuilder onDispense(BiFunction<IModule<KubeJSModuleData>, BlockSource, ICustomModule.ModuleDispenseResult> onDispenseCallback) {
        this.onDispenseCallback = onDispenseCallback;
        return this;
    }

    public KubeJSUnitItemBuilder onAdded(BiFunction<IModule<KubeJSModuleData>, Boolean, Void> onAddedCallback) {
        this.onAddedCallback = onAddedCallback;
        return this;
    }

    public KubeJSUnitItemBuilder onRemoved(BiFunction<IModule<KubeJSModuleData>, Boolean, Void> onRemovedCallback) {
        this.onRemovedCallback = onRemovedCallback;
        return this;
    }

    public KubeJSUnitItemBuilder onEnabledStateChange(Function<IModule<KubeJSModuleData>, Void> onEnabledStateChangeCallback) {
        this.onEnabledStateChangeCallback = onEnabledStateChangeCallback;
        return this;
    }

    public KubeJSUnitItemBuilder getDamageAbsorbInfo(BiFunction<IModule<KubeJSModuleData>, DamageSource, ICustomModule.ModuleDamageAbsorbInfo> getDamageAbsorbInfoCallback) {
        this.getDamageAbsorbInfoCallback = getDamageAbsorbInfoCallback;
        return this;
    }

    public KubeJSUnitItemBuilder onItemUse(BiFunction<IModule<KubeJSModuleData>, UseOnContext, InteractionResult> onItemUseCallback) {
        this.onItemUseCallback = onItemUseCallback;
        return this;
    }

    public KubeJSUnitItemBuilder canPerformAction(BiFunction<IModule<KubeJSModuleData>, ToolAction, Boolean> canPerformActionCallback) {
        this.canPerformActionCallback = canPerformActionCallback;
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