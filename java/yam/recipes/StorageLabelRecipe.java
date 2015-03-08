package yam.recipes;

import java.awt.Color;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import yam.items.ItemStorage;

public class StorageLabelRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting var1, World var2) {
		int devices = 0;
		for (int i = 0; i < var1.getSizeInventory(); i++) {
			if (var1.getStackInSlot(i) != null && var1.getStackInSlot(i).getItem() instanceof ItemStorage) {
				if (!var1.getStackInSlot(i).hasTagCompound()) {
					((ItemStorage)var1.getStackInSlot(i).getItem()).initNBTData(var1.getStackInSlot(i));
				}
				devices += 1;
			}
		}
		if (devices != 1) {return false;}
		
		int paper = 0;
		for (int i = 0; i < var1.getSizeInventory(); i++) {
			if (var1.getStackInSlot(i) != null && var1.getStackInSlot(i).getItem() == Items.paper) {
				paper += 1;
			}
		}
		if (paper != 1) {return false;}

		for (int i = 0; i < var1.getSizeInventory(); i++) {
			if (var1.getStackInSlot(i) != null && var1.getStackInSlot(i).getItem() instanceof ItemDye) {return true;}
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		ItemStack device = null;
		Color color = null;
		
		for (int i = 0; i < var1.getSizeInventory(); i++) {
			if (var1.getStackInSlot(i) != null && var1.getStackInSlot(i).getItem() instanceof ItemStorage) {
				device = var1.getStackInSlot(i);
				break;
			}
		}
		
		for (int i = 0; i < var1.getSizeInventory(); i++) {
			if (var1.getStackInSlot(i) != null && var1.getStackInSlot(i).getItem() instanceof ItemDye) {
				if (color == null) {
					color = Color.blue;//new Color(ItemDye.field_150922_c[var1.getStackInSlot(i).getItemDamage()]);
				} else {
					color = Color.blue;//blend(color, new Color(ItemDye.field_150922_c[var1.getStackInSlot(i).getItemDamage()]));
				}
			}
		}
		
		device = device.copy();
		device.stackTagCompound.setInteger("label", color.getRGB());
		return device;
	}
	
	public static Color blend(Color c0, Color c1) {
	    double totalAlpha = c0.getAlpha() + c1.getAlpha();
	    double weight0 = c0.getAlpha() / totalAlpha;
	    double weight1 = c1.getAlpha() / totalAlpha;

	    double r = weight0 * c0.getRed() + weight1 * c1.getRed();
	    double g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
	    double b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
	    double a = Math.max(c0.getAlpha(), c1.getAlpha());

	    return new Color((int) r, (int) g, (int) b, 255);
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return null;
	}

}
