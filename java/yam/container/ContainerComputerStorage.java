package yam.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import yam.blocks.entity.TileEntityComputer;

public class ContainerComputerStorage extends Container {
	
	private TileEntityComputer te;
	private InventoryDrive containerDisk;

	public ContainerComputerStorage(InventoryPlayer par1InventoryPlayer, TileEntityComputer par2TileEntityComputer, int drive) {
        this.te = par2TileEntityComputer;
        this.containerDisk = par2TileEntityComputer.getStorageDrive(drive+1);
        
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 48 + j * 18, 142 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 48 + i * 18, 200));
        }

    	this.addSlotToContainer(new ComputerSlot(par2TileEntityComputer, 0, 6, 230));
        for (int i = 0; i < 8; ++i) {
        	this.addSlotToContainer(new ComputerSlot(par2TileEntityComputer, i+1, 38 + i * 28, 230));
        }
        
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 13; ++j) {
                this.addSlotToContainer(new ComputerStorageSlot(containerDisk, j + i * 13 + 13, 12 + j * 18, 23 + i * 18));
            }
        }
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}
	
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        return null;
    }
	
	public void onContainerClosed(EntityPlayer p_75134_1_)
    {		
        super.onContainerClosed(p_75134_1_);
    }
	
	class ComputerSlot extends Slot
    {
        public ComputerSlot(IInventory p_i1801_2_, int p_i1801_3_, int p_i1801_4_, int p_i1801_5_)
        {
            super(p_i1801_2_, p_i1801_3_, p_i1801_4_, p_i1801_5_);
        }

        /**
         * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
         */
        public boolean isItemValid(ItemStack p_75214_1_)
        {
            return te.isItemValidForSlot(this.slotNumber, p_75214_1_);
        }
        
        /**
         * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
         * case of armor slots)
         */
        public int getSlotStackLimit()
        {
            return 1;
        }
        
        public void onSlotChanged() {
        }
    }
	
	class ComputerStorageSlot extends Slot
    {
        public ComputerStorageSlot(IInventory p_i1801_2_, int p_i1801_3_, int p_i1801_4_, int p_i1801_5_)
        {
            super(p_i1801_2_, p_i1801_3_, p_i1801_4_, p_i1801_5_);
        }

        /**
         * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
         */
        public boolean isItemValid(ItemStack p_75214_1_)
        {
            return true;
        }
        
        /**
         * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
         * case of armor slots)
         */
        public int getSlotStackLimit()
        {
            return 64;
        }
        
        public void onSlotChanged() {
        	containerDisk.markDirty();
        }
    }

}
