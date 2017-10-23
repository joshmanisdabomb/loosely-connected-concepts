package com.joshmanisdabomb.aimagg.te;

import javax.annotation.Nullable;

import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBouncePad;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggTEBouncePad extends TileEntity implements IInventory, ITickable {

	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);

	private String customName;
	
	private float extension = 2.0F;

	private float strengthPrimary;
	private float strengthSecondary1;
	private float strengthSecondary2;

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setFloat("primary_strength", this.strengthPrimary);
		compound.setFloat("secondary_strength_1", this.strengthSecondary1);
		compound.setFloat("secondary_strength_2", this.strengthSecondary2);

		ItemStackHelper.saveAllItems(compound, this.inventory);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.getCustomName());
		}

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		this.strengthPrimary = compound.getFloat("primary_strength");
		this.strengthSecondary1 = compound.getFloat("secondary_strength_1");
		this.strengthSecondary2 = compound.getFloat("secondary_strength_2");

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

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = super.getUpdateTag();
		compound.setFloat("primary_strength", this.strengthPrimary);
		compound.setFloat("secondary_strength_1", this.strengthSecondary1);
		compound.setFloat("secondary_strength_2", this.strengthSecondary2);
		return compound;
	}

	public String getBaseName() {
		return "container.aimagg:bounce_pad";
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
		return 1;
	}

	public ItemStack getStackInSlot(int index) {
		return this.inventory.get(index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = this.inventory.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.inventory.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		this.markDirty();
	}

	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.inventory, index, count);
	}

	public ItemStack removeStackFromSlot(int index) {
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
		this.inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
	}

	public String getCustomName() {
		return this.customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.inventory) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}
	
	public void setStrength(float strengthPrimary, float strengthSecondary1, float strengthSecondary2) {
		this.strengthPrimary = strengthPrimary;
		this.strengthSecondary1 = strengthSecondary1;
		this.strengthSecondary2 = strengthSecondary2;
		this.getTileData().setFloat("primary_strength", this.strengthPrimary);
		this.getTileData().setFloat("secondary_strength_1", this.strengthSecondary1);
		this.getTileData().setFloat("secondary_strength_2", this.strengthSecondary2);
		this.markDirty();
	}
	
	public float getPrimaryStrength() {
		return this.strengthPrimary;
	}
	
	public float getSecondaryStrength1() {
		return this.strengthSecondary1;
	}
	
	public float getSecondaryStrength2() {
		return this.strengthSecondary2;
	}
	
	public float getExtension() {
		return this.extension;
	}

	public void setExtension(float f) {
		this.extension = f;
	}
	
	public EnumFacing getDirection() {
		if (this.getWorld().getBlockState(pos).getBlock() instanceof AimaggBlockBouncePad) {
			return this.getWorld().getBlockState(this.getPos()).getValue(AimaggBlockBouncePad.FACING);
		} else {
			return null;
		}
	}
	
	public EnumFacing.Axis getSecondaryStrengthAxis1() {
		return (this.getDirection().getAxis() == EnumFacing.Axis.X) ? EnumFacing.Axis.Y : EnumFacing.Axis.X;
	}
	
	public EnumFacing.Axis getSecondaryStrengthAxis2() {
		return (this.getDirection().getAxis() == EnumFacing.Axis.Y || this.getSecondaryStrengthAxis1() == EnumFacing.Axis.Y) ? EnumFacing.Axis.Z : EnumFacing.Axis.Y;
	}

	public double getMotionX() {
		if (this.getDirection().getAxis() == EnumFacing.Axis.X) {
			return this.getPrimaryStrength() * this.getDirection().getAxisDirection().getOffset();
		} else {
			return this.getSecondaryStrength1();
		}
	}

	public double getMotionY() {
		if (this.getDirection().getAxis() == EnumFacing.Axis.Y) {
			return this.getPrimaryStrength() * this.getDirection().getAxisDirection().getOffset();
		} else if (this.getSecondaryStrengthAxis1() == EnumFacing.Axis.Y) {
			return this.getSecondaryStrength1();
		} else {
			return this.getSecondaryStrength2();
		}
	}

	public double getMotionZ() {
		if (this.getDirection().getAxis() == EnumFacing.Axis.Z) {
			return this.getPrimaryStrength() * this.getDirection().getAxisDirection().getOffset();
		} else {
			return this.getSecondaryStrength2();
		}
	}
	
	public boolean shouldBounce() {
		return this.getPrimaryStrength() != 0 || this.getSecondaryStrength1() != 0 || this.getSecondaryStrength2() != 0;
	}
	
	public int getSpringAmount() {
		return this.getStackInSlot(0).getCount();
	}
	
	public double getSpringMotionModifier() {
		return (this.getSpringAmount() + 1) / 11D;
	}
	
	@Override
    public double getMaxRenderDistanceSquared() {
        return 16384D;
    }
	
    public double getPacketDistanceSquared() {
        return 16384D;
    }
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return Block.FULL_BLOCK_AABB.offset(this.getPos()); //TODO
	}

	@Override
	public void update() {
		this.extension = Math.max(2.0F, this.extension - 1F);
	}

}
