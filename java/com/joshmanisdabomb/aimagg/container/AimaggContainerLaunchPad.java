package com.joshmanisdabomb.aimagg.container;

import com.joshmanisdabomb.aimagg.container.slot.AimaggSlotLimited;
import com.joshmanisdabomb.aimagg.container.slot.AimaggSlotMissile;
import com.joshmanisdabomb.aimagg.container.slot.AimaggSlotOutput;
import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;
import com.joshmanisdabomb.aimagg.te.AimaggTESpreaderConstructor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class AimaggContainerLaunchPad extends Container {

	private AimaggTELaunchPad te;
	
	public AimaggContainerLaunchPad(EntityPlayer player, AimaggTELaunchPad te) {
		this.te = te;
		
	    // Tile Entity, Slot 0-8, Slot IDs 0-8
	    /*for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 3; ++x) {
	            this.addSlotToContainer(new Slot(te, x + y * 3, 62 + x * 18, 18 + y * 18));
	        }
	    }*/
		
		//Missile Slot, Slot 0, Slot ID 0
        this.addSlotToContainer(new AimaggSlotMissile(te, 0, 150, 9));
		
		//Rocket Fuel Slot, Slot 1, Slot ID 1
        this.addSlotToContainer(new AimaggSlotLimited(te, 1, 150, 27, Items.LAVA_BUCKET));
		
		//Coordinate Slot, Slot 2, Slot ID 2
        this.addSlotToContainer(new AimaggSlotLimited(te, 2, 150, 63, Items.PAPER));

	    // Player Inventory, Slot 9-35, Slot IDs 3-29
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(player.inventory, x + y * 9 + 9, 36 + x * 18, 95 + y * 18));
	        }
	    }

	    // Player Inventory, Slot 0-8, Slot IDs 30-38
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(player.inventory, x, 36 + x * 18, 153));
	    }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.te.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
	    ItemStack previous = null;
	    Slot slot = (Slot) this.inventorySlots.get(fromSlot);

	    if (slot != null && slot.getHasStack()) {
	        ItemStack current = slot.getStack();
	        previous = current.copy();

	        if (fromSlot < this.te.getSizeInventory()) {
	            // From TE Inventory to Player Inventory
	            if (!this.mergeItemStack(current, 0+this.te.getSizeInventory(), 36+this.te.getSizeInventory(), true))
	                return null;
	        } else {
	            // From Player Inventory to TE Inventory
	            if (!this.mergeItemStack(current, 0, this.te.getSizeInventory(), false))
	                return null;
	        }

	        if (current.stackSize == 0)
	            slot.putStack((ItemStack) null);
	        else
	            slot.onSlotChanged();

	        if (current.stackSize == previous.stackSize)
	            return null;
	        slot.onPickupFromSlot(playerIn, current);
	    }
	    return previous;
	}
	
	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean useEndIndex) {
	    boolean success = false;
	    int index = startIndex;

	    if (useEndIndex)
	        index = endIndex - 1;

	    Slot slot;
	    ItemStack stackinslot;

	    if (stack.isStackable()) {
	        while (stack.stackSize > 0 && (!useEndIndex && index < endIndex || useEndIndex && index >= startIndex)) {
	            slot = (Slot) this.inventorySlots.get(index);
	            stackinslot = slot.getStack();

	            if (stackinslot != null && stackinslot.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == stackinslot.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, stackinslot)) {
	                int l = stackinslot.stackSize + stack.stackSize;
	                int maxsize = Math.min(stack.getMaxStackSize(), slot.getItemStackLimit(stack));

	                if (l <= maxsize) {
	                    stack.stackSize = 0;
	                    stackinslot.stackSize = l;
	                    slot.onSlotChanged();
	                    success = true;
	                } else if (stackinslot.stackSize < maxsize) {
	                    stack.stackSize -= stack.getMaxStackSize() - stackinslot.stackSize;
	                    stackinslot.stackSize = stack.getMaxStackSize();
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

	    if (stack.stackSize > 0) {
	        if (useEndIndex) {
	            index = endIndex - 1;
	        } else {
	            index = startIndex;
	        }

	        while (!useEndIndex && index < endIndex || useEndIndex && index >= startIndex && stack.stackSize > 0) {
	            slot = (Slot) this.inventorySlots.get(index);
	            stackinslot = slot.getStack();

	            // Forge: Make sure to respect isItemValid in the slot.
	            if (stackinslot == null && slot.isItemValid(stack)) {
	                if (stack.stackSize < slot.getItemStackLimit(stack)) {
	                    slot.putStack(stack.copy());
	                    stack.stackSize = 0;
	                    success = true;
	                    break;
	                } else {
	                    ItemStack newstack = stack.copy();
	                    newstack.stackSize = slot.getItemStackLimit(stack);
	                    slot.putStack(newstack);
	                    stack.stackSize -= slot.getItemStackLimit(stack);
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
	}

}
