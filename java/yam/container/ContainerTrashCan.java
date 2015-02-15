package yam.container;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import yam.blocks.entity.TileEntityTrashCan;

public class ContainerTrashCan extends Container {
	
	private TileEntityTrashCan te;

	public ContainerTrashCan(InventoryPlayer par1InventoryPlayer, TileEntityTrashCan par2TileEntityTrashCan) {
        this.te = par2TileEntityTrashCan;
        
        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(par2TileEntityTrashCan, j + i * 9, 8 + j * 18, 17 + i * 18));
            }
        }
        
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
        }
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}
	
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (p_82846_2_ < 27)
            {
                if (!this.mergeItemStack(itemstack1, 27, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 27, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
	
	public void onContainerClosed(EntityPlayer p_75134_1_)
    {
		for (int i = 0; i < te.getSizeInventory(); i++) {
			this.getSlot(i).putStack(null);
		}
		
        super.onContainerClosed(p_75134_1_);
    }

}
