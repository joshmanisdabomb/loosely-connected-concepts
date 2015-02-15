package yam.container;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import yam.blocks.entity.TileEntityClassicChest;
import yam.blocks.entity.TileEntityTrashCan;

public class ContainerClassicChest extends Container {
	
	private TileEntityClassicChest te;
	private int numRows;
    private IInventory lowerChestInventory;

	public ContainerClassicChest(InventoryPlayer par1InventoryPlayer, TileEntityClassicChest tileEntity, TileEntityClassicChest tileEntity2) {
        this.te = tileEntity;

        int i = (this.numRows - 4) * 18;
        int j, k;
        this.numRows = tileEntity.getSizeInventory() / 9;
        this.lowerChestInventory = tileEntity2;
        tileEntity2.openInventory();
        
        for (j = 0; j < this.numRows; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(tileEntity, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for (j = 0; j < 3; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }

        for (j = 0; j < 9; ++j)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, j, 8 + j * 18, 161 + i));
        }
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) 
    {
        return this.lowerChestInventory.isUseableByPlayer(var1);
    }
	
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (p_82846_2_ < this.numRows * 9)
            {
                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false))
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
	
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);
        this.lowerChestInventory.closeInventory();
    }

    /**
     * Return this chest container's lower chest inventory.
     */
    public IInventory getLowerChestInventory()
    {
        return this.lowerChestInventory;
    }

}
