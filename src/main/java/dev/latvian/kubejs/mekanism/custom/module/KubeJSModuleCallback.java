package dev.latvian.kubejs.mekanism.custom.module;

import dev.latvian.kubejs.mekanism.custom.CustomInterface;
import mekanism.api.functions.TriConsumer;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IHUDElement;
import mekanism.api.gear.IModule;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.radial.RadialData;
import mekanism.api.radial.mode.IRadialMode;
import mekanism.api.radial.mode.NestedRadialMode;
import net.minecraft.core.BlockSource;
import net.minecraft.network.chat.Component;
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

public class KubeJSModuleCallback {
    public BiFunction<IModule<KubeJSModuleData>, ModuleConfigItemCreator, Void> initCallback;
    public TriConsumer<IModule<KubeJSModuleData>, Player, Consumer<IHUDElement>> addHUDElementsCallback;
    public java.util.function.BiFunction<IModule<KubeJSModuleData>, Player, Void> tickServerCallback;
    public BiFunction<IModule<KubeJSModuleData>, Player, Void> tickClientCallback;
    public TriConsumer<IModule<KubeJSModuleData>, Player, Consumer<Component>> addHUDStringsCallback;

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

}
