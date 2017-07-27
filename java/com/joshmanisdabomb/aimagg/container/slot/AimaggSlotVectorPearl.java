package com.joshmanisdabomb.aimagg.container.slot;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.items.AimaggItemVectorPearl;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class AimaggSlotVectorPearl extends Slot {

	public AimaggSlotVectorPearl(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		NBTTagCompound vpNBT = stack.getSubCompound(Constants.MOD_ID + "_vector_pearl");
        return stack != null && (stack.getItem() instanceof AimaggItemVectorPearl) && vpNBT != null && vpNBT.getBoolean("used");
    }
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}

}
