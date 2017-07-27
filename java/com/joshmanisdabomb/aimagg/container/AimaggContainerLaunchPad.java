package com.joshmanisdabomb.aimagg.container;

import com.joshmanisdabomb.aimagg.container.slot.AimaggSlotLimited;
import com.joshmanisdabomb.aimagg.container.slot.AimaggSlotMissile;
import com.joshmanisdabomb.aimagg.container.slot.AimaggSlotVectorPearl;
import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
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
        //TODO Lava fuel is placeholder.
        this.addSlotToContainer(new AimaggSlotLimited(te, 1, 150, 27, Items.LAVA_BUCKET));
		
		//Coordinate Slot, Slot 2, Slot ID 2
        this.addSlotToContainer(new AimaggSlotVectorPearl(te, 2, 150, 63));

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

			// If itemstack is in missile slot.
			if (par2 == 0) {
				//Place in inventory, then action bar.
				if (!this.mergeItemStack(itemstack1, 3, 30, false) && !this.mergeItemStack(itemstack1, 30, 39, false)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			// If itemstack is in fuel slot.
			} else if (par2 == 1) {
				//Place in inventory, then action bar.
				if (!this.mergeItemStack(itemstack1, 3, 30, false) && !this.mergeItemStack(itemstack1, 30, 39, false)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			// If itemstack is in vector pearl slot.
			} else if (par2 == 2) {
				//Place in action bar, then inventory.
				if (!this.mergeItemStack(itemstack1, 30, 39, false) && !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			// If itemstack is in inventory.
			} else if (par2 >= 3 && par2 <= 29) {
				//Place in missile slot, then fuel slot, then vector pearl slot, then action bar.
				if (!this.mergeItemStack(itemstack1, 0, 1, false) && !this.mergeItemStack(itemstack1, 1, 2, false) && !this.mergeItemStack(itemstack1, 2, 3, false) && !this.mergeItemStack(itemstack1, 30, 39, false)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			// If itemstack is in action bar.
			} else if (par2 >= 30 && par2 <= 38) {
				//Place in missile slot, then fuel slot, then vector pearl slot, then action bar.
				if (!this.mergeItemStack(itemstack1, 0, 1, false) && !this.mergeItemStack(itemstack1, 1, 2, false) && !this.mergeItemStack(itemstack1, 2, 3, false) && !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return null;
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
