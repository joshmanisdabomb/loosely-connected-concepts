package yam.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.Constants;
import yam.blocks.entity.TileEntityComputer;
import yam.items.ItemStorage;

public class InventoryDrive implements IInventory {

	private TileEntityComputer te;
	private ItemStack drive;
	private int id;
	private NBTTagList filesystem;

	public InventoryDrive(TileEntityComputer te, ItemStack itemStack, int id) {
		this.te = te;
		this.drive = itemStack;
		this.id = id;
		this.filesystem = drive.stackTagCompound.getTagList("filesystem", Constants.NBT.TAG_COMPOUND);
	}

	@Override
	public int getSizeInventory() {
		return ((ItemStorage)drive.getItem()).getSizeInStacks();
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
        return ItemStack.loadItemStackFromNBT(filesystem.getCompoundTagAt(var1));
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {		
        ItemStack i = this.getStackInSlot(var1);
        if (i == null) {return null;}

        if (i.stackSize <= var2)
        {
            this.setInventorySlotContents(var1, null);
        }
        else
        {
            i = i.splitStack(var2);

            if (i.stackSize == 0)
            {
            	this.setInventorySlotContents(var1, null);
            }
        }
        
        this.markDirty();
        return i;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
        ItemStack i = this.getStackInSlot(var1);
        if (i == null) {return null;}
        
        this.setInventorySlotContents(var1, null);
        return i;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		if (var2 != null) {
			var2.writeToNBT(this.filesystem.getCompoundTagAt(var1));
		} else {
			this.filesystem.getCompoundTagAt(var1).removeTag("id");
			this.filesystem.getCompoundTagAt(var1).removeTag("Count");
			this.filesystem.getCompoundTagAt(var1).removeTag("Damage");
			this.filesystem.getCompoundTagAt(var1).removeTag("tag");
		}
		this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return drive.getDisplayName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return drive.getDisplayName() == StatCollector.translateToLocal(drive.getItem().getUnlocalizedName());
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		drive.stackTagCompound.removeTag("filesystem");
	    drive.stackTagCompound.setTag("filesystem", filesystem);
	    te.setInventorySlotContents(id, drive);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}

	@Override
	public void openInventory() {
		//
	}

	@Override
	public void closeInventory() {
	    this.markDirty();
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return true;
	}

}
