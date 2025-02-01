package dev.latvian.kubejs.mekanism.mixin;

import mekanism.api.math.FloatingLongSupplier;
import mekanism.common.content.gear.IBlastingItem;
import mekanism.common.content.gear.IModuleContainerItem;
import mekanism.common.content.gear.Module;
import mekanism.common.item.ItemEnergized;
import mekanism.common.lib.radial.IGenericRadialModeItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(mekanism.common.item.gear.ItemMekaTool.class)
public abstract class ItemMekaToolMixin extends ItemEnergized implements IModuleContainerItem, IBlastingItem, IGenericRadialModeItem {
    public ItemMekaToolMixin(FloatingLongSupplier chargeRateSupplier, FloatingLongSupplier maxEnergySupplier, Properties properties) {
        super(chargeRateSupplier, maxEnergySupplier, properties);
    }

	@Override
	public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
		for (Module<?> module : getModules(pStack)) {
			if (pEntity instanceof Player) {
				module.tick((Player) pEntity);
			}
		}
	}
}