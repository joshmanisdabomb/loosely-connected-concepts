package com.joshmanisdabomb.aimagg.container;

import com.joshmanisdabomb.aimagg.container.inventory.InventorySpreaderInterface;
import com.joshmanisdabomb.aimagg.data.world.SpreaderData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSetSlot;
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
		
		// Spreader Interface Inventory, Slot 0-127, Slot IDs 0-127
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

	    // Player Inventory, Slot 9-35, Slot IDs 128-154
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(player.inventory, x + y * 9 + 9, 8 + x * 18, 140 + y * 18));
	        }
	    }

	    // Player Inventory, Slot 0-8, Slot IDs 155-163
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(player.inventory, x, 8 + x * 18, 198));
	    }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	@Override
	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		if (!w.isRemote) {
			ItemStack itemstack = null;
			Slot slot = (Slot) this.inventorySlots.get(par2);
	
			if (slot.getStack() != null && !slot.getStack().isEmpty()) {
				ItemStack itemstack1 = slot.getStack();
				itemstack = itemstack1.copy();
	
				// If itemstack is in spreader interface.
				if (par2 <= 16*8) {
					//Place in inventory, then action bar.
					if (!this.mergeItemStack(itemstack1, 128, 164, false)) {
						((AimaggContainerSpreaderInterface)par1EntityPlayer.openContainer).detectAndSendChanges();
						return ItemStack.EMPTY;
					}
					slot.onSlotChange(itemstack1, itemstack);
				// If itemstack is in inventory.
				} else if (par2 >= 128 && par2 < 155) {
					//Place in spreader interface slots, then action bar.
					if (!this.mergeItemStack(itemstack1, 0, (16*8)+1, false) && !this.mergeItemStack(itemstack1, 155, 164, false)) {
						((AimaggContainerSpreaderInterface)par1EntityPlayer.openContainer).detectAndSendChanges();
						return ItemStack.EMPTY;
					}
					slot.onSlotChange(itemstack1, itemstack);
				// If itemstack is in action bar.
				} else if (par2 >= 155 && par2 < 164) {
					//Place in spreader interface slots, then inventory.
					if (!this.mergeItemStack(itemstack1, 0, (16*8)+1, false) && !this.mergeItemStack(itemstack1, 128, 155, false)) {
						((AimaggContainerSpreaderInterface)par1EntityPlayer.openContainer).detectAndSendChanges();
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
					((AimaggContainerSpreaderInterface)par1EntityPlayer.openContainer).detectAndSendChanges();
					return ItemStack.EMPTY;
				}
	
				slot.onTake(par1EntityPlayer, itemstack1);
			}
			((AimaggContainerSpreaderInterface)par1EntityPlayer.openContainer).detectAndSendChanges();
			return itemstack != null ? itemstack : ItemStack.EMPTY;
		} else {
			return ItemStack.EMPTY;
		}
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
