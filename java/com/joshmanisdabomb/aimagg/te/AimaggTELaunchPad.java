package com.joshmanisdabomb.aimagg.te;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockLaunchPad;
import com.joshmanisdabomb.aimagg.data.MissileType;
import com.joshmanisdabomb.aimagg.entity.AimaggEntityMissile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggTELaunchPad extends TileEntity implements IInventory {
	
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
    private String customName;
    
    private int destinationx;
    private int destinationy;
    private int destinationz;
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger("destinationx", destinationx);
		compound.setInteger("destinationy", destinationy);
		compound.setInteger("destinationz", destinationz);

        ItemStackHelper.saveAllItems(compound, this.inventory);
	    
	    if (this.hasCustomName()) {
	    	compound.setString("CustomName", this.getCustomName());
	    }
	    
	    return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		destinationx = compound.getInteger("destinationx");
		destinationy = compound.getInteger("destinationy");
		destinationz = compound.getInteger("destinationz");
		
		this.inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);

	    if (compound.hasKey("CustomName", 8)) {
	        this.setCustomName(compound.getString("CustomName"));
	    }
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtIn = new NBTTagCompound();
		this.writeToNBT(nbtIn);
		return new SPacketUpdateTileEntity(this.getPos(), this.getBlockMetadata(), nbtIn);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	public String getBaseName() {
		return "container.aimagg_launch_pad";
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
		return 3;
	}

	public ItemStack getStackInSlot(int index) {
        return this.inventory.get(index);
    }
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = this.inventory.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.inventory.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

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
	
	public MissileType getMissileType() {
		return this.inventory.get(0) == null ? null : MissileType.getFromMetadata(this.inventory.get(0).getMetadata());
	}

	public void setDestination(int destX, int destY, int destZ) {
		destinationx = destX;
		destinationy = destY;
		destinationz = destZ;
		this.getTileData().setInteger("destinationx", destinationx);
		this.getTileData().setInteger("destinationy", destinationy);
		this.getTileData().setInteger("destinationz", destinationz);
		this.markDirty();
	}

	public void launch(boolean creative) {
		AimaggEntityMissile newMissile = new AimaggEntityMissile(this.getWorld(), this.getPos().getX() + 0.5, this.getPos().getY() + 0.3, this.getPos().getZ() + 0.5);
		newMissile.setMissileType(this.getMissileType());
		newMissile.setOrigin(newMissile.getPosition());
		newMissile.setDestination(new BlockPos(destinationx,destinationy,destinationz));
		newMissile.setStrength(this.inventory.get(0).getSubCompound(Constants.MOD_ID + "_missile").getInteger("strength"));
		newMissile.setLaunched(true);
		this.getWorld().spawnEntity(newMissile);
		
		if (!creative) {
			this.inventory.get(0).setCount(this.inventory.get(0).getCount() - 1);
			this.inventory.get(1).setCount(this.inventory.get(1).getCount() - 1);
		}
		
		this.markDirty();
	}
	
	@SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 16384D;
    }
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AimaggBlockLaunchPad.LAUNCH_PAD_AABB_RENDER.offset(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		//TODO see how other tile entities handle this
		return true;
	}
	
}