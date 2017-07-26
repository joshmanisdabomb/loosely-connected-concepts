package com.joshmanisdabomb.aimagg.container;

import com.joshmanisdabomb.aimagg.container.inventory.InventorySpreaderInterface;
import com.joshmanisdabomb.aimagg.container.slot.AimaggSlotLimited;
import com.joshmanisdabomb.aimagg.container.slot.AimaggSlotOutput;
import com.joshmanisdabomb.aimagg.data.world.SpreaderData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AimaggContainerSpreaderInterface extends Container {
	
	public static final int defaultSpeedSlotX = 38;
	public static final int defaultSpeedSlotY = 42;
	public static final int defaultDamageSlotX = 59;
	public static final int defaultDamageSlotY = 42;
	public static final int defaultRangeSlotX = 80;
	public static final int defaultRangeSlotY = 42;
	public static final int defaultSpreadSlotX = 101;
	public static final int defaultSpreadSlotY = 42;
	public static final int defaultEatingSlotX = 122;
	public static final int defaultEatingSlotY = 42;
	public static final int defaultInGroundSlotX = 38;
	public static final int defaultInGroundSlotY = 113;
	public static final int defaultInLiquidSlotX = 72;
	public static final int defaultInLiquidSlotY = 113;
	public static final int defaultInAirSlotX = 106;
	public static final int defaultInAirSlotY = 113;
	
	private final World w;

	public AimaggContainerSpreaderInterface(EntityPlayer player, World world) {	
		InventorySpreaderInterface isi = SpreaderData.getInstance(world).getInventory();
		
		this.w = world;
		
		//Speed Slots, 0+(color*8)
		//Damage Slots, 1+(color*8)
		//Range Slots, 2+(color*8)
		//Spread Slots, 3+(color*8)
		//Eating Slots, 4+(color*8)
		
		//In Ground Slots, 5+(color*8)
		//In Liquid Slots, 6+(color*8)
		//In Air Slots, 7+(color*8)
		
		for (int i = 0; i < 16; i++) {
			this.addSlotToContainer(new Slot(isi, 0+(i*8), defaultSpeedSlotX, defaultSpeedSlotY));
			this.addSlotToContainer(new Slot(isi, 1+(i*8), defaultDamageSlotX, defaultDamageSlotY));
			this.addSlotToContainer(new Slot(isi, 2+(i*8), defaultRangeSlotX, defaultRangeSlotY));
			this.addSlotToContainer(new Slot(isi, 3+(i*8), defaultSpreadSlotX, defaultSpreadSlotX));
			this.addSlotToContainer(new Slot(isi, 4+(i*8), defaultEatingSlotX, defaultEatingSlotY));
			this.addSlotToContainer(new Slot(isi, 5+(i*8), defaultInGroundSlotX, defaultInGroundSlotY));
			this.addSlotToContainer(new Slot(isi, 6+(i*8), defaultInLiquidSlotX, defaultInLiquidSlotY));
			this.addSlotToContainer(new Slot(isi, 7+(i*8), defaultInAirSlotX, defaultInAirSlotY));
		}

	    // Player Inventory, Slot 9-35, Slot IDs 137-163
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(player.inventory, x + y * 9 + 9, 8 + x * 18, 140 + y * 18));
	        }
	    }

	    // Player Inventory, Slot 0-8, Slot IDs 164-172
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(player.inventory, x, 8 + x * 18, 198));
	    }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	//run on shift click
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		if (playerIn.world.isRemote) {return null;}
	    
		ItemStack previous = null;
	    Slot slot = (Slot) this.inventorySlots.get(fromSlot);
	    
	    InventorySpreaderInterface isi = SpreaderData.getInstance(playerIn.world).getInventory();
	    
	    if (slot != null && slot.getHasStack()) {
	        ItemStack current = slot.getStack();
	        previous = current.copy();

	        if (fromSlot < isi.getSizeInventory()) {
	            // From TE Inventory to Player Inventory
	            if (!this.mergeItemStack(current, 0+isi.getSizeInventory(), 36+isi.getSizeInventory(), true)) {
	                isi.updateWorld();
	            	return null;
	            }
	        } else {
	            // From Player Inventory to TE Inventory
	            if (!this.mergeItemStack(current, 0, isi.getSizeInventory(), false)) {
	            	isi.updateWorld();
	            	return null;
	            }
	        }

	        if (current.isEmpty())
	            slot.putStack((ItemStack) null);
	        else
	            slot.onSlotChanged();

	        if (current.getCount() == previous.getCount()) {
	        	isi.updateWorld();
	        	return null;
	        }
	        slot.onTake(playerIn, current);
	    }
	    
	    isi.updateWorld();
	    return previous;
	}
	
	//run on shift click
	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean useEndIndex) {	    
        boolean success = false;
	    int index = startIndex;

	    if (useEndIndex)
	        index = endIndex - 1;

	    Slot slot;
	    ItemStack stackinslot;

	    if (stack.isStackable()) {
	        while (!stack.isEmpty() && (!useEndIndex && index < endIndex || useEndIndex && index >= startIndex)) {
	            slot = (Slot) this.inventorySlots.get(index);
	            stackinslot = slot.getStack();

	            if (stackinslot != null && stackinslot.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == stackinslot.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, stackinslot)) {
	                int l = stackinslot.getCount() + stack.getCount();
	                int maxsize = Math.min(stack.getMaxStackSize(), slot.getItemStackLimit(stack));

	                if (l <= maxsize) {
	                    stack.setCount(0);
	                    stackinslot.setCount(l);
	                    slot.onSlotChanged();
	                    success = true;
	                } else if (stackinslot.getCount() < maxsize) {
	                    stack.setCount(stack.getCount() - stack.getMaxStackSize() - stackinslot.getCount());
	                    stackinslot.setCount(stack.getMaxStackSize());
	                    slot.onSlotChanged();
	                    success = true;
	                }
	            }

	            if (useEndIndex) {
	                --index;
	            } else {
	                ++index;
	            }
	        }
	    }

	    if (!stack.isEmpty()) {
	        if (useEndIndex) {
	            index = endIndex - 1;
	        } else {
	            index = startIndex;
	        }

	        while (!useEndIndex && index < endIndex || useEndIndex && index >= startIndex && !stack.isEmpty()) {
	            slot = (Slot) this.inventorySlots.get(index);
	            stackinslot = slot.getStack();

	            // Forge: Make sure to respect isItemValid in the slot.
	            if (stackinslot == null && slot.isItemValid(stack)) {
	                if (stack.getCount() < slot.getItemStackLimit(stack)) {
	                    slot.putStack(stack.copy());
	                    stack.setCount(0);
	                    success = true;
	                    break;
	                } else {
	                    ItemStack newstack = stack.copy();
	                    newstack.setCount(slot.getItemStackLimit(stack));
	                    slot.putStack(newstack);
	                    stack.setCount(stack.getCount() - slot.getItemStackLimit(stack));
	                    success = true;
	                }
	            }

	            if (useEndIndex) {
	                --index;
	            } else {
	                ++index;
	            }
	        }
	    }
	    
	    return success;
		
	    /*InventorySpreaderInterface isi = SpreaderData.getInstance(w).getInventory();
		boolean rtn = super.mergeItemStack(stack, startIndex, endIndex, useEndIndex);
		isi.updateWorld();
		return rtn;*/
	}

}
