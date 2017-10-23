package com.joshmanisdabomb.aimagg.container.slot;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AimaggSlotLimited extends Slot {

	private final Item item;
	private final int metadata;
	
	private final int stackSize;

	public AimaggSlotLimited(IInventory inventoryIn, int index, int xPosition, int yPosition, Block b) {
		this(inventoryIn, index, xPosition, yPosition, Item.getItemFromBlock(b), 64);
	}

	public AimaggSlotLimited(IInventory inventoryIn, int index, int xPosition, int yPosition, Item i) {
		this(inventoryIn, index, xPosition, yPosition, i, 64);
	}

	public AimaggSlotLimited(IInventory inventoryIn, int index, int xPosition, int yPosition, ItemStack is) {
		this(inventoryIn, index, xPosition, yPosition, is, 64);
	}

	public AimaggSlotLimited(IInventory inventoryIn, int index, int xPosition, int yPosition, Block b, int size) {
		this(inventoryIn, index, xPosition, yPosition, Item.getItemFromBlock(b), size);
	}

	public AimaggSlotLimited(IInventory inventoryIn, int index, int xPosition, int yPosition, Item i, int size) {
		super(inventoryIn, index, xPosition, yPosition);
		this.item = i;
		this.metadata = -1;
		this.stackSize = size;
	}

	public AimaggSlotLimited(IInventory inventoryIn, int index, int xPosition, int yPosition, ItemStack is, int size) {
		super(inventoryIn, index, xPosition, yPosition);
		this.item = is.getItem();
		this.metadata = is.getMetadata();
		this.stackSize = size;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
        return stack != null && stack.getItem() == this.item && (this.metadata == -1 || stack.getMetadata() == this.metadata);
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
