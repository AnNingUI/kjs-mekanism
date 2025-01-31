package dev.latvian.kubejs.mekanism.custom.item;

import mekanism.api.gear.IModuleHelper;
import mekanism.api.gear.ModuleData;
import mekanism.api.providers.IModuleDataProvider;
import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import mekanism.client.key.MekKeyHandler;
import mekanism.client.key.MekanismKeyHandler;
import mekanism.common.MekanismLang;
import mekanism.common.content.gear.IModuleItem;
import mekanism.common.item.ItemModule;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static dev.latvian.kubejs.mekanism.util.KubeJSMekUntiItemUtils.getModuleById;

public class KubeJSUnitItem extends ItemModule implements IModuleItem {
    public KubeJSUnitItemBuilder itemBuilder;
    public IModuleDataProvider<?> moduleData;

    public KubeJSUnitItem(IModuleDataProvider<?> moduleData, KubeJSUnitItemBuilder builder) {
        super(moduleData ,builder.createItemProperties());
        this.itemBuilder = builder;
        this.moduleData = moduleData;
    }


    @Override
    public int getMaxStackSize(ItemStack stack) {
        return itemBuilder.maxStackSize;
    }

    @Override
    public ModuleData<?> getModuleData() {
        if (this.moduleData == null) {
            return Objects.requireNonNull(getModuleById(itemBuilder.id)).getModuleData();
        } else {
            return this.moduleData.getModuleData();
        }
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
            if (moduleData != null) {
                tooltip.add(TextComponentUtil.translate(moduleData.getDescriptionTranslationKey()));
                tooltip.add(MekanismLang.MODULE_STACKABLE.translateColored(EnumColor.GRAY, EnumColor.AQUA, moduleData.getMaxStackSize()));
                tooltip.add(MekanismLang.HOLD_FOR_SUPPORTED_ITEMS.translateColored(EnumColor.GRAY, EnumColor.INDIGO, MekanismKeyHandler.detailsKey.getTranslatedKeyMessage()));
            } else {
                tooltip.add(TextComponentUtil.translate(getDescriptionId()));
            }
        }
    }
}
