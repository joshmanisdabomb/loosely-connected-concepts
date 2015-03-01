package yam.recipes;

import yam.YetAnotherMod;
import yam.items.ItemMotherboard;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class MotherboardRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting var1, World var2) {
		int motherboards = 0;
		for (int i = 0; i < var1.getSizeInventory(); i++) {
			if (var1.getStackInSlot(i) != null && var1.getStackInSlot(i).getItem() == YetAnotherMod.motherboard) {
				if (!var1.getStackInSlot(i).hasTagCompound()) {
					ItemMotherboard.initNBTData(var1.getStackInSlot(i));
				}
				motherboards += 1;
			}
		}
		if (motherboards != 1) {return false;}

		int processors = 0;
		int gcards = 0;
		int memory = 0;
		int fans = 0;
		for (int i = 0; i < var1.getSizeInventory(); i++) {
			if (var1.getStackInSlot(i) != null) {
				if (var1.getStackInSlot(i).getItem() == YetAnotherMod.processor) {processors += 1;}
				if (var1.getStackInSlot(i).getItem() == YetAnotherMod.graphicscard) {gcards += 1;}
				if (var1.getStackInSlot(i).getItem() == YetAnotherMod.memory) {memory += 1;}
				if (var1.getStackInSlot(i).getItem() == YetAnotherMod.coolingfan) {fans += 1;}
			}
		}
		return processors > 0 || gcards > 0 || memory > 0 || fans > 0;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		ItemStack motherboard = null;
		for (int i = 0; i < var1.getSizeInventory(); i++) {
			if (var1.getStackInSlot(i) != null && var1.getStackInSlot(i).getItem() == YetAnotherMod.motherboard) {
				motherboard = var1.getStackInSlot(i);
				break;
			}
		}
		
		int processors = 0;
		int gcards = 0;
		int memory = 0;
		int fans = 0;
		for (int i = 0; i < var1.getSizeInventory(); i++) {
			if (var1.getStackInSlot(i) != null) {
				if (var1.getStackInSlot(i).getItem() == YetAnotherMod.processor) {processors += 1;}
				if (var1.getStackInSlot(i).getItem() == YetAnotherMod.graphicscard) {gcards += 1;}
				if (var1.getStackInSlot(i).getItem() == YetAnotherMod.memory) {memory += 1;}
				if (var1.getStackInSlot(i).getItem() == YetAnotherMod.coolingfan) {fans += 1;}
			}
		}
		
		ItemStack i = new ItemStack(YetAnotherMod.motherboard, 1);
		ItemMotherboard.initNBTData(i, ItemMotherboard.getMemoryNum(motherboard) + memory, ItemMotherboard.getPowerNum(motherboard) + processors, ItemMotherboard.getGraphicPowerNum(motherboard) + gcards, ItemMotherboard.getCoolants(motherboard) + fans);
		return i;
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

}
