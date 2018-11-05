package com.joshmanisdabomb.aimagg.container;

import com.joshmanisdabomb.aimagg.blocks.AimaggBlockClassicChest;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockClassicChest.DoubleChestType;
import com.joshmanisdabomb.aimagg.te.AimaggTEClassicChest;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class AimaggContainerClassicChest extends Container {

	private AimaggTEClassicChest te;
	private AimaggTEClassicChest teNeighbour;
	
	public AimaggContainerClassicChest(EntityPlayer player, AimaggTEClassicChest te) {
		this.te = te;
		this.teNeighbour = te.getNeighbour();
		
		IBlockState s = te.getWorld().getBlockState(te.getPos());
		boolean swapChests = s.getBlock().getActualState(s, te.getWorld(), te.getPos()).getValue(AimaggBlockClassicChest.DOUBLE_TYPE) == DoubleChestType.DOUBLE_LEFT;
		
	    // Chest 1 Inventory, Slot 0-26, Slot IDs 0-26
		for (int y = 0; y < te.getSizeInventory() / 9; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(swapChests ? teNeighbour : te, x + y * 9, 8 + x * 18, 18 + y * 18));
            }
        }

	    // Chest 2 Inventory, Slot 0-26, Slot IDs 27-53
		if (this.isDoubleChest()) {
			for (int y = 0; y < teNeighbour.getSizeInventory() / 9; ++y) {
	            for (int x = 0; x < 9; ++x) {
	                this.addSlotToContainer(new Slot(swapChests ? te : teNeighbour, x + y * 9, 8 + x * 18, 72 + y * 18));
	            }
	        }
		}

	    // Player Inventory, Slot 9-35, Slot IDs 27-53/54-80 if double
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(player.inventory, x + y * 9 + 9, 8 + x * 18, 85 + y * 18 + (this.isDoubleChest() ? 54 : 0)));
	        }
	    }

	    // Player Inventory, Slot 0-8, Slot IDs 54-62/81-89 if double
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(player.inventory, x, 8 + x * 18, 143 + (this.isDoubleChest() ? 54 : 0)));
	    }
	}
	
	private boolean isDoubleChest() {
		return this.teNeighbour != null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.te.isUsableByPlayer(player);
	}
	
	@Override
	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot.getStack() != null && !slot.getStack().isEmpty()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			int slotAddition = (this.isDoubleChest() ? 27 : 0);
			
			// If itemstack is in the chests.
			if (par2 <= 0 && par2 <= 26 + slotAddition) {
				//Place in inventory, then action bar.
				if (!this.mergeItemStack(itemstack1, 27 + slotAddition, 63 + slotAddition, false)) {
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(itemstack1, itemstack);
			// If itemstack is in inventory.
			} else if (par2 >= 27 + slotAddition && par2 < 54 + slotAddition) {
				//Place in chest, then action bar.
				if (!this.mergeItemStack(itemstack1, 0, 27 + slotAddition, false) && !this.mergeItemStack(itemstack1, 54 + slotAddition, 63 + slotAddition, false)) {
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(itemstack1, itemstack);
			// If itemstack is in action bar.
			} else if (par2 >= 54 + slotAddition && par2 < 63 + slotAddition) {
				//Place in chest, then inventory.
				if (!this.mergeItemStack(itemstack1, 0, 27 + slotAddition, false) && !this.mergeItemStack(itemstack1, 27 + slotAddition, 54 + slotAddition, false)) {
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(itemstack1, itemstack);
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(par1EntityPlayer, itemstack1);
		}
		return itemstack != null ? itemstack : ItemStack.EMPTY;
	}
	
	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
		boolean flag = false;
        int i = startIndex;

        if (reverseDirection)
        {
            i = endIndex - 1;
        }

        if (stack.isStackable())
        {
            while (!stack.isEmpty())
            {
                if (reverseDirection)
                {
                    if (i < startIndex)
                    {
                        break;
                    }
                }
                else if (i >= endIndex)
                {
                    break;
                }

                Slot slot = this.inventorySlots.get(i);
                ItemStack itemstack = slot.getStack();

                if (!itemstack.isEmpty() && itemstack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemstack))
                {
                    int j = itemstack.getCount() + stack.getCount();
                    int maxSize = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());

                    if (j <= maxSize)
                    {
                        stack.setCount(0);
                        itemstack.setCount(j);
                        slot.onSlotChanged();
                        flag = true;
                    }
                    else if (itemstack.getCount() < maxSize)
                    {
                        stack.shrink(maxSize - itemstack.getCount());
                        itemstack.setCount(maxSize);
                        slot.onSlotChanged();
                        flag = true;
                    }
                }

                if (reverseDirection)
                {
                    --i;
                }
                else
                {
                    ++i;
                }
            }
        }

        if (!stack.isEmpty())
        {
            if (reverseDirection)
            {
                i = endIndex - 1;
            }
            else
            {
                i = startIndex;
            }

            while (true)
            {
                if (reverseDirection)
                {
                    if (i < startIndex)
                    {
                        break;
                    }
                }
                else if (i >= endIndex)
                {
                    break;
                }

                Slot slot1 = this.inventorySlots.get(i);
                ItemStack itemstack1 = slot1.getStack();

                if (itemstack1.isEmpty() && slot1.isItemValid(stack))
                {
                    if (stack.getCount() > slot1.getSlotStackLimit())
                    {
                        slot1.putStack(stack.splitStack(slot1.getSlotStackLimit()));
                    }
                    else
                    {
                        slot1.putStack(stack.splitStack(stack.getCount()));
                    }

                    slot1.onSlotChanged();
                    flag = true;
                    break;
                }

                if (reverseDirection)
                {
                    --i;
                }
                else
                {
                    ++i;
                }
            }
        }

        return flag;
	}

}
