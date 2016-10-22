package com.joshmanisdabomb.aimagg.te;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class AimaggTESpreaderConstructor extends TileEntity implements IInventory {
	
	private ItemStack[] inventory;
    private String customName;
    
    public AimaggTESpreaderConstructor() {
		this.inventory = new ItemStack[this.getSizeInventory()];
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		NBTTagList list = new NBTTagList();
	    for (int i = 0; i < this.getSizeInventory(); ++i) {
	        if (this.getStackInSlot(i) != null) {
	            NBTTagCompound stackTag = new NBTTagCompound();
	            stackTag.setByte("Slot", (byte) i);
	            this.getStackInSlot(i).writeToNBT(stackTag);
	            list.appendTag(stackTag);
	        }
	    }
	    compound.setTag("Items", list);
	    
	    if (this.hasCustomName()) {
	    	compound.setString("CustomName", this.getCustomName());
	    }
	    
	    return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

	    NBTTagList list = compound.getTagList("Items", 10);
	    for (int i = 0; i < list.tagCount(); ++i) {
	        NBTTagCompound stackTag = list.getCompoundTagAt(i);
	        int slot = stackTag.getByte("Slot") & 255;
	        this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
	    }

	    if (compound.hasKey("CustomName", 8)) {
	        this.setCustomName(compound.getString("CustomName"));
	    }
	}

	public String getBaseName() {
		return "container.aimagg_spreader_constructor";
	}
	
	@Override
	public String getName() {
		return this.hasCustomName() ? this.getCustomName() : this.getBaseName();
	}

	@Override
	public boolean hasCustomName() {
		return this.getCustomName() != null && !this.getCustomName().isEmpty();
	}
	
	@Override
	public ITextComponent getDisplayName() {
	    return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}

	@Override
	public int getSizeInventory() {
		return 10;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index < 0 || index >= this.getSizeInventory()) {
	        return null;
		}
	    return this.inventory[index];
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 0 || index >= this.getSizeInventory()) {
	        return;
		}

	    if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
	        stack.stackSize = this.getInventoryStackLimit();
	    }
	    
	    if (stack != null && stack.stackSize == 0) {
	        stack = null;
	    }

	    this.inventory[index] = stack;
	    this.markDirty();
	}
	
	public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.inventory, index, count);
    }

    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.inventory, index);
    }

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.getSizeInventory(); i++) {
	        this.setInventorySlotContents(i, null);
		}
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}
	
}