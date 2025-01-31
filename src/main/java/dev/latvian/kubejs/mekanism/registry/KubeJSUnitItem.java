package dev.latvian.kubejs.mekanism.registry;

import dev.latvian.mods.kubejs.item.custom.BasicItemJS;
import mekanism.api.gear.IModuleHelper;
import mekanism.api.gear.ModuleData;
import mekanism.api.providers.IModuleDataProvider;
import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import mekanism.client.key.MekKeyHandler;
import mekanism.client.key.MekanismKeyHandler;
import mekanism.common.MekanismLang;
import mekanism.common.content.gear.IModuleItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

import static dev.latvian.kubejs.mekanism.registry.KubeJSUnitItemBuilder.ITEM_MAP;

public class KubeJSUnitItem extends BasicItemJS implements IModuleItem {
    public KubeJSUnitItemBuilder.UnitItemSlots slot;

    public KubeJSUnitItemBuilder itemBuilder;

    private final IModuleDataProvider<?> moduleData;

    public KubeJSUnitItem(KubeJSUnitItemBuilder builder, IModuleDataProvider<?> moduleData) {
        super(builder);
        this.itemBuilder = builder;
        this.slot = builder.slot;
        this.moduleData = moduleData;
        System.out.println("注册模块物品++++：" + this.itemBuilder.id.toString() + " " + this.kjs$getId());
        System.out.println("ITEM_MAP++++：" + ITEM_MAP);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return itemBuilder.maxStackSize;
    }

    @Override
    public ModuleData<?> getModuleData() {
        try {
            if (this.moduleData != null) {
                return this.itemBuilder.getModuleData().getModuleData();
            } else if (KubeJSUnitItemModules.allModules.get(this.itemBuilder.id) != null) {
                var moduleData = KubeJSUnitItemModules.allModules.get(this.itemBuilder.id);
                return moduleData.getModuleData();
            }
        } catch (Exception ignored) {
            System.out.println("获取模块数据失败：" + this.itemBuilder.id.toString());
            return null;
        }
        return null;
    }

    @NotNull
    @Override
    public Rarity getRarity(@NotNull ItemStack stack) {
        return itemBuilder.rarity;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (MekKeyHandler.isKeyPressed(MekanismKeyHandler.detailsKey)) {
            tooltip.add(MekanismLang.MODULE_SUPPORTED.translateColored(EnumColor.BRIGHT_GREEN));
            IModuleHelper moduleHelper = IModuleHelper.INSTANCE;
            for (Item item : moduleHelper.getSupported(getModuleData())) {
                tooltip.add(MekanismLang.GENERIC_LIST.translate(item.getName(new ItemStack(item))));
            }
            Set<ModuleData<?>> conflicting = moduleHelper.getConflicting(getModuleData());
            if (!conflicting.isEmpty()) {
                tooltip.add(MekanismLang.MODULE_CONFLICTING.translateColored(EnumColor.RED));
                for (ModuleData<?> module : conflicting) {
                    tooltip.add(MekanismLang.GENERIC_LIST.translate(module));
                }
            }
        } else {
            ModuleData<?> moduleData = getModuleData();
            System.out.println("模块数据 + " + moduleData);
            if (moduleData != null) {
                tooltip.add(TextComponentUtil.translate(moduleData.getDescriptionTranslationKey()));
                tooltip.add(MekanismLang.MODULE_STACKABLE.translateColored(EnumColor.GRAY, EnumColor.AQUA, moduleData.getMaxStackSize()));
                tooltip.add(MekanismLang.HOLD_FOR_SUPPORTED_ITEMS.translateColored(EnumColor.GRAY, EnumColor.INDIGO, MekanismKeyHandler.detailsKey.getTranslatedKeyMessage()));
            } else {
                tooltip.add(TextComponentUtil.translate(getDescriptionId()));
            }
        }
    }

    @NotNull
    @Override
    public String getDescriptionId() {
        return itemBuilder.getTranslationKey();
    }
}
