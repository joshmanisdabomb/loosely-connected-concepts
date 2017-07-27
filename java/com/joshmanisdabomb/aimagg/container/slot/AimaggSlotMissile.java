package com.joshmanisdabomb.aimagg.container.slot;

import com.joshmanisdabomb.aimagg.items.AimaggItemMissile;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class AimaggSlotMissile extends Slot {

	public AimaggSlotMissile(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
        return stack != null && stack.getItem() instanceof AimaggItemMissile;
    }
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}

}
