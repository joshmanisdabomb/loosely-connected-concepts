package yam.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import yam.blocks.entity.TileEntityComputer;

public class ContainerComputer extends Container {
	
	private TileEntityComputer te;

	public ContainerComputer(InventoryPlayer par1InventoryPlayer, TileEntityComputer par2TileEntityComputer) {
        this.te = par2TileEntityComputer;
       
        this.addSlotToContainer(new ComputerSlot(par2TileEntityComputer, 0, 5, 229));
        this.addSlotToContainer(new ComputerSlot(par2TileEntityComputer, 1, 37, 229));
        this.addSlotToContainer(new ComputerSlot(par2TileEntityComputer, 2, 65, 229));
        this.addSlotToContainer(new ComputerSlot(par2TileEntityComputer, 3, 93, 229));
        this.addSlotToContainer(new ComputerSlot(par2TileEntityComputer, 4, 121, 229));
        this.addSlotToContainer(new ComputerSlot(par2TileEntityComputer, 5, 149, 229));
        this.addSlotToContainer(new ComputerSlot(par2TileEntityComputer, 6, 177, 229));
        this.addSlotToContainer(new ComputerSlot(par2TileEntityComputer, 7, 205, 229));
        this.addSlotToContainer(new ComputerSlot(par2TileEntityComputer, 8, 233, 229));
        
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
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
        private static final String __OBFID = "CL_00001736";

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

}
