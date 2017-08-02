package com.joshmanisdabomb.aimagg.container.slot;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AimaggSlotLimited extends Slot {

	private Item item;
	private int stackSize;

	public AimaggSlotLimited(IInventory inventoryIn, int index, int xPosition, int yPosition, Block b) {
		this(inventoryIn, index, xPosition, yPosition, Item.getItemFromBlock(b), 64);
	}

	public AimaggSlotLimited(IInventory inventoryIn, int index, int xPosition, int yPosition, Item i) {
		this(inventoryIn, index, xPosition, yPosition, i, 64);
	}

	public AimaggSlotLimited(IInventory inventoryIn, int index, int xPosition, int yPosition, Block b, int size) {
		this(inventoryIn, index, xPosition, yPosition, Item.getItemFromBlock(b), size);
	}

	public AimaggSlotLimited(IInventory inventoryIn, int index, int xPosition, int yPosition, Item i, int size) {
		super(inventoryIn, index, xPosition, yPosition);
		this.item = i;
		this.stackSize = size;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
        return stack != null && stack.getItem() == this.item;
    }
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return this.stackSize;
	}
	
	@Override
	public int getSlotStackLimit() {
        return getItemStackLimit(ItemStack.EMPTY);
    }

}
