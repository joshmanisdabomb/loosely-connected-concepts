package yam.blocks.entity;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import yam.container.InventoryDrive;
import yam.items.ItemMotherboard;
import yam.items.ItemStorage;

public class TileEntityComputer extends TileEntity implements IInventory {

	private ItemStack[] stacks = new ItemStack[9];
	private Random rand = new Random();
	private String customName;

	public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
        this.stacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.stacks.length)
            {
                this.stacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        if (p_145839_1_.hasKey("CustomName", 8))
        {
        	this.customName = p_145839_1_.getString("CustomName");
        }
    }
	
	public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.stacks.length; ++i)
        {
            if (this.stacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.stacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        p_145841_1_.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName())
        {
            p_145841_1_.setString("CustomName", this.customName);
        }
    }

	@Override
	public int getSizeInventory() {
		return this.stacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return this.stacks[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.stacks[par1] != null)
        {
            ItemStack itemstack;

            if (this.stacks[par1].stackSize <= par2)
            {
                itemstack = this.stacks[par1];
                this.stacks[par1] = null;
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.stacks[par1].splitStack(par2);

                if (this.stacks[par1].stackSize == 0)
                {
                    this.stacks[par1] = null;
                }

                this.markDirty();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.stacks[par1] != null)
        {
            ItemStack itemstack = this.stacks[par1];
            this.stacks[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.stacks[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
		
		this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "Computer";
	}
	
	public void setInventoryName(String name) {
		this.customName = name;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return !(this.customName == null || this.customName == "");
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	public void openInventory()
    {
    }

    public void closeInventory()
    {
    }

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		if (var2 == null) {return false;}
		else if (var1 == 36 && var2.getItem() instanceof ItemMotherboard) {return true;}
		else if (var1 > 36 && var1 < 46 && var2.getItem() instanceof ItemStorage) {return true;}
		return false;
	}

	public InventoryDrive getStorageDrive(int id) {
		return new InventoryDrive(this, this.stacks[id], id);
	}
	
}